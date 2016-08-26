package wusc.edu.pay.web.boss.action.remit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitChannelFacade;
import wusc.edu.pay.facade.remit.service.RemitChannelFlowRuleFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 打款分流规则
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleAction.java
 * @Date： 2014-7-29 下午2:48:58 
 * @version：  V1.0
 */
public class RemitChannelFlowRuleAction extends BossBaseAction {

	private static final long serialVersionUID = 5313872713673929359L;
	private static final Log log = LogFactory.getLog(RemitChannelFlowRuleAction.class);
	@Autowired
	private RemitChannelFlowRuleFacade remitChannelFlowRuleFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private RemitChannelFacade remitChannelFacade;
	
	/**
	 * 打款分流规则查询
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:view")
	public String listRemitChannelFlowRule(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelCode", getString("channelCode")); // 打款通道编码
		log.info("getString(channelCode)" + getString("channelCode"));
		super.pageBean = remitChannelFlowRuleFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); 
		this.putData("remitUrgentEnumList", RemitUrgentEnum.toList());
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		List<RemitChannel> remitChannelList = remitChannelFacade.listBy(new HashMap<String, Object>());
		this.putData("remitChannelList", remitChannelList);
		return "remitChannelFlowRuleList";
	}
	
	/**
	 * 跳转到新增页面
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:add")
	public String addRemitChannelFlowRuleUI(){
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("remitUrgentEnumList",RemitUrgentEnum.toList());
		return "remitChannelFlowRuleAdd";
	}
	
	/**
	 * 新增分流规则
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:add")
	public String addRemitChannelFlowRule(){
		RemitChannelFlowRule remitChannelFlowRule = creatObject();
		String msg = validateRemitChannelFlowRule(remitChannelFlowRule);
		if(remitChannelFlowRuleFacade.getByChannelCode(remitChannelFlowRule.getChannelCode())!=null){
			log.info("====error==== 该打款通道("+remitChannelFlowRule.getChannelCode()+")的分流规则已经存在");
			return operateError("该打款通道的分流规则已经存在");
		}
		
		if(StringUtils.isBlank(msg)){
			remitChannelFlowRuleFacade.creat(remitChannelFlowRule);
			super.logSave("新增分流规则" + remitChannelFlowRule.getChannelCode());
			return operateSuccess();
		}else{
			log.info("异常数据" + msg);
			return operateError(msg);
		}
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:edit")
	public String editRemitChannelFlowRuleUI(){
		long id = getLong("id");
		if(id != 0){
			RemitChannelFlowRule remitChannelFlowRule = remitChannelFlowRuleFacade.getById(id);
			if(remitChannelFlowRule == null){
				return operateError("不存在该分流规则");
			}else{
				String[] includeBankTypeCodes = remitChannelFlowRule.getIncludeBankCode()==null?null:remitChannelFlowRule.getIncludeBankCode().split(",");
				String[] excludeBankTypeCodes = remitChannelFlowRule.getExcludeBankCode()==null?null:remitChannelFlowRule.getExcludeBankCode().split(",");
				StringBuffer includeBankCode = new StringBuffer();
				StringBuffer excludeBankCode = new StringBuffer();
				if(includeBankTypeCodes!=null){
					for(String includeBankTypeCode:includeBankTypeCodes){
						includeBankCode.append(remitBankTypeFacade.getByTypeCode(includeBankTypeCode).getBankCode()).append(",");
					}
				}
				if(excludeBankTypeCodes!=null){
					for(String excludeBankTypeCode:excludeBankTypeCodes){
						excludeBankCode.append(remitBankTypeFacade.getByTypeCode(excludeBankTypeCode).getBankCode()).append(",");
					}
				}
			
				this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
				this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
				this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
				this.putData("remitUrgentEnumList",RemitUrgentEnum.toList());
				this.pushData(remitChannelFlowRule);
				this.putData("includeBankCodes", includeBankCode);
				this.putData("excludeBankCodes", excludeBankCode);
			}
		}
		return "remitChannelFlowRuleEdit";
	}
	
	/**
	 * 修改分流规则
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:edit")
	public String editRemitChannelFlowRule(){
		RemitChannelFlowRule remitChannelFlowRule = creatObject();
		String msg = validateRemitChannelFlowRule(remitChannelFlowRule);
		if(StringUtils.isBlank(msg)){
			remitChannelFlowRuleFacade.update(remitChannelFlowRule);
			super.logEdit("修改分流规则" + remitChannelFlowRule.getChannelCode());
			return operateSuccess();
		}else{
			log.info("异常数据" + msg);
			return operateError(msg);
		}
	}
	
	/**
	 * 跳转到查看页面
	 * @return
	 */
	@Permission("boss:remitChannelFlowRule:view")
	public String viewRemitChannelFlowRuleUI(){
		long id = getLong("id");
		if(id != 0){
			RemitChannelFlowRule remitChannelFlowRule = remitChannelFlowRuleFacade.getById(id);
			if(remitChannelFlowRule == null){
				return operateError("不存在该分流规则");
			}else{
				String[] includeBankTypeCodes = remitChannelFlowRule.getIncludeBankCode()==null?null:remitChannelFlowRule.getIncludeBankCode().split(",");
				String[] excludeBankTypeCodes = remitChannelFlowRule.getExcludeBankCode()==null?null:remitChannelFlowRule.getExcludeBankCode().split(",");
				StringBuffer includeBankCode = new StringBuffer();
				StringBuffer excludeBankCode = new StringBuffer();
				if(includeBankTypeCodes!=null){
					for(String includeBankTypeCode:includeBankTypeCodes){
						includeBankCode.append(remitBankTypeFacade.getByTypeCode(includeBankTypeCode).getBankCode()).append(",");
					}
				}
				if(excludeBankTypeCodes!=null){
					for(String excludeBankTypeCode:excludeBankTypeCodes){
						excludeBankCode.append(remitBankTypeFacade.getByTypeCode(excludeBankTypeCode).getBankCode()).append(",");
					}
				}
			
				this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
				this.putData("tradeTypeEnumList", TradeTypeEnum.toList());
				this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
				this.putData("remitUrgentEnumList",RemitUrgentEnum.toList());
				this.pushData(remitChannelFlowRule);
				this.putData("includeBankCodes", includeBankCode);
				this.putData("excludeBankCodes", excludeBankCode);
			}
		}
		return "remitChannelFlowRuleView";
	}
	
	/**
	 * 银行选择页面
	 * @return
	 */
	public String bankChannelSelectList(){
		String includeBankStr = getString("includeBankStr");
		String excludeBankStr = getString("excludeBankStr");
		List<RemitBankType> includeList = null;
		List<RemitBankType> excludeList = null;
		List<RemitBankType> allActiveBankList = null;
		List<String> noInList = new ArrayList<String>();
		
		/**加载包含的银行*/
		if(StringUtils.isBlank(includeBankStr)){
			includeList = new ArrayList<RemitBankType>();
		}else{
			String[] includeStrArr = includeBankStr.split(",");
			List<String> lists = new ArrayList<String>();
			for(int i = 0 ; i < includeStrArr.length; i++){
				if(!StringUtils.isBlank(includeStrArr[i])){
					lists.add(includeStrArr[i]);
				}
			}
			noInList.addAll(lists);
			includeList = remitBankTypeFacade.listActiveBankByIn(lists);
		}
		/**加载不包含的银行*/
		if(StringUtils.isBlank(excludeBankStr)){
			excludeList = new ArrayList<RemitBankType>();
		}else{
			String[] excludeStrArr = excludeBankStr.split(",");
			List<String> lists = new ArrayList<String>();
			for(int i = 0 ; i < excludeStrArr.length; i++){
				if(!StringUtils.isBlank(excludeStrArr[i])){
					lists.add(excludeStrArr[i]);
				}
			}
			noInList.addAll(lists);
			excludeList = remitBankTypeFacade.listActiveBankByIn(lists);
		}
		if(noInList.size() == 0){
			allActiveBankList = remitBankTypeFacade.listActiveBank();
		}else{
			allActiveBankList = remitBankTypeFacade.listActiveBankByNotIn(noInList);
		}
		this.putData("includeList", includeList);
		this.putData("excludeList", excludeList);
		this.putData("allActiveBankList", allActiveBankList);
		return "remitBankChannelSelectList";
	}
	
	/**
	 * 通过页面参数封装成一个对象
	 * @return
	 */
	private RemitChannelFlowRule creatObject(){
		RemitChannelFlowRule remitChannelFlowRule = new RemitChannelFlowRule();
		remitChannelFlowRule.setChannelFlowName(getString("channelFlowName"));
		remitChannelFlowRule.setId(getLong("id"));
		String[] accountType = getStringArr("accountType");
		if(accountType != null){
			String tempAccountType = "";
			for(String s : accountType){
				tempAccountType += s + ",";
			}
			remitChannelFlowRule.setAccountType(tempAccountType);//账户类型
		}
		remitChannelFlowRule.setChannelCode(getString("channelCode"));
		remitChannelFlowRule.setExcludeBankCode(getString("excludeBankTypeCode"));
		remitChannelFlowRule.setIncludeBankCode(getString("includeBankTypeCode"));
		remitChannelFlowRule.setVersion(getInteger("version"));
		if(getDouble("maxAmount") != null){
			remitChannelFlowRule.setMaxAmount(BigDecimal.valueOf(getDouble("maxAmount")));
		}
		if(getDouble("minAmount") != null){
			remitChannelFlowRule.setMinAmount(BigDecimal.valueOf(getDouble("minAmount")));
		}
		remitChannelFlowRule.setIsUrgent(getInteger("isUrgent"));
		remitChannelFlowRule.setStatus(getInteger("status"));
		String[] tradeTypeArr = getStringArr("tradeType");
		if(tradeTypeArr != null){
			String tempTradeTYpe = "";
			for(String s : tradeTypeArr){
				tempTradeTYpe += s + ",";
			}
			remitChannelFlowRule.setTradeType(tempTradeTYpe);
		}
		
		return remitChannelFlowRule;
	}
	
	private String validateRemitChannelFlowRule(RemitChannelFlowRule remitChannelFlowRule){
		if(StringUtils.isBlank(remitChannelFlowRule.getChannelFlowName())){
			return "分流规则名称不能为空";
		}
		if(StringUtils.isBlank(remitChannelFlowRule.getChannelCode())){
			return "打款通道编号不能为空";
		}
		if(remitChannelFlowRule.getStatus() == null){
			return "状态选择有误";
		}
		if(StringUtils.isBlank(remitChannelFlowRule.getTradeType())){
			return "业务类型选择有误";
		}
		if(StringUtils.isBlank(remitChannelFlowRule.getAccountType())){
			return "账户类型选择有误";
		}
		if(StringUtils.isBlank(remitChannelFlowRule.getIncludeBankCode())){
			return "包含的收款银行有误";
		}
		if(remitChannelFlowRule.getMinAmount() == null){
			return "最小金额有误";
		}
		if(remitChannelFlowRule.getMaxAmount() == null){
			return "最大金额有误";
		}
		return "";
	}
}
