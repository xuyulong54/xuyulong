package wusc.edu.pay.web.portal.action.merchant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantTypeEnum;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title:商户基本信息</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-6
 */
@Scope("prototype")
public class MerchantInfoAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(MerchantInfoAction.class);
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;

	/**
	 * 查看商户基本信息
	 */
	public String viewMerchant() {
		// 获取商户基本信息
		MerchantOnline merchantOnline = getMerchantOnline();
		UserInfo userInfo = getCurrentUserInfo();
		putData("bean", merchantOnline);
		putData("showaddress", userInfo.getProvince() + "  " + userInfo.getCity() + "  " + merchantOnline.getAddress());

		putData("MerchantTypeEnumList", MerchantTypeEnum.values());
		putData("MerchantTypeEnum", MerchantTypeEnum.toMap());

		putData("MerchantStatusEnumList", MerchantStatusEnum.values());
		putData("MerchantStatusEnum", MerchantStatusEnum.toMap());

		putData("UserOperatorTypeEnum", UserOperatorTypeEnum.toMap());
		// 获取当用户的CA证书列表
		//this.putData("snArray", CurrentUserCertUtils.getCurrentUserSN(userInfo.getId(), userInfo.getUserType(), userInfo.getUserNo()));
		return "MerchantView";
	}

	/**
	 * 去修改商户信息页面
	 * 
	 * @return
	 */
	public String editMerchantUI() {
		MerchantOnline merchantOnline = getMerchantOnline();
		putData("userInfo", getUserInfoByUserNo(getCurrentUserInfo().getUserNo()));
		pushData(merchantOnline);
		putData("MerchantTypeEnum", MerchantTypeEnum.toMap());
		putData("provinceList", remitBankAreaFacade.getProvince());
		// 状态为“注册中”“审核不通过”
		if (merchantOnline.getStatus().intValue() == MerchantStatusEnum.NOPASS.getValue()) {
			return "editAllMerchantUI";
		}
		putData("isMobileAuth", getCurrentUserInfo().getIsMobileAuth());
		putData("showBindMobileNo", ValidateUtils.isEmpty(getCurrentUserInfo().getBindMobileNo()) ? "" : StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
		return "editMerchantUI";
	}

	/**
	 * 
	 * 修改部分基本信息
	 * 
	 * @return
	 */
	public String editMerchant() {
		String shortName = getString("shortName");// 商户简称
		String busiContactName = getString("busiContactName");// 联系人
		String busiContactMobileNo = getString("busiContactMobileNo");// 联系人手机号
		String province = StringTools.stringToTrim(getString("province"));// 省
		String city = StringTools.stringToTrim(getString("city"));// 市
		String area = StringTools.stringToTrim(getString("area"));// 区
		String address = getString("address");// 地址
		String phoneCode = getString("phoneCode");
		Map<String, String> msgMap = validateEditMerchant(shortName, busiContactName, address, busiContactMobileNo, phoneCode, province, city, area);
		if (!msgMap.isEmpty()) {
			// 校验不通过，回显提示信息和表单数据
			putData("isMobileAuth", getCurrentUserInfo().getIsMobileAuth());
			this.pushData(msgMap);
			return "editMerchantUI";
		}
		MerchantOnline merchantOnline = getMerchantOnline();
		merchantOnline.setBusiContactName(busiContactName);
		merchantOnline.setAddress(address);
		merchantOnline.setBusiContactMobileNo(busiContactMobileNo);
		merchantOnline.setShortName(shortName);
		UserInfo userInfo = super.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfo.setArea(area);
		userManagementFacade.updateUserInfo(userInfo);
		merchantOnlineFacade.update(merchantOnline);

		setCurrentUserInfo(userInfo); // 更新session中用户信息的值

		this.addUserLog(OpeStatusEnum.SUCCESS, "修改商户部分基本信息");

		return viewMerchant();
	}

	/**
	 * 修改全部基本信息
	 * 
	 * @return
	 */
	public String editMerchantAll() {

		Integer merchantType = getInteger("merchantType");// 签约属性
		String licenseNo = StringTools.stringToTrim(getString("licenseNo"));// 营业执照号
		String cardNo = StringTools.stringToTrim(getString("cardNo"));// 身份证号
		String fullName = StringTools.stringToTrim(getString("fullName"));// 公司全称
		String url = StringTools.stringToTrim(getString("url"));// 商城网址
		String icp = StringTools.stringToTrim(getString("icp"));// ICP证备案号
		String busiContactName = StringTools.stringToTrim(getString("busiContactName"));// 联系人姓名
		String busiContactMobileNo = StringTools.stringToTrim(getString("busiContactMobileNo"));// 业务电话
		String address = StringTools.stringToTrim(getString("address"));// 通信地址
		String scope = StringTools.stringToTrim(getString("scope"));// 经营范围
		String shortName = StringTools.stringToTrim(getString("shortName"));// 商户简称
		String province = StringTools.stringToTrim(getString("province"));// 省
		String city = StringTools.stringToTrim(getString("city"));// 市
		String area = StringTools.stringToTrim(getString("area"));// 区
		String legalPerson = StringTools.stringToTrim(getString("legalPerson"));

		Map<String, String> msgMap = validateEditMerchantAll(merchantType, licenseNo, cardNo, fullName, url, icp, busiContactName, busiContactMobileNo, address, scope, shortName, province, city, area, legalPerson);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		MerchantOnline merchanOnline = getMerchantOnline();
		merchanOnline.setMerchantType(merchantType);
		merchanOnline.setMerchantNo(String.valueOf(merchantType));// 签约属性作为标示，由存储过程生成商户编号
		merchanOnline.setLicenseNo(licenseNo);
		merchanOnline.setFullName(fullName);
		merchanOnline.setUrl(url);
		merchanOnline.setIcp(icp);
		merchanOnline.setBusiContactName(busiContactName);
		merchanOnline.setBusiContactMobileNo(busiContactMobileNo);
		merchanOnline.setAddress(address);
		merchanOnline.setScope(scope);
		merchanOnline.setShortName(shortName);
		merchanOnline.setCardNo(cardNo);
		merchanOnline.setLegalPerson(legalPerson);

		UserInfo userInfo = super.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfo.setArea(area);
		userInfo.setCardNo(cardNo);
		merchanOnline.setStatus(MerchantStatusEnum.CREATED.getValue());
		userManagementFacade.updateUserInfo(userInfo);
		merchantOnlineFacade.update(merchanOnline);
		setCurrentUserInfo(userInfo);

		this.addUserLog(OpeStatusEnum.SUCCESS, "修改商户全部基本信息");
		return viewMerchant();
	}

	/**
	 * 跳转至指定的URL
	 */
	public void redirectUrl() {
		try {
			getHttpResponse().sendRedirect((String) getSessionMap().remove(BaseConsts.MERCHANT_REDIRECT_URL));
		} catch (IOException e) {
			LOG.error("redirectUrl fail:", e);
		}
	}

	/***
	 * 验证修改商户的基本信息
	 * 
	 * @param shortName
	 * @param busiContactName
	 * @param address
	 * @param busiContactMobileNo
	 * @param phoneCode
	 * @param province
	 * @param city
	 * @param area
	 * @return
	 */
	public Map<String, String> validateEditMerchant(String shortName, String busiContactName, String address, String busiContactMobileNo, String phoneCode, String province, String city, String area) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		// 商户简称
		if (ValidateUtils.isEmpty(shortName) || shortName.length() < 2 || shortName.length() > 10) {
			msgMap.put(errorType, "请输入2-10的字");
			return msgMap;
		}
		// 联系人姓名
		if (ValidateUtils.isEmpty(busiContactName) || busiContactName.length() < 2 || busiContactName.length() > 10 || !ValidateUtils.isChinese(busiContactName)) {
			msgMap.put(errorType, "请输入2-10个中文");
			return msgMap;
		}
		// 联系人手机
		if (ValidateUtils.isEmpty(busiContactMobileNo) || !ValidateUtils.isMobile(busiContactMobileNo)) {
			msgMap.put(errorType, "请输入正确格式的手机号码");
			return msgMap;
		}
		// 省市区
		if (ValidateUtils.isEmpty(province) || ValidateUtils.isEmpty(city) || "0".equals(province) || "0".equals(city)) {
			msgMap.put(errorType, "请选择具体地址");
			return msgMap;
		}
		// 通信地址
		if (ValidateUtils.isEmpty(address) || address.length() < 2 || address.length() > 50) {
			msgMap.put(errorType, "请输入2-50个字");
			return msgMap;
		}
		if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorType, "验证码有误");
			return msgMap;
		}
		if (getCurrentUserOperator().getType().intValue() != UserOperatorTypeEnum.ADMIN.getValue()) {
			msgMap.put(errorType, "非管理员不可修改商户信息");
		}
		return msgMap;
	}

	private Map<String, String> validateEditMerchantAll(Integer merchantType, String licenseNo, String cardNo, String fullName, String url, String icp, String busiContactName, String busiContactMobileNo, String address, String scope, String shortName, String province, String city, String area, String legalPerson) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsge = BaseConsts.PAGE_ERROR_MSG;
		// 签约属性验证
		if (ValidateUtils.isEmpty(merchantType)) {
			msgMap.put(errorMsge, "请选择类型");
			return msgMap;
		}
		// 签约名验证
		if (ValidateUtils.isEmpty(fullName) || fullName.length() < 2 || fullName.length() > 30) {
			msgMap.put(errorMsge, "请输入2-30个字");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo)) {
			msgMap.put(errorMsge, "请输入18位正确格式的身份证号");
			return msgMap;
		}

		// 身份证号
		if (merchantType != MerchantTypeEnum.MEMBER.getValue()) {// 营业执照号验证
			if (ValidateUtils.isEmpty(licenseNo) || licenseNo.length() != 15) {
				msgMap.put(errorMsge, "请输入15位的营业执照号");
				return msgMap;
			}
			// 商户简称
			if (ValidateUtils.isEmpty(shortName) || shortName.length() < 2 || shortName.length() > 10) {
				msgMap.put(errorMsge, "请输入2-10的字");
				return msgMap;
			}
		}
		// 经营范围
		if (ValidateUtils.isEmpty(scope) || scope.length() < 2 || scope.length() > 100) {
			msgMap.put(errorMsge, "请输入2-100个字");
			return msgMap;
		}

		// 商城网址
		if (ValidateUtils.isEmpty(url) || !ValidateUtils.isURL(url) || url.length() > 255) {
			msgMap.put(errorMsge, "请输入少于255个字的正确网址");
			return msgMap;
		}
		// ICP证备案号
		if (ValidateUtils.isEmpty(icp) || icp.length() < 2 || icp.length() > 30) {
			msgMap.put(errorMsge, "请输入2-30个字");
			return msgMap;
		}
		// 法人企业代表姓名
		if (ValidateUtils.isEmpty(legalPerson) || legalPerson.length() < 2 || legalPerson.length() > 10 || !ValidateUtils.isChinese(legalPerson)) {
			msgMap.put(errorMsge, "请输入2-10个中文");
			return msgMap;
		}
		// 联系人姓名
		if (ValidateUtils.isEmpty(busiContactName) || busiContactName.length() < 2 || busiContactName.length() > 10 || !ValidateUtils.isChinese(busiContactName)) {
			msgMap.put(errorMsge, "请输入2-10个中文");
			return msgMap;
		}
		// 联系人手机
		if (ValidateUtils.isEmpty(busiContactMobileNo) || !ValidateUtils.isMobile(busiContactMobileNo)) {
			msgMap.put(errorMsge, "请输入正确格式的手机号码");
			return msgMap;
		}
		// 省市区
		if (ValidateUtils.isEmpty(province) || ValidateUtils.isEmpty(city) || "0".equals(province) || "0".equals(city)) {
			msgMap.put(errorMsge, "请选择具体地址");
			return msgMap;
		}
		// 通信地址
		if (ValidateUtils.isEmpty(address) || address.length() < 2 || address.length() > 50) {
			msgMap.put(errorMsge, "请输入2-50个字");
			return msgMap;
		}
		return msgMap;
	}
}
