package wusc.edu.pay.web.portal.action.merchant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.MerchantTypeEnum;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.biz.MailBiz;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title: 商户注册</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-4
 */
@SuppressWarnings("serial")
public class RegisterAction extends BaseAction {
	private static Log LOG = LogFactory.getLog(RegisterAction.class);

	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;

	/**
	 * 注册:进入验证账户名页面
	 * 
	 * @return
	 */
	public String checkLoginNameUI() {
		return "checkLoginNameUI";
	}

	/**
	 * 第一步，验证登录名 注册:验证账户名
	 * 
	 * @return
	 */
	public String checkLoginName() {
		String loginName = StringTools.stringToTrim(getString("loginName"));// 用户名
		String randomCode = StringTools.stringToTrim(getString("randomCode"));// 验证码
		Integer userType = 0;
		// 1.验证页面参数
		Map<String, String> msgMap = validateCheckLoginName(getHttpRequest(),
				loginName, randomCode);
		if (!msgMap.isEmpty()) {
			this.pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2、发送邮件
		String url = mailBiz.sendEmail(loginName, 0, loginName, loginName,
				PublicTemplateEnum.MAIL_MERCHANT_RES_MA.getValue());
		putData("userNo", loginName);
		putData("userType", userType);
		putData("loginName", loginName);
		putData("toMail", loginName);
		putData("mailType", PublicTemplateEnum.MAIL_MERCHANT_RES_MA.getValue());
		putData("url", url);
		LOG.info("注册流程--》 1.发送验证邮件成功,登录名[" + loginName + "] . ");
		return "checkLoginName";
	}

	/**
	 * 商户点击验证邮箱地址后的操作 商户注册:进入设置身份信息页面
	 * 
	 * @return
	 */
	public String setIdentityInfoUI() {
		String token = StringTools.stringToTrim(getString("token"));
		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		// 2.设置信息，用于页面显示
		putData("token", token);
		putData("questions", SecurityQuestionEnum.values());// 安全问题
		putData("loginName", ((EmailVerify) obj).getUserNo());
		return "setIdentityInfoUI";
	}
	
	

	/**
	 * 注册:设置身份信息 获取页面传过来的登录密码和支付密码
	 * 
	 * @return
	 */
	public String setIdentityInfo() {

		// 隐藏域参数
		String token = StringTools.stringToTrim(getString("token"));// （登录名+编号）

		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		Integer userType = ((EmailVerify) obj).getUserType();// 用户类型（POS,在线）
		String loginName = ((EmailVerify) obj).getUserNo();// 登录名
		String loginPwd = StringTools.stringToTrim(getString("loginPwd"));// 登录密码
		String reLoginPwd = StringTools.stringToTrim(getString("reLoginPwd"));// 确认登录密码
		String tradePwd = StringTools.stringToTrim(getString("tradePwd"));// 支付密码
		String reTradePwd = StringTools.stringToTrim(getString("reTradePwd"));// 确认支付密码
		Integer question = getInteger("question");// 安全问题
		String greeting = StringTools.stringToTrim(getString("greeting"));// 预留信息
		String answer = StringTools.stringToTrim(getString("answer"));// 回答
		String bindingPhone = StringTools
				.stringToTrim(getString("bindingPhone"));// 绑定手机
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));// 手机验证码

		// 2.验证页面参数
		Map<String, String> msgMap1 = validateSetIdentityInfo(loginName,
				loginPwd, reLoginPwd, tradePwd, reTradePwd, question, answer,
				bindingPhone, phoneCode, greeting);
		if (!msgMap1.isEmpty()) {
			pushData(msgMap1);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 把用户信息用 常量 + 用户登录名 作为key
		String putMapParamFmt = BaseConsts.SAVE_SECOND_STEP_VALUE + "_"
				+ loginName;

		// 删除Session中，原来的数据
		getSessionMap().remove(putMapParamFmt);
		// 存放用户登录密码，支付密码的Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userType", userType); // 用户类型
		paramMap.put("loginName", loginName); // 登录名
		paramMap.put("loginPwd", loginPwd); // 登录密码
		paramMap.put("tradePwd", tradePwd); // 交易密码
		paramMap.put("question", question); // 安全问题
		paramMap.put("answer", answer); // 问题答案
		paramMap.put("greeting", greeting); // 预留信息
		paramMap.put("bindingPhone", bindingPhone); // 绑定手机

		getSessionMap().put(putMapParamFmt, paramMap);

		// 移除短信验证码
		getSessionMap().remove(BaseConsts.CURRENT_USER_PHONE_CODE);

		// 5.设置信息，用于页面显示
		putData("token", token);
		putData("loginName", loginName);
		putData("userType", userType);
		putData("MerchantTypeEnum", MerchantTypeEnum.toMap());
		putData("provinceList", remitBankAreaFacade.getProvince());
		return "setMerchantInfoUI";
	}



	/**
	 * 商户注册:设置商户信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String setMerchantInfo() {
		// 隐藏域参数
		String token = StringTools.stringToTrim(getString("token"));
		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		String loginName = getString("loginName");// 登录名

		// 页面参数
		Integer merchantType = getInteger("merchantType");// 签约属性
		String licenseNo = "";
		String licenseValid = "";
		if (merchantType != 10) {
			 licenseNo = StringTools.stringToTrim(getString("licenseNo"));// 营业执照号
			 licenseValid = StringTools.stringToTrim(getString("licenseValid")); //营业执照有效期
		}
		
		String fullName = StringTools.stringToTrim(getString("fullName"));// 商户全称
		String shortName = StringTools.stringToTrim(getString("shortName"));// 商户简称
		String url = StringTools.stringToTrim(getString("url"));// 商城网址
		String mcc = StringTools.stringToTrim(getString("mccValue")); // POS商户的MCC码
		String orgCode = StringTools.stringToTrim(getString("orgCode")); // 组织机构代码
		String icp = StringTools.stringToTrim(getString("icp"));// ICP证备案号
		String legalPerson = StringTools.stringToTrim(getString("legalPerson"));// 企业法人姓名
		String cardNo = StringTools.stringToTrim(getString("cardNo"));// 身份证号
		String cardValid = StringTools.stringToTrim(getString("cardValid"));// 身份证有效期
		String busiContactName = StringTools.stringToTrim(getString("busiContactName"));// 联系人姓名
		String busiContactMobileNo = StringTools.stringToTrim(getString("busiContactMobileNo"));// 联系人电话
		String scope = StringTools.stringToTrim(getString("scope"));// 经营范围
		String province = StringTools.stringToTrim(getString("province"));// 省
		String city = StringTools.stringToTrim(getString("city"));// 市
		String address = StringTools.stringToTrim(getString("address"));// 通信地址

		Map<String, String> msgMap1 = validateSetInfo(token, loginName,
				merchantType, fullName, shortName, licenseNo, url, mcc,
				orgCode, icp, legalPerson, cardNo, busiContactName,
				busiContactMobileNo, scope, province, city, address);

		// 1.页面验证参数
		if (!msgMap1.isEmpty()) {
			pushData(msgMap1);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 获取第二步填充的数据 start
		// 把用户信息用 常量 + 用户登录名 作为key
		String putMapParamFmt = BaseConsts.SAVE_SECOND_STEP_VALUE + "_" + loginName;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = (Map<String, Object>) getSessionMap().get(putMapParamFmt);
		if (paramMap == null) {
			this.putData("errorMsg", "数据丢失，请重新再试！");
			return "registerError";
		}

		String loginPwd = String.valueOf(paramMap.get("loginPwd")); // 登录密码
		String tradePwd = String.valueOf(paramMap.get("tradePwd")); // 交易密码
		int question = Integer.parseInt(String.valueOf(paramMap.get("question"))); // 安全问题
		String answer = String.valueOf(paramMap.get("answer")); // 问题答案
		String greeting = String.valueOf(paramMap.get("greeting")); // 预留信息
		String bindingPhone = String.valueOf(paramMap.get("bindingPhone")); // 绑定手机
		// 获取第二步填充的数据 end
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// 营业执照有效期
		Date licenseValidValue = null;
		try {
			if(StringUtil.isNotNull(licenseValid)){
				licenseValidValue = sdf.parse(licenseValid);
			}else{
				licenseValidValue=sdf.parse("2099-12-31");
			}
		} catch (Exception e) {
			LOG.error("时间转换异常", e);
		}
		if(!StringUtil.isNotNull(cardValid)){
			cardValid="2099-12-31";
		}
		// 调用注册商户接口
		userManagementFacade.registerPortalMerchant(loginName, merchantType,
				fullName, shortName, licenseNo, licenseValidValue, url, mcc, orgCode, icp,
				legalPerson, cardNo, cardValid, busiContactName, busiContactMobileNo,
				scope, province, city, address, DigestUtils.sha1Hex(loginPwd), DigestUtils.sha1Hex(tradePwd), question,
				answer, greeting, bindingPhone);

		putData("loginName", loginName);

		// 更新TOKEN状态,改为“已验证”
		mailBiz.updateEmailVerifyStatus(token);

		LOG.info("注册流程--》 3.注册在线商户成功,商户登录名[" + loginName + "] . ");
		return "registerSuccess";
	}

	// ***************************** 验证方法***********************************//

	private Map<String, String> validateSetInfo(String token, String loginName,
			Integer merchantType, String fullName, String shortName,
			String licenseNo, String url, String mcc, String orgCode,
			String icp, String legalPerson, String cardNo,
			String busiContactName, String busiContactMobileNo, String scope,
			String province, String city, String address) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;
		// 签约属性：必填，
		if (ValidateUtils.isEmpty(merchantType)) {
			msgMap.put(msgErrorKey, "请选择类型");
			return msgMap;
		}
		// 商户全称:必填，2～30个字
		if (ValidateUtils.isEmpty(fullName) || fullName.length() < 2
				|| fullName.length() > 30) {
			msgMap.put(msgErrorKey, "请输入2-30个字");
			return msgMap;
		}
		// 商户简称:2～10个字
		if (ValidateUtils.isEmpty(shortName) || shortName.length() < 2
				|| shortName.length() > 10) {
			msgMap.put(msgErrorKey, "请输入2-10的字");
			return msgMap;
		}

		/*// 营业执照号:必填，15位
		if (ValidateUtils.isEmpty(licenseNo) || licenseNo.length() != 15) {
			msgMap.put(msgErrorKey, "请输入15位的营业执照号");
			return msgMap;
		}
		// 商城网址：必填，少于255个字的正确网址
		if (ValidateUtils.isEmpty(url) || !ValidateUtils.isURL(url)
				|| url.length() > 255) {
			msgMap.put(msgErrorKey, "请输入少于255个字的正确网址");
			return msgMap;
		}
		// 组织机构代码:必填，符合格式
		if (ValidateUtils.isEmpty(orgCode) || ValidateUtils.isOrgCode(orgCode)) {
			msgMap.put(msgErrorKey, "请输入正确的组织机构代码");
			return msgMap;
		}
		// ICP证备案号：2～30字
		if (ValidateUtils.isEmpty(icp) || icp.length() < 2 || icp.length() > 30) {
			msgMap.put(msgErrorKey, "请输入2-30个字");
			return msgMap;
		}*/
		// 身份证号：18位正确格式的身份证号
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo)) {
			msgMap.put(msgErrorKey, "请输入18位正确格式的身份证号");
			return msgMap;
		}

		// 企业法人姓名:2～10个字
		if (ValidateUtils.isEmpty(legalPerson) || legalPerson.length() < 2
				|| legalPerson.length() > 10) {
			msgMap.put(msgErrorKey, "请输入2-10的字");
			return msgMap;
		}

		// 身份证号：18位正确格式的身份证号
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo)) {
			msgMap.put(msgErrorKey, "请输入18位正确格式的身份证号");
			return msgMap;
		}

		// 联系人姓名：必填，2-10个中文
		if (ValidateUtils.isEmpty(busiContactName)
				|| busiContactName.length() < 2
				|| busiContactName.length() > 10
				|| !ValidateUtils.isChinese(busiContactName)) {
			msgMap.put(msgErrorKey, "请输入2-10个中文");
			return msgMap;
		}

		// 联系人手机：必填，符合手机号格式
		if (ValidateUtils.isEmpty(busiContactMobileNo)
				|| !ValidateUtils.isMobile(busiContactMobileNo)) {
			msgMap.put(msgErrorKey, "请输入正确格式的手机号码");
			return msgMap;
		}
		// 经营范围:必填，2～100个字
		if (ValidateUtils.isEmpty(scope) || scope.length() < 2
				|| scope.length() > 100) {
			msgMap.put(msgErrorKey, "请输入2-100个字");
			return msgMap;
		}

		// 省市区：必选
		if (ValidateUtils.isEmpty(province) || ValidateUtils.isEmpty(city)
				|| "0".equals(province) || "0".equals(city)) {
			msgMap.put(msgErrorKey, "请选择具体地址");
			return msgMap;
		}

		// 通信地址：必填，2～50个字
		if (ValidateUtils.isEmpty(address) || address.length() < 2
				|| address.length() > 50) {
			msgMap.put(msgErrorKey, "请输入2-50个字");
			return msgMap;
		}

		return msgMap;
	}

	private Map<String, String> validateCheckLoginName(HttpServletRequest req,
			String loginName, String randomCode) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;

		// 用户名：必填，邮箱（不可为雅虎邮箱）
		if (ValidateUtils.isEmpty(loginName)
				|| !ValidateUtils.isEmail(loginName)) {
			msgMap.put(msgErrorKey, "请正确输入邮箱地址作为用户名");
			return msgMap;
		}

		// 验证登录名是否存在
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
		if (userInfo != null) {
			msgMap.put(msgErrorKey, "该登录名已存在!");
			return msgMap;
		}

		// 验证码：必填，与正确验证码相同，且不区分大小写
		if (ValidateUtil.isValidateCode(req, randomCode)) {
			msgMap.put(msgErrorKey, "请输入正确的验证码");
			return msgMap;
		}
		return msgMap;
	}

	/***
	 * 验证数据的完整性
	 * 
	 * @param loginName
	 *            登录名
	 * @param loginPwd
	 *            登录密码
	 * @param reLoginPwd重复登录密码
	 * @param tradePwd
	 *            交易密码
	 * @param reTradePwd重复交易密码
	 * @param question
	 *            密保问题
	 * @param answer
	 *            密保答案
	 * @param bindMobileNo
	 *            绑定手机号
	 * @param phoneCode
	 *            短信验证码
	 * @param greeting
	 *            预留信息
	 * @return
	 */
	private Map<String, String> validateSetIdentityInfo(String loginName,
			String loginPwd, String reLoginPwd, String tradePwd,
			String reTradePwd, Integer question, String answer,
			String bindMobileNo, String phoneCode, String greeting) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;
		// 用户名：
		if (ValidateUtils.isEmpty(loginName)
				|| !ValidateUtils.isEmail(loginName)) {
			msgMap.put(msgErrorKey, "数据丢失：用户名");
			return msgMap;
		}

		// 登录密码：必填，8~20位不连续的数字、字母和特殊符号组合
		String pwdMsg = ValidateUtil.isValidatePwd(loginPwd);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(msgErrorKey, pwdMsg);
			return msgMap;
		}

		// 再次输入登录密码:必填，上次密码一致
		if (ValidateUtils.isEmpty(reLoginPwd) || !loginPwd.equals(reLoginPwd)) {
			msgMap.put(msgErrorKey, "两次输入密码不一致");
			return msgMap;
		}

		// 支付密码： 必填，8~20位不连续的数字、字母和特殊符号组合
		pwdMsg = ValidateUtil.isValidatePwd(tradePwd);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(msgErrorKey, pwdMsg);
			return msgMap;
		}
		// 再次输入支付密码:必填，上次密码一致
		if (ValidateUtils.isEmpty(reTradePwd) || !tradePwd.equals(reTradePwd)) {
			msgMap.put(msgErrorKey, "两次输入密码不一致");
			return msgMap;
		}

		// 登录支付密码：不可相同
		if (loginPwd.equals(tradePwd)) {
			msgMap.put(msgErrorKey, "支付密码不能与登录密码一样");
			return msgMap;
		}

		// 安全问题：必选
		if (ValidateUtils.isEmpty(question)) {
			msgMap.put(msgErrorKey, "请选择安全问题");
			return msgMap;
		}

		// 安全问题回答：必填，2~16个字
		if (ValidateUtils.isEmpty(answer) || answer.length() < 2
				|| answer.length() > 16) {
			msgMap.put(msgErrorKey, "请输入长度为2~16的字");
			return msgMap;
		}

		// 预留信息：必填，5~20个字
		if (ValidateUtils.isEmpty(greeting) || greeting.length() < 5
				|| answer.length() > 20) {
			msgMap.put(msgErrorKey, "请输入长度为5~20的字");
			return msgMap;
		}

		// 绑定手机号码验证
		if (ValidateUtils.isEmpty(bindMobileNo)
				|| !ValidateUtils.isMobile(bindMobileNo)) {
			msgMap.put(msgErrorKey, "请输入正确的手机号码");
			return msgMap;
		}
		// 手机动态码验证
		if (!ValidateUtil.isValidatePhoneCode(loginName, phoneCode,
				getCurrentPhoneCode())) {
			msgMap.put(msgErrorKey, "请输入正确的验证码");
			return msgMap;
		}
		return msgMap;
	}

	/**
	 * @Title: 根据省份获取下面的城市信息
	 * @Description: {[city:xxx],[city:xxx]}
	 * @param
	 * @return void
	 * @throws
	 */
	public void getCityByProvince() {
		String province = getString("province");
		getOutputMsg().put("cityList",
				remitBankAreaFacade.getCityByProvince(province));
		this.outPrint(this.getHttpResponse(),
				JSONObject.fromObject(this.getOutputMsg()));
	}
}
