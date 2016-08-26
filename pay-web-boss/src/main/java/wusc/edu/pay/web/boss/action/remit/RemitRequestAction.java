/**
 * wusc.edu.pay.web.boss.action.remit.RemitRequestAction.java
 */
package wusc.edu.pay.web.boss.action.remit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * <ul>
 * <li>Title:制单记录</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-4
 */
public class RemitRequestAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RemitRequestAction.class);
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;
	
	@Autowired
	private RemitBankInfoFacade remitBankInfoFacade;
	
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	/**
	 * 查询制单记录
	 * 
	 * @return
	 */
	@Permission("boss:remitRequest:view")
	public String remitRequestList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));

		paramMap.put("tradeInitiator", getString("tradeInitiator"));// 收款发卡行
		paramMap.put("requestNo", getString("requestNo"));// 流水号
		paramMap.put("accountName", getString("accountName"));// 收款户名
		paramMap.put("accountNo", getString("accountNo"));// 收款账号
		paramMap.put("bankName", getString("bankName"));// 收款发卡行
		super.pageBean = remitRequestFacade.listPage(this.getPageParam(), paramMap);

		this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.values());
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitRequestStatusEnumList", RemitRequestStatusEnum.toList());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());

		this.pushData(pageBean);
		this.pushData(paramMap);

		return "remitRequestList";
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	@Permission("boss:remitRequest:view")
	public String remitRequestView() {
		Long id = getLong("id");
		if (ValidateUtils.isEmpty(id)) {
			return operateError("参数丢失");
		}
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if (ValidateUtils.isEmpty(remitRequest)) {
			return operateError("未查询到相关信息");
		}
		this.pushData(remitRequest);

		this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("AccountTypeEnums", BankAccountTypeEnum.values());
		this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
		this.putData("TradeTypeEnums", TradeTypeEnum.values());
		return "remitRequestView";
	}
	
	/**
	 * 跳转到单笔重新制单页面
	 * @return
	 */
	public String remitRequestSingleEditUI(){
		Long id = getLong("id");
		if (ValidateUtils.isEmpty(id)) {
			return operateError("参数丢失");
		}
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if (ValidateUtils.isEmpty(remitRequest)) {
			return operateError("未查询到相关信息");
		}
		this.pushData(remitRequest);

		this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("AccountTypeEnums", BankAccountTypeEnum.values());
		this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
		this.putData("TradeTypeEnums", TradeTypeEnum.values());
		
		this.putData("provinceList", remitBankAreaFacade.getProvince());
		this.putData("cityList", remitBankAreaFacade.getCityByProvince(remitRequest.getProvince()));
		this.putData("bankInfoList", remitBankInfoFacade.listByBankTypeCodeAndArea(remitRequest.getBankChannelNo().substring(0, 3) , remitRequest.getProvince(), remitRequest.getCity()));
		this.putData("bankTypeList" ,remitBankTypeFacade.listActiveBank());
		this.putData("typeCode", remitRequest.getBankChannelNo().substring(0, 3));
		
		return "remitRequestSingleEdit";
	}

	
	/**
	 * @Title: 根据银行行别和区域查询银行信息
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void getBankInfoListByBankTypeCodeAndArea() {
		
		String bankTypeCode = getString("bankTypeCode");
		String province = getString("province");
		String city = getString("city");
		getOutputMsg().put("city", city);
		getOutputMsg().put("bankTypeCode", bankTypeCode);
		
		try {
			getOutputMsg().put("cityList", remitBankAreaFacade.getCityByProvince(province));
			
			
			List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankTypeCodeAndArea(bankTypeCode,
					province, city);
			getOutputMsg().put("remitBankInfoList", remitBankInfoList);
			
			log.info("====info==== 查询银行信息，条件为bankTypeCode：" + bankTypeCode + ",province:" + province + ",city:" + city
					+ ",查到相关信息有" + remitBankInfoList.size() + "条");
		} catch (Exception e) {
			log.error("============= RemitOrderAction deleteRemitOrder Exception", e);
			getOutputMsg().put("cityList", null);
			getOutputMsg().put("remitBankInfoList", null);
		}
		
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}
	
	
	
	/**
	 * 查询重新制单记录
	 * 
	 * @return
	 */
	@Permission("boss:remitRequest:reView")
	public String reRemitRequestList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));

		List<String> statusList = new ArrayList<String>();
		// statusList.add(String.valueOf(RemitRequestStatusEnum.REMIT_FAILURE.getValue()));
		statusList.add(String.valueOf(RemitRequestStatusEnum.UNAPPROVE.getValue()));

		paramMap.put("tradeInitiator", getString("tradeInitiator"));// 收款发卡行
		paramMap.put("requestNo", getString("requestNo"));// 流水号
		paramMap.put("accountName", getString("accountName"));// 收款户名
		paramMap.put("bankChannelNo", getString("bankChannelNo"));// 收款账号
		paramMap.put("bankName", getString("bankName"));// 收款发卡行
		paramMap.put("copied", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("statusList", statusList);// 状态为：失败和审核不通过的数据
		super.pageBean = remitRequestFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.values());
		this.putData("remitRequestStatusEnumList", RemitRequestStatusEnum.toList());
		this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());

		return "reRemitRequestList";
	}

	/**
	 * 查询重新制单记录详情
	 * 
	 * @return
	 */
	@Permission("boss:remitRequest:reView")
	public String reRemitRequestView() {
		Long id = getLong("id");
		if (ValidateUtils.isEmpty(id)) {
			return operateError("参数丢失");
		}
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if (ValidateUtils.isEmpty(remitRequest)) {
			return operateError("未查询到相关信息");
		}
		this.pushData(remitRequest);

		this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
		this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
		this.putData("AccountTypeEnums", BankAccountTypeEnum.values());
		this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
		this.putData("TradeTypeEnums", TradeTypeEnum.values());
		return "reRemitRequestView";
	}
}
