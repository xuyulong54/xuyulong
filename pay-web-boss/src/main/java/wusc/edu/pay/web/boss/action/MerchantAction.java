package wusc.edu.pay.web.boss.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.MerchantBusTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.UUIDUitl;
import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.agent.enums.ApplyRateTypeEnum;
import wusc.edu.pay.facade.boss.entity.City;
import wusc.edu.pay.facade.boss.entity.MerchantSales;
import wusc.edu.pay.facade.boss.entity.Province;
import wusc.edu.pay.facade.boss.entity.Sales;
import wusc.edu.pay.facade.boss.entity.Town;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.MerchantSalesFacade;
import wusc.edu.pay.facade.boss.service.ProvinceFacade;
import wusc.edu.pay.facade.boss.service.SalesFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.service.TradeLimitRouterFacade;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;
import wusc.edu.pay.facade.payrule.service.PayRuleFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantTypeEnum;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * 商户信息管理Action .
 * 
 * @author ShenJialong.
 * 
 * @author WuShuicheng.<br/>
 * @修改内容：对Action方法名进行规范，同时完善异常处理.<br/>
 * @修改时间：2013-08-25.
 */
@Scope("prototype")
public class MerchantAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3466546469262984628L;

	private static Log LOG = LogFactory.getLog(MerchantAction.class);

	private final Integer TIME_OUT = 30; // 设置预警天数

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade; // 商户信息接口
	@Autowired
	private UserManagementFacade userManagementFacade; // 用户操作接口
	@Autowired
	private UserOperatorFacade userOperatorFacade; // 商户操作员接口
	@Autowired
	private ProvinceFacade provinceFacade;// 省市区服务接口
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private MerchantSalesFacade merchantSalesFacade;
	@Autowired
	private SalesFacade salesFacade;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private SettQueryFacade settQueryFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;
	@Autowired
	private PayRuleFacade payRuleFacade;
	@Autowired
	private FeeQueryFacade feeQueryFacade;
	@Autowired
	private TradeLimitRouterFacade tradeLimitRouterFacade;
	@Autowired
	private MailBiz mailBiz;

	/**
	 * 查询商户列表且 根据Id和LoginName查询商户的信息 .
	 * 
	 * @return listMerchant or operateError .
	 */
	@Permission("merchant:info:view")
	public String listMerchant() {
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", getString("merchantNo")); // 商户编号
		paramMap.put("fullName", getString("fullName")); // 商户名称
		paramMap.put("merchantType", getString("merchantType")); // 商户类型
		paramMap.put("shortName", getString("shortName")); // 商户简称
		paramMap.put("accountType", getString("accountType")); // 账户状态
		paramMap.put("status", getString("status"));
		paramMap.put("beginDate", beginDate); //
		paramMap.put("endDate", endDate); //

		super.pageBean = merchantOnlineFacade.listMerchantListPage(this.getPageParam(), paramMap);

		this.putData("merchantStatusList", MerchantStatusEnum.toList());// 商户状态
		this.putData("MerchantStatusEnums", MerchantStatusEnum.toMap());// 商户状态
		this.putData("merchantTypeEnums", MerchantTypeEnum.toMap());// 商户类型
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		this.putData("accountStatusList", AccountStatusEnum.toList());// 账户类型
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "listMerchant";
	}

	/**
	 * 审核商户信息列表
	 * 
	 * @return listMerchant or operateError .
	 */
	@Permission("merchant:info:auditMerchant")
	public String listAuditMerchant() {

		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		// 查询参数集合
		List<String> statusList = new ArrayList<String>();
		statusList.add(String.valueOf(MerchantStatusEnum.CREATED.getValue()));

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", getString("merchantNo")); // 商户编号
		paramMap.put("shortName", getString("shortName")); // 商户名称
		paramMap.put("merchantType", getString("merchantType")); // 商户类型
		paramMap.put("inStatus", statusList); // 状态，显示已创建和审核不通过的数据
		paramMap.put("beginDate", beginDate); // 开始时间
		paramMap.put("endDate", endDate); // 结束时间
		LOG.info("查询条件【" + paramMap + "】");
		super.pageBean = merchantOnlineFacade.listMerchantListPage(this.getPageParam(), paramMap);
		this.putData("merchantStatusList", MerchantStatusEnum.toList());// 商户状态
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		this.putData("accountStatusList", AccountStatusEnum.toList());// 账户类型
		this.putData("isAudit", getString("isAudit"));
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "merchantAuditList";
	}

	/***
	 * 商户提醒列表
	 * 
	 * @return
	 */
	@Permission("merchant:info:remindList")
	public String merchantRemindList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String remindType = getString("remindType"); // 过期提醒类型
		if (StringUtil.isEmpty(remindType)) {
			remindType = "1";
		}
		paramMap.put("remindType", remindType); // 过期提醒类型
		paramMap.put("merchantType", getString("merchantType")); // 商户类型
		paramMap.put("merchantNo", getString("merchantNo")); // 商户编号
		paramMap.put("fullName", getString("fullName")); // 登录名
		paramMap.put("status", MerchantStatusEnum.ACTIVE.getValue()); // 商户状态
		paramMap.put("merchantBusType", MerchantBusTypeEnum.ONLINE_MERCHANT.getValue()); // 查询在线商户数据
		super.pageBean = merchantOnlineFacade.listMerchantListPage(this.getPageParam(), paramMap);
		this.putData("merchantStatusList", MerchantStatusEnum.toList());// 商户状态
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "merchantRemindList";
	}

	/***
	 * 验证身份证号的存在性
	 */
	public void checkCardNo() {
		String cardNo = getString("cardNo");
		// 根据身份证号查询商户信息
		if (!StringUtil.isEmpty(cardNo)) {
			MerchantOnline merchant = merchantOnlineFacade.getMerchantOnlineByCardNo(cardNo);
			if (merchant == null) {
				// pw.write("1");
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "");
			} else {
				// pw.write("0");
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "身份证号[" + cardNo + "]已经被其他商户绑定，请查证后再输入");
			}
		} else {
			// pw.write("0");
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请输入身份证号");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 进入添加商户的页面 .<br/>
	 * 
	 * @return addMerchantUI .
	 */
	@Permission("merchant:info:add")
	public String addMerchantUI() {
		// 获取银行列表
		Map<String, String> map = new Hashtable<String, String>();
		for (BankCode s : BankCode.values()) {
			map.put(s.name(), s.getDesc());
		}
		this.putData("bankDictList", map);
		// 安全保护问题
		this.putData("securityQuestion", SecurityQuestionEnum.toList());

		// 对私账户
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());

		return "addMerchantUI";
	}

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		return ValidateUtils.isEmail(email);
	}

	/**
	 * 添加商户
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("merchant:info:add")
	public void addMerchant() {
		String loginName = getString("loginName"); // 登陆名称
		String fullName = getString("fullName"); // 全称
		// 商户注册时，随机生成商户的登录密码
		String loginPwd = UUIDUitl.generateInteger(8);
		// 随机生成支付密码
		String payPwd = UUIDUitl.generateInteger(8);
		// 封装商户信息
		MerchantOnline merchant = buildMerchantInfo(getString("bindMobileNo"), fullName, getString("certificateExpiry"), getString("licenseNoValid"), getInteger("merchantType"));
		// 对表单提交过来的数据进行校验
		String validateMsg = merchantValidate(merchant);
		UserInfo user = userQueryFacade.getUserInfoByLoginName(loginName);
		if (user != null) {
			validateMsg += "登录名【" + loginName + "】已存在";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("allName", fullName);
		List<MerchantOnline> signMerchant = merchantOnlineFacade.listBy(paramMap);
		if (signMerchant.size() > 0) {
			validateMsg += "该商户全称【" + fullName + "】已存在";
		}
		this.putData("loginName", loginName); // ??
		if (!StringUtil.isEmpty(validateMsg)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", validateMsg);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}

		// 封装用户信息表
		UserInfo userInfo = new UserInfo();
		userInfo.setLoginName(getString("loginName")); // 登录名
		userInfo.setAnswer(getString("anwser")); // 问题答案
		if (StringUtil.isEmpty(getString("bindEmail"))) {
			userInfo.setBindEmail(loginName); // 绑定邮箱默认为登录名
		} else {
			userInfo.setBindEmail(getString("bindEmail")); // 绑定邮箱默认为登录名
		}
		userInfo.setBindMobileNo(getString("bindMobileNo")); // 绑定手机
		userInfo.setCardNo(getString("cardNo")); // 身份证号
		userInfo.setCity(getString("city")); // 城市
		userInfo.setProvince(getString("province")); // 省份
		userInfo.setArea(getString("address")); // 区域
		userInfo.setQuestion(getString("question"));
		userInfo.setRealName(fullName); // 商户全称
		userInfo.setStatus(MerchantStatusEnum.CREATED.getValue()); // 状态，商户以创建

		// 创建商户，并创建商户的账户和进行业务设置
		long merchantId = userManagementFacade.createOnlineMerchant(merchant, userInfo, DigestUtils.sha1Hex(payPwd), DigestUtils.sha1Hex(loginPwd));

		this.putData("merchantNo", merchant.getMerchantNo()); // 商户编号
		super.logSave("添加商户.商户全称[" + merchant.getFullName() + "]");
		if (StringUtil.isNotNull(merchantId)) {
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", merchantId);
			getOutputMsg().put("FULLNAMEVALUE", fullName);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

			// 封装邮件发送
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("loginName", userInfo.getLoginName());
			paramModel.put("fullName", fullName);
			paramModel.put("loginPwd", loginPwd);
			paramModel.put("payPwd", payPwd);
			paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);

			// 通过模板生成邮件内容
			String content = "";
			try {
				paramModel.put("phone", EmailConst.PHONE);
				content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_ADD_SUCCESS, paramModel);
			} catch (Exception e) {
				LOG.error("邮件模板解释异常", e);
			}

			mailBiz.sendMail(userInfo.getBindEmail(), "添加商户成功", content);

		} else {
			super.logSaveError("添加商户.商户编号[" + merchant.getMerchantNo() + "]" + "发送邮件失败");
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "创建失败，服务异常，请稍后重试!");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 封装在线商户实体信息
	 * 
	 * @param loginName
	 * @param bindMobileNo
	 * @param signName
	 * @param cardNoValid
	 * @param licenseNoValid
	 * @return
	 */
	private MerchantOnline buildMerchantInfo(String bindMobileNo, String fullName, String certificateExpiry, String licenseNoValid, int merchantType) {
		MerchantOnline merchant = new MerchantOnline(); // 在线商户信息表
		if (certificateExpiry == null) {
			certificateExpiry = "2099-12-31";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		merchant.setFullName(fullName);
		merchant.setUserType(UserTypeEnum.MERCHANT.getValue());
		// 业务联系电话
		merchant.setBusiContactMobileNo(getString("busiContactMobileNo"));
		merchant.setMerchantType(merchantType);
		merchant.setUrl(getString("url"));
		merchant.setCardNo(getString("cardNo"));
		merchant.setScope(getString("scope"));
		merchant.setOrgCode(getString("orgCode"));
		merchant.setIcp(getString("icp"));
		merchant.setBusiContactName(getString("busiContactName"));// 业务联系人
		// 业务联系人邮箱
		merchant.setAddress(getString("address"));
		merchant.setFax(getString("fax"));
		merchant.setShortName(getString("shortName"));

		merchant.setLegalPerson(getString("legalPerson"));// 法人身份证号
		merchant.setCertificateExpiry(certificateExpiry); // 法人证件有效期

		// 营业执照
		merchant.setLicenseNo(getString("licenseNo"));
		if (!StringUtil.isEmpty(licenseNoValid)) {
			try {
				merchant.setLicenseNoValid(sdf.parse(licenseNoValid));
			} catch (ParseException e) {
				e.printStackTrace();
			} // 营业执照有效期
		}

		merchant.setIpSeg(getString("ipSeg")); // 新增ip段
		merchant.setMcc(getString("mccFeeLookUp.mccma")); // mcc
		// 如果校验通过，则为其它字段设值
		merchant.setMerchantKey(StrUtil.get32UUID()); // 用32位UUID作为KEY
		merchant.setStatus(MerchantStatusEnum.CREATED.getValue()); // 设置状态为新创建

		return merchant;
	}

	/***
	 * 加载省份信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getProvince() throws Exception {
		HttpServletResponse resp = getHttpResponse();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		String province = getString("province");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		List<Province> list = provinceFacade.listProvince(searchMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			Province pro = list.get(i);
			if (province.equals(pro.getProvinceNo())) {
				sb.append("<option value='" + pro.getProvinceNo() + "' selected='selected'>" + pro.getProvinceName() + "</option>");
			} else {
				sb.append("<option value='" + pro.getProvinceNo() + "'>" + pro.getProvinceName() + "</option>");
			}
		}
		pw.write(sb.toString());
		pw.close();
	}

	/***
	 * 重置商户登录密码
	 * 
	 * @return
	 */
	@Permission("merchant:info:resetpassword")
	public String resetPassword() {
		try {
			String merchantNo = getString("merchantNo");
			if (StringUtil.isEmpty(merchantNo)) {
				return operateError("商户ID不能为空");
			}

			MerchantOnline merchant = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);

			if (merchant == null) {
				return operateError("该商户信息不存在");
			}
			UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchant.getMerchantNo());
			if (userInfo == null) {
				return operateError("商户对应的用户信息不存在");
			}

			// 修改操作员表的商户登录密码
			String newPwd = UUIDUitl.generateInteger(8); // 修改商户的登录密码

			// 重置操作员登陆密码
			userOperatorFacade.resetUserOperatorPassword(userInfo.getLoginName(), DigestUtils.sha1Hex(newPwd));

			// 发送修改登录密码的邮件
			String email = userInfo.getBindEmail(); // 商户绑定的邮箱

			if (!(StringUtil.isNotNull(email))) {
				email = userInfo.getLoginName();
			}
			// 封装邮件发送
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("loginName", userInfo.getLoginName());
			paramModel.put("newPwd", newPwd);
			paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);

			// 通过模板生成邮件内容
			String content = "";
			try {
				paramModel.put("phone", EmailConst.PHONE);
				content = mailBiz.mergeEmailTemplate(EmailConst.RESET_MERCHANT_LOGIN_PASSWORD, paramModel);
			} catch (Exception e) {
				LOG.error("邮件模板解释异常", e);
			}

			mailBiz.sendMail(email, "重置商户操作员登录密码", content);
			super.logSave("重置商户操作员登录密码.商户编号[" + merchant.getMerchantNo() + "]");
		} catch (UserBizException e) {
			LOG.error("服务异常", e);
			return operateError(e.getMessage() + "，异常编号：" + e.getCode());
		} catch (Exception e) {
			LOG.error("系统异常，请联系管理员", e);
			return operateError("系统异常，请联系管理员");
		}
		return operateSuccess();

	}

	/***
	 * 加载省份信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getCity() throws Exception {
		HttpServletResponse resp = getHttpResponse();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		String provinceNo = getString("province");
		String cityNo = getString("city");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("provinceNo", provinceNo);
		List<City> list = provinceFacade.listCityBy(paramMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			City city = list.get(i);
			// Map map = (Map) list.get(i);
			if (cityNo.equals(city.getCityNo())) {
				sb.append("<option value='" + city.getCityNo() + "' selected='selected'>" + city.getCityName() + "</option>");
			} else {
				sb.append("<option value='" + city.getCityNo() + "' >" + city.getCityName() + "</option>");
			}
		}
		pw.write(sb.toString());
		pw.close();
	}

	/***
	 * 加载省份信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getArea() throws Exception {
		HttpServletResponse resp = getHttpResponse();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		// String provinceNo = getString("province");
		String cityNo = getString("city");
		String areaNo = getString("area");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityNo", cityNo);
		List<Town> list = provinceFacade.listTownBy(paramMap);
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		for (int i = 0; i < list.size(); i++) {
			Town town = list.get(i);
			// Map map = (Map) list.get(i);
			if (areaNo.equals(town.getTownNo())) {
				sb.append("<option value='" + town.getTownNo() + "' selected='selected'>" + town.getTownName() + "</option>");
			} else {
				sb.append("<option value='" + town.getTownNo() + "' >" + town.getTownName() + "</option>");
			}
		}
		pw.write(sb.toString());
		pw.close();
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
			LOG.error("AddressAction fail:", e);
		} finally {
			try {
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 1.该账户名不能被注册过会员、不能被注册过商户、不能被注册过商户操作员、不能是会员或商户的绑定邮箱
	 * 2.商户操作员表、还要判断会员表中的账名、绑定邮箱、商户表中的绑定邮箱
	 * 
	 * @param loginName
	 * @return
	 */
	// private String checkLoginNameExist(String loginName) {
	// String returnMsg = "";
	// // Merchant loginMerchant =
	// // merchantFacade.getMerchantByLoginName(loginName);// 判断登录名是否存在
	// UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
	// if (userInfo != null) {
	// returnMsg = "【" + loginName + "】该账户名已经被其他商户所使用";
	// }
	// return returnMsg;
	// }

	/**
	 * 进入修改商户信息的页面 .
	 * 
	 * @return editMerchantUI or operateError .
	 */
	@Permission("merchant:info:edit")
	public String editMerchantUI() {
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		Long merchantId = getLong("id");// 商户ID
		if (merchantId == null) {
			return operateError("商户信息不存在");
		}
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null) {
			return operateError("商户信息不存在");
		}

		this.putData("merchantTypeEnum", MerchantTypeEnum.toMap());

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchant.getMerchantNo());
		this.pushData(userInfo);
		UserBankAccount userbank = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(merchant.getMerchantNo());
		this.putData("accountBank", userbank);
		// 根据商户编号查询销售人员与商户的关联表
		MerchantSales merchantSales = merchantSalesFacade.getByMerchantNo(merchant.getMerchantNo());
		this.putData("merchantSales", merchantSales);
		if (merchantSales != null) {
			// 拿到销售人员信息
			Sales sales = salesFacade.getById(merchantSales.getSalesId());
			this.putData("sales", sales);

			// 根据商户编号查询合同管理表
			// ContractManagement contract =
			// contractManagementFacade.getByUserNo(merchant.getMerchantNo());
			// this.putData("contract", contract);
		}
		// 对私账户
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());

		this.pushData(merchant);
		return "editMerchantUI";
	}

	/***
	 * 根据商户ID查询银行账户信息
	 * 
	 * @return
	 */
	public String findAccountBankByMerchantId() {
		Long merchantId = getLong("merchantId");
		if (null == merchantId) {
			return operateError("该商户信息不存在");
		}
		// 获取银行列表
		Map<String, String> map = new Hashtable<String, String>();
		for (BankCode s : BankCode.values()) {
			map.put(s.name(), s.getDesc());
		}
		this.putData("bankDictList", map);

		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);

		this.putData("merchant", merchant);

		// 对私账户
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());

		UserBankAccount userbank = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(merchant.getMerchantNo());
		if (userbank != null) {
			if (null != userbank.getBankChannelNo()) {
				this.putData("bankTypeCode", userbank.getBankChannelNo().substring(0, 3));
			}
			this.putData("bankAccountAddress", userbank.getBankAccountAddress());
			this.putData("provinceUserBank", userbank.getProvince());
			this.putData("cityUserBank", userbank.getCity());
			this.pushData(userbank);
		}
		this.putData("isView", getString("isView"));
		return "accountBankPageFrag";
	}

	/**
	 * 对商户信息进行修改.
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("merchant:info:edit")
	public String editMerchant() {
		Long merchantId = getLong("id");
		if (merchantId == null) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "商户信息为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return null;
		}

		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "商户信息为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return null;
		}
		// 页面参数验证
		Integer merchantType = getInteger("merchantType");
		if (merchantType == MerchantTypeEnum.ENTERPRISE.getValue() || merchantType == MerchantTypeEnum.INDIVIDUAL.getValue()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			merchant.setMerchantType(merchantType);
			merchant.setLicenseNo(getString("licenseNo"));
			try {
				merchant.setLicenseNoValid(sdf.parse(getString("licenseNoValid")));
			} catch (Exception e) {
				merchant.setLicenseNoValid(null);
				e.printStackTrace();
			}
			merchant.setOrgCode(getString("orgCode"));
		}
		String validateMsg = merchantValidate(merchant);
		if (!StringUtil.isEmpty(validateMsg)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", validateMsg);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return null;
		}
		// 更新商户 封装商户信息
		updateSetMerchantInfo(merchantId);
		if (!"".equals(getString("add")) && getString("add") != null) {
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", merchantId);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return null;
		} else {
			return operateSuccess();
		}
	}

	/***
	 * 修改商户 设置商户信息
	 * 
	 * @param merchantId
	 * @return
	 */
	private void updateSetMerchantInfo(Long merchantId) {
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null) {
			throw new BossBizException("商户信息为空");
		}
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchant.getMerchantNo());
		if (userInfo == null)
			throw new BossBizException("用户信息为空");
		if (!StringUtil.isEmpty(getString("address"))) {
			merchant.setAddress(getString("address")); // 公司地址
		}
		if (!StringUtil.isEmpty(getString("bindMobileNo"))) {
			userInfo.setBindMobileNo(getString("bindMobileNo"));
		}
		if (!StringUtil.isEmpty(getString("busiContactMobileNo"))) {
			merchant.setBusiContactMobileNo(getString("busiContactMobileNo"));
		}
		if (!StringUtil.isEmpty(getString("techContactMobileNo"))) {
			merchant.setBusiContactMobileNo(getString("techContactMobileNo"));
		}
		if (!StringUtil.isEmpty(getString("url"))) {
			merchant.setUrl(getString("url"));
		}
		if (!StringUtil.isEmpty(getString("scope"))) {
			merchant.setScope(getString("scope"));
		}
		if (!StringUtil.isEmpty(getString("busiContactName"))) {
			merchant.setBusiContactName(getString("busiContactName"));
		}

		merchant.setTechContactMobileNo("13000000000"); // 修改商户，默认技术联系人电话

		// 身份证有效期
		if (!StringUtil.isEmpty(getString("certificateExpiry"))) {
			merchant.setCertificateExpiry(getString("certificateExpiry"));
		}

		if (!StringUtil.isEmpty(getString("province"))) {
			userInfo.setProvince(getString("province"));
		}
		if (!StringUtil.isEmpty(getString("city"))) {
			userInfo.setCity(getString("city"));
		}
		if (!StringUtil.isEmpty(getString("area"))) {
			userInfo.setArea(getString("area"));
		}

		if (!StringUtil.isEmpty(getString("busiContactMobileNo"))) {
			merchant.setBusiContactMobileNo(getString("busiContactMobileNo")); // 绑定手机
		}

		if (!StringUtil.isEmpty(getString("shortName"))) {
			merchant.setShortName(getString("shortName")); // 商户简称
		}

		if (!StringUtil.isEmpty(getString("fullName"))) {
			merchant.setFullName(getString("fullName")); // 商户全称
		}
		if (!StringUtil.isEmpty(getString("licenseNo"))) {
			merchant.setLicenseNo(getString("licenseNo")); // 营业执照号
		}
		if (!StringUtil.isEmpty(getString("icp"))) {
			merchant.setIcp(getString("icp")); // ICP备案号
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtil.isEmpty(getString("licenseNoValid"))) {
			try {
				merchant.setLicenseNoValid(sdf.parse(getString("licenseNoValid")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!StringUtil.isEmpty(getString("ipSeg"))) {
			merchant.setIpSeg(getString("ipSeg")); // 新增ip段
		}

		if (!StringUtil.isEmpty(getString("legalPerson"))) {
			merchant.setIpSeg(getString("legalPerson")); // 新增ip段
		}
		if (!StringUtil.isEmpty(getString("cardNo"))) {
			merchant.setIpSeg(getString("cardNo")); // 新增ip段
		}

		if (!StringUtil.isEmpty(getString("merchantType"))) {
			merchant.setMerchantType(getInteger("merchantType"));
		}

		// 如果商户当前状态是审核不通过时，则修改时需要把商户状态改成已创建
		if (MerchantStatusEnum.NOPASS.getValue() == merchant.getStatus().intValue()) {
			merchant.setStatus(MerchantStatusEnum.CREATED.getValue());
		}

		// 更新在线商户和用户
		long updNum = userManagementFacade.updateMerchantAndUser(merchant, userInfo);
		if (updNum > 0) {
			super.logEdit("修改商户.商户编号[" + merchant.getMerchantNo() + "]");
		}
	}

	/**
	 * 商户银行账户信息校验
	 * */
	// public String merchantBankAccountValidate(AccountBank accountBank) {
	// StringBuffer errMsg = new StringBuffer("");
	//
	// String bankAccountName = accountBank.getBankAccountName();
	// // 开户名 bankAccountName
	// String bankAccountNameMsg = lengthValidate("开户名", bankAccountName, true,
	// 3, 60);
	// errMsg.append(bankAccountNameMsg);
	//
	// String province = accountBank.getProvince();
	// // 省份 province
	// String provinceMsg = lengthValidate("省份", province, true, 3, 60);
	// if (StringUtils.isEmpty(provinceMsg) &&
	// !ValidateUtils.isChinese(province)) {
	// provinceMsg += "省份只能是汉字，";// 开户名目前判断只能为汉字
	// }
	// errMsg.append(provinceMsg);
	//
	// String city = accountBank.getCity();
	// // 市 province
	// String cityMsg = lengthValidate("市", city, true, 3, 60);
	// if (StringUtils.isEmpty(cityMsg) && !ValidateUtils.isChinese(city)) {
	// cityMsg += "市只能是汉字，";// 开户名目前判断只能为汉字
	// }
	// errMsg.append(cityMsg);
	//
	// String bankAccountNo = accountBank.getBankAccountNo();
	// // 开户账号
	// String bankAccountNoMsg = lengthValidate("账号", bankAccountNo, true, 3,
	// 20);
	// if (StringUtils.isEmpty(bankAccountNoMsg) &&
	// !ValidateUtils.isInteger(bankAccountNo)) {
	// bankAccountNoMsg += "市只能是数字，";// 开户名目前判断只能为汉字
	// }
	// errMsg.append(bankAccountNoMsg);
	//
	// String bankName = accountBank.getBankName();
	// // 开户账号
	// String bankNameMsg = lengthValidate("银行名称", bankName, true, 3, 60);
	// if (StringUtils.isEmpty(bankNameMsg) &&
	// !ValidateUtils.isChinese(bankName)) {
	// bankNameMsg += "银行名称只能是汉字，";// 开户名目前判断只能为汉字
	// }
	// errMsg.append(bankNameMsg);
	//
	// int BankAccountStatus = accountBank.getBankAccountStatus();
	// // 开户账号
	// String BankAccountStatusMsg = lengthValidate("银行账户状态", BankAccountStatus
	// + "", true, 3, 60);
	// if (StringUtils.isEmpty(BankAccountStatusMsg) &&
	// !ValidateUtils.isInteger(BankAccountStatus + "")) {
	// BankAccountStatusMsg += "银行账户状态格式错误，";// 开户名目前判断只能为汉字
	// }
	// errMsg.append(BankAccountStatusMsg);
	//
	// return errMsg.toString();
	// }

	/**
	 * 对新增商户信息进行校验.
	 * 
	 * @param merchant
	 *            商户信息.
	 * @return msg 校验提示信息.
	 * @author WuShuicheng .
	 * @param paramMap
	 */
	private String merchantValidate(MerchantOnline merchant) {
		String msg = ""; // 用于存放校验提示信息的变量
		// String fullName = merchant.getFullName(); // 公司全称
		String address = merchant.getAddress(); // 公司地址
		String busiContactName = merchant.getBusiContactName(); // 业务联系人
		String fullName = merchant.getFullName(); // 商户签约名
		// String url = merchant.getUrl(); // 公司网址
		String scope = merchant.getScope(); // 经营范围
		String licenseNo = merchant.getLicenseNo(); // 营业执照号
		String cardNo = merchant.getCardNo(); // 身份证号
		// String icp = merchant.getIcp(); // ICP备案号
		Date licenseNoValid = merchant.getLicenseNoValid(); // 营业执照有效期
		Integer merchantType = merchant.getMerchantType(); // 商户类型
		String orgCode = merchant.getOrgCode(); // 组织机构代码
		String mcc = merchant.getMcc(); // Mcc

		/***************************** 做商户信息的唯一约束判断 start ********************************/

		if (!StringUtil.isNotNull(fullName)) {
			msg += "商户全称不能为空";
		}

		if (merchantType == null) {
			msg += "请选择商户类型,";
		}

		if (StringUtil.isEmpty(cardNo)) {
			msg += "法人身份证号不能为空,";
		}

		String legalPerson = getString("legalPerson"); // 法人姓名
		if (StringUtil.isEmpty(legalPerson)) {
			msg += "法人姓名不能为空,";
		}
		if (StringUtil.isEmpty(merchant.getCertificateExpiry())) {
			msg += "身份证有效期不能为空,";
		}

		msg += lengthValidate("商户简称", merchant.getShortName(), true, 2, 50);

		// 公司网址 url
		// String urlMsg = lengthValidate("公司网址", url, true, 0, 255);
		// if (StringUtil.isEmpty(urlMsg) && !ValidateUtils.isURL(url)) {
		// urlMsg += "公司网址格式不正确，";
		// }
		// msg += urlMsg;

		msg += lengthValidate("经营范围", scope, true, 2, 300);

		// msg += lengthValidate("ICP备案号", icp, true, 2, 30);

		// 公司地址 address
		msg += lengthValidate("通讯地址", address, true, 0, 100);

		// 业务联系人 busiContactName
		msg += lengthValidate("业务联系人", busiContactName, true, 2, 15);

		if ("".equals(merchantType) || merchantType == null) {
			msg += "商户类型不能为空，";
		}

		if (merchantType == MerchantTypeEnum.ENTERPRISE.getValue() || merchantType == MerchantTypeEnum.INDIVIDUAL.getValue()) {
			if ("".equals(licenseNo) || licenseNo == null) {
				msg += "营业执照号不能为空，";
			}
			if ("".equals(licenseNoValid) || licenseNoValid == null) {
				msg += "营业执照有效期不能为空,";
			}
			if ("".equals(orgCode) || orgCode == null) {
				msg += "组织机构代码不能为空,";
			}
		}
		return msg;
	}

	/***
	 * 跳转到审核不通过页面
	 * 
	 * @return
	 */
	public String toAuditNoPassUI() {
		this.putData("merchantId", getString("merchantId"));
		return "auditNoPassPage";
	}

	/***
	 * 审核商户信息
	 * 
	 * @return
	 */
	public String auditMerchantInfo() {
		long merchantId = getLong("id");
		Integer status = getInteger("status");
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId); // 查询商户信
		if (merchant == null)
			return operateError("商户信息为空");
		// 查询实名认证表信息
		String merchantNo = merchant.getMerchantNo();
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchantNo);
		if (userInfo == null)
			return operateError("商户用户信息为空");

		PmsOperator operator = super.getLoginedOperator();
		if (UserAuditStatusEnum.AGREE.getValue() == status) { // 审核
			// 校验数据完整性
			String errorMsg = checkDataIntegrity(merchant);
			if (!"".equals(errorMsg) && errorMsg != null) {
				return operateError(errorMsg);
			}
		}
		String str = "";
		String checkResult = getString("checkResult");
		// 查看审核记录有米有数据
		UserAuditRecordStatus userAuditRecordStatus = userAuditFacade.getUserAuditRecordStatusByUserNo_auditStatus(merchantNo, UserAuditStatusEnum.WAIT.getValue());
		if (status.intValue() == UserAuditStatusEnum.AGREE.getValue()) {// 成功
			if (userAuditRecordStatus == null) {
				userAuditFacade.auditPass_Status(0, merchantNo, operator.getLoginName(), operator.getRealName(), "系统自动触发申请", "运营人员审核通过", MerchantStatusEnum.ACTIVE.getValue(), merchant.getStatus());
				super.logSave("审核商户通过，商户编号为：" + merchantNo);
				str = EmailConst.MERCHANT_AUDIT_PASS;
			} else {
				userAuditFacade.auditPass_Status(userAuditRecordStatus.getId(), merchantNo, operator.getLoginName(), operator.getRealName(), "", "运营人员审核通过", MerchantStatusEnum.ACTIVE.getValue(), merchant.getStatus());
				super.logSave("审核商户通过，商户编号为：" + merchantNo);
				str = EmailConst.MERCHANT_AUDIT_PASS;
			}
		} else {// 拒绝
			if (userAuditRecordStatus == null) {
				userAuditFacade.auditRefuse_Status(0, merchantNo, operator.getLoginName(), operator.getRealName(), "系统自动触发申请", checkResult, MerchantStatusEnum.ACTIVE.getValue(), merchant.getStatus());
				super.logSave("审核商户不通过，商户编号为：" + merchantNo + "原因:" + checkResult);
				str = EmailConst.MERCHANT_AUDIT_NOTPASS;
			} else {
				userAuditFacade.auditRefuse_Status(userAuditRecordStatus.getId(), merchantNo, operator.getLoginName(), operator.getRealName(), "", checkResult, MerchantStatusEnum.ACTIVE.getValue(), merchant.getStatus());
				super.logSave("审核商户不通过，商户编号为：" + merchantNo + "原因:" + checkResult);
				str = EmailConst.MERCHANT_AUDIT_NOTPASS;
			}
		}

		// 邮件发送
		String bindEmail = userInfo.getBindEmail();
		if (!StringUtil.isEmpty(bindEmail)) {
			// 封装邮件发送
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("loginName", userInfo.getLoginName());
			paramModel.put("fullName", userInfo.getRealName());
			paramModel.put("reason", checkResult);
			paramModel.put("changeStatus", MerchantStatusEnum.ACTIVE.getDesc());
			paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);

			// 通过模板生成邮件内容
			String content = "";
			try {
				paramModel.put("phone", EmailConst.PHONE);
				content = mailBiz.mergeEmailTemplate(str, paramModel);
				mailBiz.sendMail(bindEmail, "商户审核结果邮件", content);
			} catch (Exception e) {
				LOG.error("邮件发送异常", e);
				return operateError("商户信息审核成功，但发送邮件失败!");
			}
		}
		return operateSuccess();
	}

	/***
	 * 验证数据的完整性
	 * 
	 * @param accountId
	 *            账户ID
	 * @param merchantId
	 *            商户ID
	 * @param salesId
	 *            销售人员ID
	 * @return
	 */
	private String checkDataIntegrity(MerchantOnline merchant) {
		String msg = "";
		String merchantNo = merchant.getMerchantNo();
		// 1、企业银行账户
		UserBankAccount userbank = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(merchantNo);
		if (userbank == null) {
			msg += "请补充用户银行账户！";
		}
		// 2、结算方式
		SettRule settRule = settQueryFacade.getSettlRuleByMerchantNo(merchantNo);
		if (settRule == null) {
			msg += "请补充结算方式信息！";
		}
		// 3、销售人员
		MerchantSales sales = merchantSalesFacade.getByMerchantNo(merchantNo);
		if (sales == null) {
			msg += "请补充销售人员信息！";
		}

		// 4、设置支付规则
		// 根据支付规则ID和用户编号查询用户支付规则关联表
		UserPayRuleSetting setting = payRuleFacade.getRuleSetByRuleAndUserNo(null, String.valueOf(merchantNo));
		if (setting == null) {
			msg += "请设置该商户的支付规则！";
		}
		// 5、设置计费节点
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", merchantNo);
		// map.put("userSettingStatus", status);
		super.pageBean = feeQueryFacade.queryUserFeeSettingAndNodeListPage(getPageParam(), map);
		if (pageBean.getTotalCount() <= 0) {
			msg += "请设置该商户的计费节点！";
		}
		// 6、关联限制包
		TradeLimitRouter tradeLimitRouter = tradeLimitRouterFacade.getTradeLimitRouterByMerchantNo(merchantNo);
		if (tradeLimitRouter == null) {
			msg += "请设置该商户的关联限制包！";
		}
		return msg;
	}

	/***
	 * 跳转到修改账户状态页面
	 * 
	 * @return
	 */
	@Permission("merchant:info:changestatus")
	public String toChangeMerchantStatusUI() {
		this.putData("merchantId", getString("id"));
		this.putData("status", getString("status"));
		return "changeMerchantStatusUI";

	}

	/***
	 * 跳转到注销商户信息页面
	 * 
	 * @return
	 */
	public String toCellMerchantUI() {
		Long merchantId = getLong("id");
		if (null == merchantId) {
			return operateError("商户信息不存在，请核查！");
		}
		this.putData("merchantId", merchantId);
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null) {
			return operateError("商户ID[" + merchantId + "]的信息不存在，请核查！");
		}
		this.pushData(merchant);
		// this.putData("merchantNo", merchant.getMerchantNo()); // 此处需要换成商户编号
		return "cellMerchantUI";
	}

	/***
	 * 注销商户提交Action
	 * 
	 * @return
	 */
	public String merchantCell() {
		Long merchantId = getLong("id");
		String requestDesc = getString("requestDesc"); // 申请意见
		if (null == merchantId)
			return operateError("商户信息不存在，请核查！");
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null)
			return operateError("商户信息不存在，请核查！");
		PmsOperator operator = super.getLoginedOperator();
		try {
			userAuditFacade.createUserAuditRecordClose(merchant.getMerchantNo(), operator.getLoginName(), operator.getRealName(), requestDesc);
			this.logSave("创建商户销户申请记录，商户全称为：" + merchant.getFullName());
		} catch (UserBizException e) {
			LOG.error("申请商户销户失败", e);
			return operateError("该商户的销户申请正在审核中！");
		} catch (Exception e) {
			LOG.error("申请商户销户失败", e);
			return operateError("商户审核失败，请稍后重试！");
		}
		return operateSuccess();
	}

	/**
	 * 查看商户的详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Permission("merchant:info:view")
	public String viewMerchant() {
		Long id = getLong("id");
		if (id == null) {
			return operateError("商户ID为空");
		}
		MerchantOnline merchant = merchantOnlineFacade.getById(id);
		if (merchant == null)
			return operateError("商户信息不存在");

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchant.getMerchantNo());
		if (userInfo == null)
			return operateError("用户信息不存在");

		this.pushData(userInfo);
		this.pushData(merchant);
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型

		Date currentDate = new Date();

		// 营业执照到期日期
		Date licenseNoValid = merchant.getLicenseNoValid();
		this.putData("licenseNoValid", licenseNoValid);
		if (licenseNoValid != null) {
			long diff = licenseNoValid.getTime() - currentDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			if (days <= TIME_OUT) {
				this.putData("licenseTimeOut", days);
			}
		}

		// 对私账户
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("isOrNotEnumList", PublicStatusEnum.toIsOrNotList());
		this.putData("remitBankTypeList", remitBankTypeList);
		this.putData("bankAccountTypeEnumList", BankAccountTypeEnum.toRemitList());
		this.putData("provinceList", remitBankAreaFacade.getProvince());

		this.putData("applyRateTypeEnum", ApplyRateTypeEnum.toList());

		MerchantSales merchantSales = merchantSalesFacade.getByMerchantNo(merchant.getMerchantNo());
		this.putData("merchantSales", merchantSales);
		if (merchantSales != null) {
			// 拿到销售人员信息
			Sales sales = salesFacade.getById(merchantSales.getSalesId());
			this.putData("sales", sales);

		}

		this.putData("isEdit", "1");
		this.putData("isAudit", getString("isAudit"));
		return "viewMerchant";
	}

	/**
	 * 商户信息的查找带回，用于在其他依赖于商户信息的模块中选择商户信息进行关联.
	 * 
	 * @return merchantLookupList .
	 */
	public String merchantLookupList() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", getString("merchantNo")); // 商户编号
		paramMap.put("fullName", getString("fullName")); // 商户编号
		List<String> inStatusList = new ArrayList<String>();
		inStatusList.add(String.valueOf(MerchantStatusEnum.ACTIVE.getValue()));
		inStatusList.add(String.valueOf(MerchantStatusEnum.CREATED.getValue()));
		paramMap.put("inStatus", inStatusList); // 商户状态
		super.pageBean = merchantOnlineFacade.listMerchantListPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "merchantLookupList";
	}

	/***
	 * 新增销售人员和商户的管理表
	 * 
	 * @return
	 */
	public String addMerchantSales() {
		String merchantSalesId = getString("backSalesId"); // 记录ID
		Long salesId = getLong("backSalesId"); // 销售人员id
		// String signTime = getString("signTime"); // 签约时间
		// String contractValid = getString("contractValid"); // 合同到期日期
		Long merchantId = getLong("xiaoshouMerchantId"); // 商户编号

		if (merchantId == null || salesId == null || StringUtil.isEmpty(merchantSalesId)) {
			return operateError("数据不完整，请填写完整");
		}
		try {
			MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
			if (merchant == null) {
				return operateError("商户信息为空");
			}
			String merchantNo = merchant.getMerchantNo();
			MerchantSales sales = merchantSalesFacade.getByMerchantNo(merchantNo);
			if (sales == null) {
				sales = new MerchantSales();
				sales.setMerchantNo(merchantNo);
				sales.setSalesId(salesId);
				Long sId = merchantSalesFacade.create(sales);
				if (sId > 0) {
					this.logSave("将商户【" + merchant.getFullName() + "】的销售员设为：" + salesId);
					return operateSuccess();
				} else {
					return operateError("保存异常，请稍后再试！");
				}
			} else {
				sales.setMerchantNo(merchantNo);
				sales.setSalesId(salesId);
				long num = merchantSalesFacade.update(sales);
				if (num > 0) {
					this.logEdit("将商户【" + merchant.getFullName() + "】的销售员修改为：" + salesId);
					return operateSuccess();
				} else {
					return operateError("保存异常，请稍后再试！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return operateError("保存异常，请稍后再试！");
		}
	}
}
