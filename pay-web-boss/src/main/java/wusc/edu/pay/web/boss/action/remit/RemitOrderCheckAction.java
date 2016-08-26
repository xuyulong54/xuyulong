package wusc.edu.pay.web.boss.action.remit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * 制单审核action
 * @author： Peter
 * @ClassName: RemitOrderCheckAction.java
 * @Date： 2014-8-4 上午11:44:25 
 * @version：  V1.0
 */
public class RemitOrderCheckAction extends BossBaseAction {
	private static final long serialVersionUID = -5545306444237674282L;
	
	private static final Log log = LogFactory.getLog(RemitOrderCheckAction.class);
	
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	@Autowired
	private RemitFacade remitFacade;
	
	/**
	 * 审核列表查询
	 * @return
	 */
	@Permission("boss:remitOrderCheck:view")
	public String listRemitOrderCheck(){
		PageParam pageParam = getPageParam();
		String requestNo = getString("requestNo");
		String flowNo = getString("flowNo");
		String bankName = getString("bankName");
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));
		paramMap.put("requestNo", requestNo);
		paramMap.put("flowNo", flowNo);
		paramMap.put("bankName", bankName);
		paramMap.put("status", RemitRequestStatusEnum.UNAUDIT.getValue());
		super.pageBean = remitRequestFacade.listPage(pageParam, paramMap);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.values());
		this.putData("remitUrgentEnumList", RemitUrgentEnum.toList());
		this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
		this.putData("remitRequestStatusEnumList", RemitRequestStatusEnum.toList());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		this.pushData(pageBean);
		this.pushData(paramMap); 
		return "remitOrderCheckList";
	}
	
	/**
	 * 审核处理
	 * @return
	 */
	@Permission("boss:remitOrderCheck:check")
	public String remitDoCheck(){
		Integer flag = getInteger("flag");
		log.info(String.format("======> 开始制单审核，审核类型是[%s]", flag == 1 ? "批量" : "单笔"));
		if(flag == null ){
			return operateError("异常数据 ");
		}
		PmsOperator pmsOperator = getLoginedOperator();
		String checkLoginName = pmsOperator.getLoginName();  // 审核人
		String passOrders = getString("passOrders");
		
		if(!StringUtils.isBlank(passOrders)){
			String[] ids = passOrders.split("\\|");
			remitFacade.checkSuccess(ids, checkLoginName);
			this.logSave("手动审核打款请求："+passOrders);
		}
		
		String rejectOrders = getString("rejectOrders");
		if(!StringUtils.isBlank(rejectOrders)){
			String[] ids = rejectOrders.split("\\|");
			remitFacade.checkFail(ids, checkLoginName);
			this.logSave("手动审核打款请求："+rejectOrders);
		}
		
		//flag == 1 时 ，批量处理 ； flag == 2时，单笔处理
		if(flag.intValue() == 1){
			return listRemitOrderCheck();
		}else if(flag.intValue() == 2){
			return operateSuccess();
		}else{
			return operateError("异常数据 ");
		}
	}
	
	/**
	 * 明细查询
	 * @return
	 */
	@Permission("boss:remitOrderCheck:view")
	public String viewRemitDetail(){
		Long id = getLong("id");
		RemitRequest remitRequest = remitRequestFacade.getById(id);
		if(remitRequest != null){
			this.putData("RemitProcessStatusEnums", RemitRequestStatusEnum.values());
			this.putData("TradeSourcesEnums", TradeSourcesEnum.values());
			this.putData("AccountTypeEnums", BankAccountTypeEnum.toRemitList());
			this.putData("PublicStatusEnums", PublicStatusEnum.toIsOrNotList());
			this.putData("TradeTypeEnums", TradeTypeEnum.values());
			this.pushData(remitRequest);
		}else{
			return operateError("异常数据");
		}
		return "remitDetailView";
	}
	
}
