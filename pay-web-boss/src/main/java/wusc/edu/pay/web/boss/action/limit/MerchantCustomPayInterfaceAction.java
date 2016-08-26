package wusc.edu.pay.web.boss.action.limit;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.enums.MerchantCustomPayInterfaceStatusEnum;
import wusc.edu.pay.facade.limit.service.MerchantCustomPayInterfaceFacade;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.enums.PayTypeEnum;
import wusc.edu.pay.facade.payrule.service.BankBranchFacade;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

/**
 * 
 * @描述: 开关限制包Action类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-7, 上午11:06:27 .
 * @版本: V1.0 .
 */
@SuppressWarnings("unchecked")
public class MerchantCustomPayInterfaceAction extends BossBaseAction{	
	
	private static final Log log = LogFactory.getLog(MerchantCustomPayInterfaceAction.class);
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private MerchantCustomPayInterfaceFacade merchantCustomPayInterfaceFacade;
	@Autowired
	private FrpFacade frpFacade;
	@Autowired
	private BankBranchFacade bankBranchFacade;
	
	/**
	 * 查询支付接口路由
	 * @return
	 */
	public String listMerchantCustomPayInterface(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String merchantNo = getString("merchantNo");
		paramMap.put("delStatus", MerchantCustomPayInterfaceStatusEnum.DELETED.name());
		paramMap.put("merchantNo", merchantNo);
		super.pageBean = merchantCustomPayInterfaceFacade.listPage(getPageParam(), paramMap);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);		
		return "merchantCustomPayInterfaceList";
	}
	
	/**
	 * 添加支付接口路由界面
	 * @return
	 */
	@Permission("limit:payinteface:edit")
	public String addMerchantCustomPayInterfaceUI(){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer PayType = PayTypeEnum.BANK_CARD.getValue();
		map.put("payType", PayType);
		super.pageBean = frpFacade.listPage(new PageParam(1, 1), map);
		pageBean = frpFacade.listPage(new PageParam(1,pageBean.getTotalCount()), map);
		List<Object> listFrp = pageBean.getRecordList();
//		List<Frp> listFrp = frpFacade.listAll();
		this.putData("listFrp", listFrp);
		String merchantNo = getString("merchantNo");
		this.putData("merchantNo", merchantNo);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("delStatus", MerchantCustomPayInterfaceStatusEnum.DELETED.name());
		paramMap.put("merchantNo", merchantNo);
		return "merchantCustomPayInterfaceAdd";
	}	
	
	/**
	 * 刷新支付接口路由数据
	 * @return
	 */
	public void refreshMerchantCustomPayInterface(){
		List<Frp> listFrp = frpFacade.listAll();
 		this.putData("listFrp", listFrp);
		String merchantNo = getString("merchantNo");
		this.putData("merchantNo", merchantNo);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("delStatus", MerchantCustomPayInterfaceStatusEnum.DELETED.name());
		paramMap.put("merchantNo", merchantNo);
		super.pageBean = merchantCustomPayInterfaceFacade.listPage(getPageParam(), paramMap);
		List<Object> MerchantCustomPayInterfaceList = pageBean.getRecordList();
		this.pushData(pageBean);
		this.pushData(MerchantCustomPayInterfaceList);	
		this.getOutputMsg().put("MerchantCustomPayInterfaceList", MerchantCustomPayInterfaceList);
		this.outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg(), this.getDefaultJsonConfig()));
	}
	
	/**
	 * 添加支付接口路由
	 * @return
	 */
	@Permission("limit:payinteface:edit")
	public void addMerchantCustomPayInterface(){	
		try {
			MerchantCustomPayInterface entity = new MerchantCustomPayInterface(); 
			String merchantNo = getString("merchantNo");
			String payWay = getString("payWay");
			String payInterface = getString("payInterface");
			if (payWay == null || "".equals(payWay) || payInterface == null || "".equals(payInterface)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "支付方式和支付接口不能为空！");		
			}
			else {
				MerchantCustomPayInterface merchantCustomPayInterface = 
						merchantCustomPayInterfaceFacade.getByMerchantNoPayWayAndPayInterface(merchantNo, payWay, payInterface);
						if (merchantCustomPayInterface == null) {
							entity.setMerchantNo(merchantNo);
							entity.setPayWay(payWay);
							entity.setPayInterface(payInterface);
							entity.setStatus(MerchantCustomPayInterfaceStatusEnum.ACTIVITY);
							merchantCustomPayInterfaceFacade.saveMerchantCustomPayInterface(entity);
							this.logSaveError("添加支付接口路由成功：商户编号为" + entity.getMerchantNo() +",接口编码为"+entity.getPayInterface() );
							this.getOutputMsg().put("STATE", "SUCCESS");
						}
						else {
							getOutputMsg().put("STATE", "FAIL");
							getOutputMsg().put("MSG", "该支付接口路由限制已存在");		
						}	
			}
			
		} catch (Exception e) {
			this.logSaveError("添加支付接口路由失败,商户编号为:" + getString("merchantNo") +",接口编码为:"+getString("payInterface"));
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统出错，请稍后再试！");	
			log.error("添加支付接口路由出错!", e);
		}
		finally {
			this.outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg(), this.getDefaultJsonConfig()));
		}
	}
	
	/**
	 * 修改支付接口路由页面
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Permission("limit:payinteface:edit")
	public String editMerchantCustomPayInterfaceUI(){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer PayType = PayTypeEnum.BANK_CARD.getValue();
		map.put("payType", PayType);
		super.pageBean = frpFacade.listPage(new PageParam(1, 1), map);
		pageBean = frpFacade.listPage(new PageParam(1,pageBean.getTotalCount()), map);
		List<Object> listFrp = pageBean.getRecordList();
		this.putData("listFrp", listFrp);
		Long id = getLong("id");
		MerchantCustomPayInterface merchantCustomPayInterface = null;
		if(id!=null){
			merchantCustomPayInterface = merchantCustomPayInterfaceFacade.getById(id);
			
		}
		List listPayInterface = new ArrayList();
		if(merchantCustomPayInterface!=null){
			BankBranch bankBranch = bankBranchFacade.getByFrpCode(merchantCustomPayInterface.getPayWay());
			if(bankBranch != null){
				listPayInterface.add(bankBranch.getDefaultBankChannelCode());
				listPayInterface.add(bankBranch.getSpareBankChannelCode());
			}
		}		
		this.putData("listPayInterface", listPayInterface);
		this.pushData(merchantCustomPayInterface);
		
		return "merchantCustomPayInterfaceEdit";
	}
	/**
	 * 修改支付接口路由
	 * @return
	 */
	@Permission("limit:payinteface:edit")
	public String editMerchantCustomPayInterface(){
			Long id = getLong("id");
			MerchantCustomPayInterface entity = null;
			if(id!=null){
				entity = merchantCustomPayInterfaceFacade.getById(id);
			}else{
				entity = new MerchantCustomPayInterface();
			}
			String merchantNo = getString("merchantNo");
			String payWay = getString("payWay");
			String payInterface = getString("payInterface");
			MerchantCustomPayInterface merchantCustomPayInterface = 
			merchantCustomPayInterfaceFacade.getByMerchantNoPayWayAndPayInterface(merchantNo, payWay, payInterface);
			if (merchantCustomPayInterface!=null) {
				return this.operateError("该支付接口路由限制已存在");
			}
			entity.setMerchantNo(merchantNo);
			entity.setPayWay(payWay);
			entity.setPayInterface(payInterface);
			entity.setStatus(MerchantCustomPayInterfaceStatusEnum.ACTIVITY);
			if(id!=null){
				merchantCustomPayInterfaceFacade.updateMerchantCustomPayInterface(entity);
				this.logEdit("修改支付接口路由限制成功：商户编号为" + entity.getMerchantNo() +",接口编码为"+entity.getPayInterface() );
			}else{
				merchantCustomPayInterfaceFacade.saveMerchantCustomPayInterface(entity);
				this.logSave("添加支付接口路由限制成功：商户编号为" + entity.getMerchantNo() +",接口编码为"+entity.getPayInterface() );
			}
			return this.operateSuccess("修改成功！");		
	}
	
	/**
	 * 删除支付接口路由
	 */
	@Permission("limit:payinteface:edit")
	public void deleteMerchantCustomPayInterface(){
		try {
			Long id = getLong("id");
			merchantCustomPayInterfaceFacade.deleteMerchantCustomPayInterface(id);
//			MerchantCustomPayInterface entity = merchantCustomPayInterfaceFacade.getById(id);
//			entity.setStatus(MerchantCustomPayInterfaceStatusEnum.DELETED);
//			merchantCustomPayInterfaceFacade.updateMerchantCustomPayInterface(entity);
			this.getOutputMsg().put("STATE", "SUCCESS");
			this.logDelete("删除支付接口路由成功：ID=" + id);
		} catch (Exception e) {
			this.logDeleteError("删除支付接口路由失败：ID=" +  getLong("id"));
			this.getOutputMsg().put("STATE", "ERROR");
		}
		this.outPrint(this.getHttpResponse(),
				JSONObject.fromObject(this.getOutputMsg()));
		
	}
	
	
	/***
	 * 支付接口
	 */
	@SuppressWarnings("rawtypes")
	public void loadPayInterface() {
		HttpServletResponse resp = null;
		try {
			resp = getHttpResponse();
			resp.setCharacterEncoding("utf-8");
			String payWay = getString("payWay");// 支付方式
			JSONArray json = null;
			List list = new ArrayList();
			BankBranch bankBranch = bankBranchFacade.getByFrpCode(payWay);
			if(bankBranch != null){
				list.add(bankBranch.getDefaultBankChannelCode());
				list.add(bankBranch.getSpareBankChannelCode());
			}
			json = JSONArray.fromObject(list);
			resp.getWriter().write(json.toString());
			
		} catch (Exception e) {
			log.error("loadPayInterface fail:", e);
		} finally {
			try {
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
