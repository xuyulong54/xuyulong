package wusc.edu.pay.web.boss.action.rule; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.service.BankChannelFacade;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.enums.PayTypeEnum;
import wusc.edu.pay.facade.payrule.service.BankBranchFacade;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 *类描述：
 *@author: huangbin
 *@date： 日期：2013-12-19 时间：下午5:23:48
 *@version 1.0
 */
public class BankBranchAction extends BossBaseAction {

	private static final long serialVersionUID = 5781919856017194617L;
	
	@Autowired
	private BankBranchFacade bankBranchFacade;
	@Autowired
	private BankChannelFacade bankChannelFacade;
	@Autowired
	private FrpFacade frpFacade;
	private static final Log log = LogFactory.getLog(BankBranchAction.class);
	
	/***
	 * 获取列表方法
	 * @return
	 */
	@Permission("bank:branch:view")
	public String list(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("frpCode", getString("frpCode"));
		paramMap.put("defaultBankChannelCode", getString("defaultBankChannelCode"));
		paramMap.put("spareBankChannelCode", getString("spareBankChannelCode"));
		super.pageBean = bankBranchFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "list";
	}
	
	/***
	 * 跳转到新增页面 只针对银行卡类型的新增
	 * @return
	 */
	@Permission("bank:branch:add")
	public String toAdd(){
		String addType=getString("addType");
		List<Frp> frpList = frpFacade.listByPayType(PayTypeEnum.BANK_CARD.getValue());
		if(("loadAll").equals(addType)){
		//加载所有的支付接口
			List<BankChannel> bankChannelList = bankChannelFacade.listBy(new HashMap<String, Object>());
			this.putData("bankChannelList", bankChannelList);
			this.putData("addType", addType);
		}
		this.putData("frpList", frpList);
		return "add";
	}
	
	/***
	 * 保存
	 * @return
	 */
	@Permission("bank:branch:add")
	public String doAdd(){
		Long id = getLong("id");
		String frpCode = getString("frpCode"); // 支付渠道编号
		String defaultBankChannelCode  = getString("defaultBankChannelCode");
		if(defaultBankChannelCode == null || "".equals(defaultBankChannelCode)){
			return operateError("请选择默认支付渠道");
		}
		String spareBankChannelCode = getString("spareBankChannelCode");
		if(spareBankChannelCode == null || "".equals(spareBankChannelCode)){
			return operateError("请选择备用支付渠道");
		}
		String desc = getString("desc");
		if (id != null) { // 修改
			BankBranch bankBranch = bankBranchFacade.getById(id);
			bankBranch.setFrpCode(frpCode);
			bankBranch.setDefaultBankChannelCode(defaultBankChannelCode);
			bankBranch.setSpareBankChannelCode(spareBankChannelCode);
			bankBranch.setRemark(desc);
			bankBranch.setLastTime(new Date());
			bankBranchFacade.update(bankBranch);
			super.logEdit("修改渠道分流.支付渠道编号["+bankBranch.getFrpCode()+"]");
		}else{ // 新增
			try {
				BankBranch bankBranch = new BankBranch();
				bankBranch.setFrpCode(frpCode);
				bankBranch.setDefaultBankChannelCode(defaultBankChannelCode);
				bankBranch.setSpareBankChannelCode(spareBankChannelCode);
				bankBranch.setRemark(desc);
				bankBranch.setLastTime(new Date());
				bankBranchFacade.create(bankBranch);
				super.logSave("增加渠道分流.支付渠道编号["+bankBranch.getFrpCode()+"]");
			} catch (Exception e) {
				log.info("【"+frpCode+"】创建失败," , e);
				return operateError("【"+frpCode+"】创建失败,请检查渠道编号是否重复");
			}
		}
		return operateSuccess();
	}
	
	/***
	 * 修改
	 * @return
	 */
	@Permission("bank:branch:edit")
	public String toEdit(){
		Long id = getLong("id");
		BankBranch bankBranch = bankBranchFacade.getById(id);
		if(bankBranch == null){
			return operateError("改支付渠道编号不存在");
		}
		List<Frp> frpList = new ArrayList<Frp>();
		Frp frp = new Frp();
		frp.setFrpCode(bankBranch.getFrpCode());
		frpList.add(frp);
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("bankChannelCode2", bankBranch.getFrpCode());
		paramMap.put("status", 100);
		List<BankChannel> bankChannelList = bankChannelFacade.listBy(paramMap);
		this.putData("frpList", frpList);
		this.putData("bankChannelList", bankChannelList);
		this.pushData(bankBranch);
		return "edit";
	}
	
	public void getbankChannelCodeAll(){
		List<BankChannel>  tempList = bankChannelFacade.listBy(new HashMap<String , Object>());
		JSONArray json = null;
		HttpServletResponse resp = null;
		if(tempList != null && tempList.size() > 0){
			try{
				resp = getHttpResponse();
				resp.setCharacterEncoding("utf-8");
				json = JSONArray.fromObject(tempList);
				resp.getWriter().write(json.toString());
			}catch (Exception e){
				log.error("AddressAction fail:", e);
			}finally {
				try {
					resp.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 根据支付渠道获取相对应的银行支付渠道
	 */
	public void getbankChannelCodeByFrpCode(){
		String frpCode = getString("frpCode");
		HttpServletResponse resp = null;
		JSONArray json = null;
		if(frpCode != null && !"".equals(frpCode)){
			//bankChannelCode2 银行支付渠道
			Map<String , Object> paramMap = new HashMap<String , Object>();
			paramMap.put("bankChannelCode2", frpCode);
			//paramMap.put("bankChannelCode2", frpCode);
			List<BankChannel>  tempList = bankChannelFacade.listBy(paramMap);
			if(tempList != null && tempList.size() > 0){
				try{
					resp = getHttpResponse();
					resp.setCharacterEncoding("utf-8");
					json = JSONArray.fromObject(tempList);
					resp.getWriter().write(json.toString());
				}catch (Exception e){
					log.error("AddressAction fail:", e);
				}finally {
					try {
						resp.getWriter().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
 