package wusc.edu.pay.web.boss.action.remit;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitChannelFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * @Title: 打款通道Action
 * @Description:
 * @author zzh
 * @date 2014-7-25 下午3:43:13
 */
public class RemitChannelAction extends BossBaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RemitChannelAction.class);

	@Autowired
	private RemitChannelFacade remitChannelFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private BankAccountFacade bankAccountFacade;

	/**
	 * @Title: 查询打款通道
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:view")
	public String remitChannelList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelCode", getString("channelCode"));
		paramMap.put("channelName", getString("channelName"));
		paramMap.put("bankTypeCode", getString("bankTypeCode"));
		super.pageBean = remitChannelFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("remitBankTypeList", remitBankTypeList);
		return "remitChannelList";
	}

	/**
	 * @Title: 打款通道添加页面
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:add")
	public String remitChannelAdd() {
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		return "remitChannelAdd";
	}

	/**
	 * @Title: 收款账户信息查找带回
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:view")
	public String remitSrcAccountInfo() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String openBank = null;
			if(getString("openBank")!=null){
				openBank = URLDecoder.decode(getString("openBank"), "UTF-8");
			}
			
			paramMap.put("openBank", openBank);
			super.pageBean = bankAccountFacade.listPage(getPageParam(), paramMap);
			this.pushData(pageBean);
		} catch (Exception e) {
			log.error("RemitChannelAction remitSrcAccountInfo Exception", e);
		}
		return "remitSrcAccountInfo";
	}

	/**
	 * @Title: 打款通道保存
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:add")
	public String remitChannelSave() {
		String channelCode = getString("channelCode");
		RemitChannel tempRemitChannel = remitChannelFacade.getByChannelCode(channelCode);
		if(tempRemitChannel != null){
			return operateError("【"+channelCode+"】已存在");
		}
		RemitChannel remitChannel = new RemitChannel();
		try {
			remitChannel.setChannelCode(getString("channelCode"));
			remitChannel.setChannelName(getString("channelName"));
			remitChannel.setAccountType(getInteger("accountType"));
			remitChannel.setBankTypeCode(getString("bankTypeCode"));
			if(getDouble("minAmount")!=null){
				remitChannel.setMinAmount(BigDecimal.valueOf(getDouble("minAmount")));
			}
			if(getDouble("maxAmount")!=null){
				remitChannel.setMaxAmount(BigDecimal.valueOf(getDouble("maxAmount")));
			}
			if(getDouble("batchMinAmount")!=null){
				remitChannel.setBatchMinAmount(BigDecimal.valueOf(getDouble("batchMinAmount")));
			}
			if(getDouble("batchMaxAmount")!=null){
				remitChannel.setBatchMaxAmount(BigDecimal.valueOf(getDouble("batchMaxAmount")));
			}
			remitChannel.setIsAutoRemit(getInteger("isAutoRemit"));
			remitChannel.setIsConfirm(getInteger("isConfirm"));
			remitChannel.setIsUrgent(getInteger("isUrgent"));
			remitChannel.setLimitNum(getLong("limitNum"));
			remitChannel.setSrcAccountName(getString("srcAccountTag.srcAccountName"));
			remitChannel.setSrcAccountNum(getString("srcAccountTag.srcAccountNum"));
			remitChannel.setSrcBankChannelNo(getString("srcAccountTag.srcBankChannelNo"));
			remitChannel.setSrcBankName(getString("srcAccountTag.srcBankName"));
			remitChannel.setStatus(getInteger("status"));
			remitChannelFacade.create(remitChannel);
			log.info("====info==== 创建打款通道成功，channelCode："+remitChannel.getChannelCode()+"，channelName："+remitChannel.getChannelName());
			this.logSave("添加打款通道，打款通道编号："+remitChannel.getChannelCode()+"，打款通道名称："+remitChannel.getChannelName());
			return operateSuccess();
		} catch (Exception e) {
			log.error("==remitChannelSave Exception", e);
			return operateError("保存出错");
		}
	}

	/**
	 * @Title: 打款通道修改 页面
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:edit")
	public String remitChannelEdit() {
		Long id = getLong("id");
		RemitChannel remitChannel = remitChannelFacade.getById(id);
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.pushData(remitChannel);
		return "remitChannelEdit";
	}

	/**
	 * @Title: 打款通道修改保存
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Permission("boss:remitChannel:edit")
	public String remitChannelUpdate() {
		Long id = getLong("id");
		RemitChannel remitChannel = remitChannelFacade.getById(id);
		try {
			remitChannel.setChannelCode(getString("channelCode"));
			remitChannel.setChannelName(getString("channelName"));
			remitChannel.setAccountType(getInteger("accountType"));
			remitChannel.setBankTypeCode(getString("bankTypeCode"));
			remitChannel.setMinAmount(BigDecimal.valueOf(getDouble("minAmount")));
			remitChannel.setMaxAmount(BigDecimal.valueOf(getDouble("maxAmount")));
			remitChannel.setBatchMinAmount(BigDecimal.valueOf(getDouble("batchMinAmount")));
			remitChannel.setBatchMaxAmount(BigDecimal.valueOf(getDouble("batchMaxAmount")));
			remitChannel.setIsAutoRemit(getInteger("isAutoRemit"));
			remitChannel.setIsConfirm(getInteger("isConfirm"));
			remitChannel.setIsUrgent(getInteger("isUrgent"));
			remitChannel.setLimitNum(getLong("limitNum"));
			remitChannel.setSrcAccountName(getString("srcAccountTag.srcAccountName"));
			remitChannel.setSrcAccountNum(getString("srcAccountTag.srcAccountNum"));
			remitChannel.setSrcBankChannelNo(getString("srcAccountTag.srcBankChannelNo"));
			remitChannel.setSrcBankName(getString("srcAccountTag.srcBankName"));
			remitChannel.setStatus(getInteger("status"));
			remitChannelFacade.update(remitChannel);
			this.logEdit("修改打款通道，打款通道编号："+getString("channelCode")+"，打款通道名称："+getString("channelName"));
			return operateSuccess();
		} catch (Exception e) {
			log.error("==remitChannelUpdate Exception", e);
			this.logEditError("修改打款通道，打款通道编号："+getString("channelCode")+"，打款通道名称："+getString("channelName"));
			return operateError("更新失败");
		}
	}

	/**
	 * @Title: 修改打款通道状态
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	@Permission("boss:remitChannel:edit")
	public void changeRemitChannelStatus() {
		long id = getLong("id");
		Integer status = getInteger("status");
		RemitChannel remitChannel = remitChannelFacade.getById(id);
		remitChannel.setStatus(status);
		remitChannelFacade.update(remitChannel);
		this.logEdit("修改打款通道状态，打款通道ID："+id+"，修改后的状态："+status);
		getOutputMsg().put("STATE", "SUCCESS");
		getOutputMsg().put("MSG", "修改成功");
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @Title: 删除打款通道
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	@Permission("boss:remitChannel:delete")
	public void deleteRemitChannel() {
		try {
			Long id = getLong("id");
			remitChannelFacade.deleteById(id);
			this.logDelete("删除打款通道，ID："+id);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "删除成功");

		} catch (Exception e) {
			log.error("==deleteRemitChannel Exception", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "删除失败");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
	
	/**
	 * 打款分流规则查找带回页面
	 * @return
	 */
	@Permission("boss:remitChannel:view")
	public String remitChannelFLowRuleLookUp(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("channelCode", getString("channelCode"));
		paramMap.put("channelName", getString("channelName"));
		paramMap.put("bankTypeCode", getString("bankTypeCode"));
		super.pageBean = remitChannelFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listAll();
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("remitBankTypeList", remitBankTypeList);
		return "remitChannelFLowRuleLookUp";
	}
}
