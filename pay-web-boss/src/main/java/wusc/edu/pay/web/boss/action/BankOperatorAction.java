package wusc.edu.pay.web.boss.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.entity.BankAgreement;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;
import wusc.edu.pay.facade.bank.service.BankAgreementFacade;
import wusc.edu.pay.facade.bank.service.BankChannelFacade;
import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Town;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.ProvinceFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.util.PinYin4jUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 银行Action,用于对银行相关业务的增删查改
 * 
 * @author zenghao
 * @修改版本:1.1
 * @修改时间: 2013-07-16 .
 * @修改内容: 添加银行协议增删改查 .
 */
@Scope("prototype")
public class BankOperatorAction extends BossBaseAction{
	private static final long serialVersionUID = -7152894960591833784L;
	
	private static final Log log = LogFactory.getLog(BankOperatorAction.class);

	@Autowired
	private BankChannelFacade bankChannelFacade;
	@Autowired
	private BankAgreementFacade bankAgreementFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade; // 账户查询接口
	@Autowired
	private BankAccountFacade bankAccountFacade;
	@Autowired
	private ProvinceFacade provinceFacade;//省市区对外发布接口
	
	/**
	 * 获取银行渠道列表
	 * 
	 * @return listBankChannel or operateError.
	 */
	@Permission("bank:channel:view")
	public String listBankChannel() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankSequence", getString("bankSequence"));
		paramMap.put("bankChannelCode", getString("bankChannelCode")); // 银行渠道名称
		paramMap.put("bankName", getString("bankName")); // 银行渠道名称
		paramMap.put("bankChannelName", getString("bankChannelName")); // 银行渠道名称
		paramMap.put("landingBankName", getString("landingBankName")); // 银行渠道名称
		paramMap.put("status", getString("status")); // 银行渠道名称
		super.pageBean = bankChannelFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listBankChannel";
	}

	/**
	 * 转到添加银行渠道页面
	 * 
	 * @return addBankChannelUI or operateError.
	 */
	@Permission("bank:channel:add")
	public String addBankChannelUI() {
		// 获取银行列表
		Map<String, String> map = new Hashtable<String, String>();
		for (BankCode s : BankCode.values()) {
			map.put(s.name(), s.getDesc());
		}
		ActionContext.getContext().put("bankDictList", map);
		return "addBankChannelUI";
	}

	/**
	 * 保存新添加银行渠道信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:channel:add")
	public String addBankChannel() {
		Long bankAgreementID = getLong("bankAgreementId");
		if(bankAgreementID == null){
			return operateError("银行协议ID为空");
		}
		BankChannel bankChannelParam = bankChannelFacade.getByBankAgreementId(bankAgreementID);
		if(bankChannelParam != null){
			return operateError("该银行协议已被绑定，请重新选择");
		}
		BankChannel bankChannel = new BankChannel();
		String bankChannelCode = getString("bankChannelCode");//获得银行渠道编号
		BankChannel bankChannelCodeModel = bankChannelFacade.getByBankChannelCode(bankChannelCode);
		if (bankChannelCodeModel != null) {
			return operateError("【" + bankChannelCode + "】该银行渠道编号已经存在！");
		}
		bankChannel.setBankChannelCode(bankChannelCode); // set银行渠道编号
		String bankChannelName = getString("bankChannelName");//获得银行渠道名称
		BankChannel bankChannelNameModel = bankChannelFacade.getByBankChannelName(bankChannelName);
		if (bankChannelNameModel != null) {
			return operateError("【" + bankChannelName + "】该银行渠道名称已经存在！");
		}
		bankChannel.setBankChannelName(bankChannelName); // set银行渠道名称
		String bCode = getString("bankName");//获得银行名称
		Long bankAccountId = getLong("bankAccountId");
		if(bankAccountId == null){
			return operateError("请选择银行账户");
		}
		BankAccount bankAccountParam = bankAccountFacade.getById(bankAccountId);
		if(bankAccountParam == null){
			return operateError("选择的银行账户有误");
		}
		bankChannel.setBankAccountId(bankAccountId);
		BankCode bankCode = BankCode.valueOf(getString("bankCode"));//银行编号
		bankChannel.setBankName(bCode);
		bankChannel.setBankAgreementId(bankAgreementID);
		bankChannel.setBankCode(bankCode);
		bankChannel.setLandingBankName(getString("landingBankName"));// 落地行名称
		bankChannel.setRemark(getString("desc"));
		bankChannel.setStatus(getInteger("status"));
		String message = validateChannel(bankChannel);// 对表单提交过来的数据进行再次验证
		if (!message.isEmpty()) {
			throw new BossBizException(message);
		}
		long saveNum = bankChannelFacade.create(bankChannel);
		if(saveNum <= 0){
			super.logSaveError("新添加银行渠道.渠道编号["+bankChannelCode+"]");
			return operateError("创建银行渠道失败");
		}
		super.logSave("新添加银行渠道.渠道编号["+bankChannelCode+"]");
		return operateSuccess();
	}
	
	/**
	 * 查看银行渠道相关信息
	 * */
	@Permission("bank:channel:view")
	public String viewBankChannelUI(){
		Long bankChannelID = getLong("bankChannelID");
		if(bankChannelID == null){
			return operateError("银行渠道ID不能为空");
		}
		BankChannel bankChannelParam = bankChannelFacade.getById(bankChannelID);
		if(bankChannelParam == null){
			return operateError("不存该银行渠道");
		}
		this.pushData(bankChannelParam);
		return "viewBankChannel";
	}

	/**
	 * 转到银行渠道修改页面
	 * 
	 * @return editBankChannelUI or operateError.
	 */
	@Permission("bank:channel:edit")
	public String editBankChannelUI() {
		Long bankChannelID = getLong("bankChannelID");
		if(bankChannelID == null){
			return operateError("银行渠道ID不能为空");
		}
		BankChannel bankChannelParam = bankChannelFacade.getById(bankChannelID);
		if(bankChannelParam == null){
			return operateError("不存该银行渠道");
		}
		this.pushData(bankChannelParam);
		return "editBankChannelUI";
	}

	/**
	 * 保存修改后的银行渠道信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:channel:edit")
	public String editBankChannel() {
		String bankchannelCode = getString("bankchannelCode");
		if(null == bankchannelCode || "".equals(bankchannelCode)){
			return operateError("银行渠道编码为空");
		}
		BankChannel bankChannel = bankChannelFacade.getByBankChannelCode(bankchannelCode);
		if (bankChannel != null) {
			bankChannel.setStatus(getInteger("status"));
			bankChannel.setRemark(getString("desc"));
			long updateNum = bankChannelFacade.update(bankChannel);
			if(updateNum <= 0){
				super.logSaveError("修改加银行渠道.渠道编号["+bankchannelCode+"]");
				return operateError("修改银行渠道错误");
			}
			super.logSave("修改加银行渠道.渠道编号["+bankchannelCode+"]");
		}else{
			super.logSaveError("修改加银行渠道.渠道编号["+bankchannelCode+"]");
			return operateError("不存在该银行渠道");
		}
		return operateSuccess();
	}

	/**
	 * 删除银行渠道信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:channel:delete")
	public String deleteBankChannel() {
		String bankChannelCode = getString("bankChannelCode");
		bankChannelFacade.deleteChannelByCode(bankChannelCode);
		super.logDelete("删除银行渠道信息.银行渠道编号["+bankChannelCode+"]");
		return operateSuccess();
	}

	/**
	 * 获取银行资金账户列表
	 * 
	 * @return listAccount or operateError.
	 */
	public String listAccount() {
		super.pageBean = accountQueryFacade.queryAccountHistoryListPage(getPageParam(), null);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "listAccount";
	}

	/**
	 * 转到添加银行资金账户页面
	 * 
	 * @return addAccountUI
	 */
	public String addAccountUI() {
		
		return "addAccountUI";
	}

	/**
	 * 保存新添加银行资金账户信息
	 * 
	 * @return
	 */
	public String addAccount() {

		return operateSuccess();
	}

	/**
	 * 转到修改银行资金账户页面
	 * 
	 * @return
	 */
	public String editAccountUI() {

		return "editAccountUI";
	}

	/**
	 * 修改银行资金账户信息
	 * 
	 * @return
	 */
	public String editAccount() {

		return operateSuccess();
	}

	/**
	 * 删除银行资金账户信息
	 * 
	 * @return
	 */
	public String deleteAccount() {

		return operateSuccess();
	}

	/**
	 * 获取账户变动历史列表
	 * 
	 * @return listAccountHistory or operateError.
	 */
	public String listAccountHistory() {
		super.pageBean = accountQueryFacade.queryAccountHistoryListPage(getPageParam(), null);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "listAccountHistory";
	}

	/**
	 * 获取银行协议列表
	 * 
	 * @return listBankAgreement or operateError.
	 */
	@Permission("bank:agreement:view")
	public String listBankAgreement() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", getString("merchantNo")); // 商户编号
		paramMap.put("id", getString("id")); // 商户编号
		paramMap.put("interfaceName", getString("interfaceName")); // 商户编号
		paramMap.put("bankSequence", getString("bankSequence")); // 商户编号
		paramMap.put("status", getString("status")); // 商户编号
		paramMap.put("linkType", getString("linkType")); // 业务类型
		paramMap.put("agreementNo", getString("agreementNo")); // 合同编号
		
		super.pageBean = bankAgreementFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listBankAgreement";
	}
	
	
	
	/**
	 * 获取银行协议列表，通过查找带回
	 * 
	 * @return BankAgreementLookupList or operateError.
	 */
	public String BankAgreementLookupList() {
		// 查询参数集合
		super.pageBean = bankAgreementFacade.listPage(getPageParam(), null);
		this.pushData(pageBean);
		this.putData("bankCodeWithName", BankCode.toStringMap());
		return "BankAgreementLookupList";
	}
	
	
	/**
	 * 拼接省市区
	 * 
	 */
	public String getAddress(){

			// 查询省市区
			Province province  =  provinceFacade.getProvinceByCode(getString("provinceNo"));
			String provinceName = province.getProvinceName();
			City cityPram = provinceFacade.getCityByCode(getString("cityNo"));
			String cityName = cityPram.getCityName();
			Town TownPram = provinceFacade.getTownByCode(getString("areaNo"));
			String areaName = TownPram.getTownName();
			
			StringBuffer address = new StringBuffer();
			address.append(provinceName).append(cityName).append(areaName);
			
			return address.toString();
	}

	/**
	 * 获取银行渠道名称：
	 * 
	 * @return
	 */
	public String BankChannelLookuplist() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankChannelCode", getString("bankChannelCode")); // 银行渠道名称
		paramMap.put("bankName", getString("bankName")); // 银行渠道名称
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue()); // 状态为激活
		this.pushData(paramMap);
		super.pageBean = bankChannelFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		return "BankChannelLookuplist";
	}
	
	/**
	 * 转到添加银行协议页面
	 * 
	 * @return
	 */
	@Permission("bank:agreement:add")
	public String addBankAgreementUI() {
		this.putData("bankCodeWithName", BankCode.toStringMap());
		return "addBankAgreementUI";
	}

	/**
	 * 保存新添加银行协议信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:agreement:add")
	public String addBankAgreement() {
		String bankSequence = getString("bankSequence");
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("bankSequence", bankSequence); // 商户编号merchantNo
		PageBean pageBean2 = bankAgreementFacade.listPage(getPageParam(), paramMap2);
		if(pageBean2.getTotalCount() > 0){
			return operateError("已有银行序号为：" + bankSequence + "的数据");
		}
		BankAgreement bankAgreement = new BankAgreement();
		bankAgreement.setAgreementNo(getString("agreementNo"));
		bankAgreement.setRemark(getString("desc"));
		bankAgreement.setOnlineTime(DateUtils.getDateByStr(getString("onlineTime")) );
		bankAgreement.setOfflineTime(DateUtils.getDateByStr(getString("offlineTime")));
		bankAgreement.setMerchantNo(getString("merchantNo"));
		bankAgreement.setLinkMan(getString("linkMan"));
		bankAgreement.setLinkType(getInteger("linkType"));
		bankAgreement.setBankSequence(bankSequence);
		bankAgreement.setInterfaceName(getString("interfaceName"));
		bankAgreement.setAmountSystem(getDouble("amountSystem"));
		bankAgreement.setProvince(getString("province"));
		bankAgreement.setCity(getString("city"));
		bankAgreement.setArea(getString("area"));
		bankAgreement.setBankCode(getString("bankCode"));//银行名称对应编码
		String[] communicationModeArr = getStringArr("communicationMode");
		if(communicationModeArr == null){
			return operateError("请选择通讯方式");
		}
		StringBuffer communicationMode = new StringBuffer("");
		for(String s : communicationModeArr){
			communicationMode.append("|").append(s);
		}
		bankAgreement.setCommunicationMode(communicationMode.toString().substring(1));
		
		bankAgreement.setCommunicationAddress(getString("communicationAddress"));
		bankAgreement.setContractOANo(getString("contractOANo"));
		
		
		String[] cardTypeArr = getStringArr("cardType");
		if(cardTypeArr == null){
			return operateError("请选择银行卡类别");
		}
		StringBuffer cardType = new StringBuffer("");
		for(String s : cardTypeArr){
			cardType.append("|").append(s);
		}
		bankAgreement.setCardType(cardType.toString());
		
		/*try {
			bankAgreement.setOnlineTime(DateUtils.parseDate(getString("onlineTime"), DateUtils.DATE_FORMAT_DATEONLY));
			bankAgreement.setOffLineTime(DateUtils.parseDate(getString("offLineTime"), DateUtils.DATE_FORMAT_DATEONLY));
		} catch (ParseException e) {
			log.info("日期格式不正确",e);
		}*/
		
		
		String[] merchantTypeArr = getStringArr("merchantType");
		if(merchantTypeArr == null){
			return operateError("请选择商户类型");
		}
		StringBuffer merchantType = new StringBuffer("");
		for(String s : merchantTypeArr){
			merchantType.append("|").append(s);
		}
		bankAgreement.setMerchantType(merchantType.toString()); // 商户类型
		
		String message = validateAgreement(bankAgreement);// 对表单提交过来的数据进行再次验证
		if (!message.isEmpty()) {
			throw new BossBizException(message);
		}
		long num = bankAgreementFacade.create(bankAgreement);
		if(num == 0){
			super.logSaveError("新添加银行协议.银行序号["+bankAgreement.getBankSequence()+"]");
			return operateError("创建银行协议失败");	
		}
		super.logSave("新添加银行协议.银行序号["+bankAgreement.getBankSequence()+"]");
		return operateSuccess();
	}

	/**
	 * 查看银行协议详情.
	 * @return
	 */
	@Permission("bank:agreement:view")
	public String viewBankAgreement(){
		Long id = getLong("id");
		BankAgreement bankAgreement = bankAgreementFacade.getById(id);
		ActionContext.getContext().getValueStack().push(bankAgreement);
		this.putData("bankCodeWithName", BankCode.toStringMap());
		// 查询省市区
		String provinceName = "";
		String cityName = "";
		String areaName = "";
		if (null != bankAgreement.getProvince()) {
			Province province = provinceFacade
					.getProvinceByCode(bankAgreement.getProvince());
			if (province != null) {
				provinceName = province.getProvinceName();
			}
		}
		if (null != bankAgreement.getCity()) {
			City city = provinceFacade.getCityByCode(bankAgreement
					.getCity());
			if (city != null) {
				cityName = city.getCityName();
			}
		}
		if (null != bankAgreement.getArea()) {
			Town town = provinceFacade.getTownByCode(bankAgreement
					.getArea());
			if (town != null) {
				areaName = town.getTownName();
			}
		}
		this.putData("provinceName", provinceName);
		this.putData("cityName", cityName);
		this.putData("areaName", areaName);
		return "viewBankAgreement";
	}
	
	/**
	 * 转到修改银行协议页面
	 * 
	 * @return
	 */
	@Permission("bank:agreement:edit")
	public String editBankAgreementUI() {
		Long id = getLong("id");
		BankAgreement bankAgreement = bankAgreementFacade.getById(id);
		ActionContext.getContext().getValueStack().push(bankAgreement);
		this.putData("bankCodeWithName", BankCode.toStringMap());
		// 查询省市区
		String provinceName = "";
		String cityName = "";
		String areaName = "";
		if (null != bankAgreement.getProvince()) {
			Province province = provinceFacade
					.getProvinceByCode(bankAgreement.getProvince());
			if (province != null) {
				provinceName = province.getProvinceName();
			}
		}
		if (null != bankAgreement.getCity()) {
			City city = provinceFacade.getCityByCode(bankAgreement
					.getCity());
			if (city != null) {
				cityName = city.getCityName();
			}
		}
		if (null != bankAgreement.getArea()) {
			Town town = provinceFacade.getTownByCode(bankAgreement
					.getArea());
			if (town != null) {
				areaName = town.getTownName();
			}
		}
		this.putData("provinceName", provinceName);
		this.putData("cityName", cityName);
		this.putData("areaName", areaName);
		return "editBankAgreementUI";
	}

	/**
	 * 保存修改后银行协议信息
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("bank:agreement:edit")
	public String editBankAgreement() {
		String bankSequence = getString("bankSequence");
		BankAgreement bankAgreement = bankAgreementFacade.getById(getLong("id"));
		if (bankAgreement != null) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("bankSequence", bankSequence); // 商户编号
					super.pageBean = bankAgreementFacade.listPage(getPageParam(), paramMap);
					if(pageBean.getTotalCount() < 0){
						return operateError("不存在银行序号为：" + bankSequence + "的数据");
					}
			}
			bankAgreement.setAgreementNo(getString("agreementNo"));
			bankAgreement.setRemark(getString("desc"));
			bankAgreement.setMerchantNo(getString("merchantNo"));
			bankAgreement.setLinkType(getInteger("linkType"));
			bankAgreement.setOnlineTime(DateUtils.getDateByStr(getString("onlineTime")) );
			bankAgreement.setOfflineTime(DateUtils.getDateByStr(getString("offlineTime")));
			bankAgreement.setBankSequence(getString("bankSequence"));
			bankAgreement.setInterfaceName(getString("interfaceName"));
			bankAgreement.setLinkMan(getString("linkMan"));
			bankAgreement.setAmountSystem(getDouble("amountSystem"));
			String[] communicationModeArr = getStringArr("communicationMode");
			StringBuffer communicationMode = new StringBuffer("");
			if(communicationModeArr != null){
				for(String s : communicationModeArr){
					communicationMode.append("|").append(s);
				}
			}
			bankAgreement.setCommunicationMode(communicationMode.toString());
			bankAgreement.setCommunicationAddress(getString("communicationAddress"));
			bankAgreement.setContractOANo(getString("contractOANo"));
			
			String[] cardTypeArr = getStringArr("cardType");
			StringBuffer cardType = new StringBuffer("");
			if(cardTypeArr != null){
				for(String s : cardTypeArr){
					cardType.append("|").append(s);
				}
			}
			bankAgreement.setCardType(cardType.toString());
			String[] merchantTypeArr = getStringArr("merchantType1");
			StringBuffer merchantType = new StringBuffer("");
			if(merchantTypeArr != null){
				for(String s : merchantTypeArr){
					merchantType.append("|").append(s);
				}
			}
			bankAgreement.setMerchantType(merchantType.toString()); // 商户类型
			
		String message = validateAgreement(bankAgreement);// 对表单提交过来的数据进行再次验证
		if (!message.isEmpty()) {
			throw new BossBizException(message);
		}
		long num = bankAgreementFacade.update(bankAgreement);
		if(num == 0){
			super.logEditError("修改银行协议信息.银行序号["+bankAgreement.getBankSequence()+"]");
			return operateError("修改银行协议失败");
		}
		super.logEdit("修改银行协议信息.银行序号["+bankAgreement.getBankSequence()+"]");
		return operateSuccess();
	}




	/**
	 * 验证合同里面字段
	 * 
	 * @param bankAgreement
	 * @return
	 */
	private String validateAgreement(BankAgreement bankAgreement) {
		
		if (StringUtils.isEmpty(bankAgreement.getMerchantNo())) {
			return "商户编号不能为空，";
		}
		if (StringUtils.isEmpty(bankAgreement.getAgreementNo())) {
			return "合同编号不能为空，";
		}
		if (StringUtils.isEmpty(bankAgreement.getBankCode())) {
			return "银行编号不能为空，";
		}
		if (StringUtils.isEmpty(bankAgreement.getBankSequence())) {
			return "银行序号不能为空，";
		}
		if (StringUtils.isEmpty(bankAgreement.getInterfaceName())) {
			return "接口不能为空，";
		}
		if(bankAgreement.getOnlineTime() == null){
			return "上线时间不能为空，";
		}
		if(bankAgreement.getOfflineTime() == null){
			return "下线时间不能为空，";
		}
		if (bankAgreement.getOnlineTime().after(bankAgreement.getOfflineTime())) {
			return "上线时间不能晚于下线时间，";
		}
		if (StringUtils.isEmpty(bankAgreement.getCardType())) {
			return "卡种不能为空，";
		}
		if (StringUtils.isEmpty(bankAgreement.getMerchantType())) {
			return "支持商户类型不能为空，";
		}
			if (bankAgreement.getLinkType() == null || bankAgreement.getLinkType() == 0) {
				return "B2B/B2C不能为空，";
			}
		if(bankAgreement.getAmountSystem() == null || bankAgreement.getAmountSystem().isNaN()){
			return "大小额限额点不正确，";
		}
		if (StringUtils.isEmpty(bankAgreement.getCommunicationMode())) {
			return "通讯方式不能为空";
		}
		if (StringUtils.isEmpty(bankAgreement.getCommunicationAddress())) {
			return "通讯地址不能为空";
		}
		if (StringUtils.isEmpty(bankAgreement.getLinkMan())) {
			return "银行联系人方式不能为空";
		}
		if(StringUtils.isEmpty(bankAgreement.getProvince()) || "0".equals(bankAgreement.getProvince())){
			return "通讯地址错误";
		}
		if(StringUtils.isEmpty(bankAgreement.getCity()) || "0".equals(bankAgreement.getCity())){
			return "通讯地址错误";
		}
		if(StringUtils.isEmpty(bankAgreement.getArea()) || "0".equals(bankAgreement.getArea())){
			return "通讯地址错误";
		}
		return "";
	}

	/**
	 * 银行渠道验证
	 * 
	 * @author shenjialong
	 * @return
	 */
	private String validateChannel(BankChannel bankChannel) {
		String errmeg = "";
		if (bankChannel.getBankChannelCode().isEmpty() || bankChannel.getBankChannelName().isEmpty()) {
			errmeg += "银行渠道编号,银行渠道名字不能为空，";
		}
		/*if (bankChannel.getBankCode().getDesc() != bankChannel.getBankName()) {
			errmeg += "银行编号和银行名字不对应，";
		}
		if ("".equals(bankChannel.getBankCode()) || bankChannel.getBankCode() == null) {
			errmeg += "请选择银行名称";
		}*/
		if ("".equals(bankChannel.getLandingBankName()) || bankChannel.getLandingBankName() == null) {
			errmeg += "请输入落地行名称";
		}
		return errmeg;
	}
	

	
	/***
	 * 获取省市区
	 */
	@SuppressWarnings("unchecked")
	public void loadAddressInfo() {
		HttpServletResponse resp = null;
		try {
			resp = getHttpResponse();
			resp.setCharacterEncoding("utf-8");
			Integer provinceNo = getInteger("provinceNo");// 省
			Integer cityNo = getInteger("cityNo");// 市
			JSONArray json = null;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (!ValidateUtils.isEmpty(provinceNo) && provinceNo != 0) {
				paramMap.put("provinceNo", provinceNo);
				List<City> list = provinceFacade.listCityBy(paramMap);
				json = JSONArray.fromObject(list);
			} else if (!ValidateUtils.isEmpty(cityNo) && cityNo != 0) {
				paramMap.put("cityNo", cityNo);
				List<Town> list = provinceFacade.listTownBy(paramMap);
				json = JSONArray.fromObject(list);
			} else {
				List<Province> list = provinceFacade.listProvince(paramMap);
				json = JSONArray.fromObject(list);
			}
			resp.getWriter().write(json.toString());
		} catch (Exception e) {
			log.error("AddressAction fail:", e);
		} finally {
			try {
				if(resp != null && resp.getWriter() != null){
					resp.getWriter().close();
				}
			} catch (IOException e) {
				log.error("IOException fail:", e);
			}
		}
	}
	
	/***
	 * 分别获取省市区
	 * @author lichao
	 */
	
	public void getAddressInfo() {
		HttpServletResponse resp = null;
		try {
			resp = getHttpResponse();
			resp.setCharacterEncoding("utf-8");
			JSONArray json = null;
			
			Province province = provinceFacade.getProvinceByCode( getString("provinceNo"));
			City city = provinceFacade.getCityByCode(getString("cityNo"));
			Town town = provinceFacade.getTownByCode(getString("areaNo"));
			log.info("provinceParam.getProvinceName()" + province.getProvinceName());
			log.info("cityParam.getCityName()" + city.getCityName());
			log.info("townParam.getTownName()" + town.getTownName());
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(province.getProvinceName());
			arrayList.add(city.getCityName());
			arrayList.add(town.getTownName());
			String cityName = "";
			if("市辖区".equals(city.getCityName())){
				cityName = PinYin4jUtil.getFirstPinyin(province.getProvinceName());
			}else{
				cityName = PinYin4jUtil.getFirstPinyin(city.getCityName());
			}
			String bankCode = getString("bankChannelCode");
			String linkType = getString("linkType");
			String bankChannelCode = "";
			log.info(linkType);
			if("_FAST".equals(linkType)){
				bankChannelCode = bankCode + linkType;
				log.info(bankChannelCode );
			}else{
				bankChannelCode = bankCode + linkType + "_" + cityName;
			}
			BankChannel bankChannelParam = bankChannelFacade.getByBankChannelCode(bankChannelCode);
			if(bankChannelParam != null){
				bankChannelCode = bankChannelCode + PinYin4jUtil.getFirstPinyin(province.getProvinceName());
			}
			log.info("bankChannelCode : " + bankChannelCode);
			arrayList.add(bankChannelCode);
			json = JSONArray.fromObject(arrayList);
			
			resp.getWriter().write(json.toString());
		} catch (Exception e) {
			log.error("AddressAction fail:", e);
		} finally {
			try {
				if(resp != null && resp.getWriter() != null){
					resp.getWriter().close();
				}
			} catch (IOException e) {
				log.error("IOException fail:", e);
			}
		}
	}
	
}
