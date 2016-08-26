package wusc.edu.pay.web.portal.action.member;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.IDCardUtils;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.file.FastDFSClient;
import wusc.edu.pay.common.web.utils.WebUtil;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;

import com.google.code.kaptcha.Constants;

/**
 * 实名认证
 * 
 * @author liliqiong
 * @date 2013-11-5
 * @version 1.0
 */
public class RealNameAuthAction extends BaseAction {
	private static final Log LOG = LogFactory.getLog(RealNameAuthAction.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserAuditFacade userAuditFacade;

	private File file;// 上传文件本身
	private String fileFileName;// 上传文件的原始文件名
	private String fileContentType;// 上传文件的文件类型

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 去签订协议页面
	 * 
	 * @return
	 */
	public String consentAgreementUI() {
		// 帐户验证标识
		putData("isNotWaitRealName", isNotWaitRealName());
		return "consentAgreementUI";
	}

	/**
	 * 是在在做实名认证申请
	 * 
	 * @return
	 */
	public Boolean isNotWaitRealName() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", getCurrentUserInfo().getUserNo());
		paramMap.put("auditStatus", UserAuditStatusEnum.WAIT.getValue());
		pageBean = userAuditFacade.queryUserAuditRecordRealNameListPage(getPageParam(), paramMap);
		if (ValidateUtils.isEmpty(pageBean) || pageBean.getTotalCount() <= 0) {
			return false;
		}
		return true;
	}
	/**
	 * 签订协议
	 * 
	 * @return
	 */
	public String consentAgreement() {
		return "consentAgreement";
	}

	/**
	 * 去实名认证页面
	 * 
	 * @return
	 */
	public String realNameAuthUI() {
		return "realNameAuthUI";
	}

	/**
	 * 上传身份证正反面.
	 */
	public void uploadFile() {
		String pictureUrl = "";// 图片地址
		getOutputMsg().put("STATE", "SUCCESS");
		getOutputMsg().put("MSG", "上传图片成功");
		Map<String, String> msgMap = validateUploadFile(file);
		if (!msgMap.isEmpty()) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", msgMap.get(BaseConsts.PAGE_ERROR_MSG));
		} else {
			pictureUrl = PublicConfig.FILE_SYS_URL + FastDFSClient.uploadFile(file, fileFileName);
			getOutputMsg().put("IMGURL", pictureUrl);
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 实名认证
	 * 
	 * @return
	 */
	public String realNameAuth() {
		// 页面参数
		String realName = StringTools.stringToTrim(getString("realName"));// 真实姓名
		String cardNo = StringTools.stringToTrim(getString("cardNo"));// 身份证号
		String randomCode = StringTools.stringToTrim(getString("randomCode"));// 验证码
		String cardFrontPath = StringTools.stringToTrim(getString("cardFrontPath"));// 身份证正面
		String cardBackPath = StringTools.stringToTrim(getString("cardBackPath"));// 身份证反面
		String country = StringTools.stringToTrim(getString("country"));// 国籍
		String profession = StringTools.stringToTrim(getString("profession"));// 职业
		String cardNoValid=StringTools.stringToTrim(getString("cardNoValid"));//到期时间
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 身份证有效期
			Date licenseValidValue = null;
			try {
				if(StringUtil.isNotNull(cardNoValid)){
					licenseValidValue = sdf.parse(cardNoValid);
				}else{
					licenseValidValue=sdf.parse("2099-12-31");
				}
			} catch (Exception e) {
				this.addUserLog(OpeStatusEnum.FAIL, "实名认证.");
				LOG.error("时间转换异常", e);
			}
		
		Map<String, String> msgMap = validateSolidattestation(getHttpRequest(), realName, cardNo, randomCode, cardFrontPath, cardBackPath);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "实名认证.");
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		
		// 创建实名认证记录
		userAuditFacade.createUserAuditRecordRealName(cardNo, cardFrontPath, cardBackPath,licenseValidValue, 
				getCurrentUserOperator(), realName, country, profession);
		
		putData("createTime", new Date());
		this.addUserLog(OpeStatusEnum.SUCCESS, "实名认证.");
		return "realNameAuth";
	}

	// ///////////////////////////验证////////////////////////
	public Map<String, String> validateConsentAgreement(String consentAgreement) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorPage = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(consentAgreement) || !"1".equals(consentAgreement)) {
			msgMap.put(errorPage, "必须同意协议");
		}
		return msgMap;
	}

	public Map<String, String> validateSolidattestation(HttpServletRequest req, String realName, String cardNo, String randomCode, String cardFrontPath, String cardBackPath) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorPage = BaseConsts.PAGE_ERROR_MSG;
		// 联系人姓名
		if (ValidateUtils.isEmpty(realName) || realName.length() < 2 || realName.length() > 10 || !ValidateUtils.isChinese(realName)) {
			msgMap.put(errorPage, "真实姓名请输入2-10个中文");
			return msgMap;
		}
		// 身份证号验证
		if (ValidateUtils.isEmpty(cardNo) || !IDCardUtils.verifi(cardNo)) {
			msgMap.put(errorPage, "请输入正确的身份证号");
			return msgMap;
		}
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("cardNo", cardNo);
//		paramMap.put("isRealNameAuth", PublicStatusEnum.ACTIVE.getValue());
//		paramMap.put("userType", AccountTypeEnum.CUSTOMER.getValue());
//		pageBean = userQueryFacade.listUserInfoListPage(getPageParam(), paramMap);

//		if (!ValidateUtils.isEmpty(pageBean) && pageBean.getTotalCount() >= 1) {
//			msgMap.put(errorPage, "此身份证已被其他会员验证");
//			return msgMap;
//		}
		if (ValidateUtils.isEmpty(cardFrontPath) && ValidateUtils.isEmpty(cardBackPath)) {
			msgMap.put(errorPage, "请上传身份证正反面");
			return msgMap;
		}
		// 验证验证码正确性
		String kaptchaCode = (String) WebUtil.getSession(req, Constants.KAPTCHA_SESSION_KEY);
		if (ValidateUtils.isEmpty(randomCode) || !randomCode.equalsIgnoreCase(kaptchaCode))
			msgMap.put(errorPage, "请输入正确的验证码");
		return msgMap;
	}

	public Map<String, String> validateUploadFile(File file) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorPage = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(file)) {
			msgMap.put(errorPage, "1");// 请选择文件
			return msgMap;
		}
		if (file.length() > 2097152) {
			msgMap.put(errorPage, "2");// 上传证件格式为bmp、jpg、jpeg，证件大小不超过2M
			return msgMap;
		}
		if (!"image/jpeg".equals(fileContentType) && !"image/pjpeg".equals(fileContentType) && !"image/bmp".equals(fileContentType)) {
			msgMap.put(errorPage, "3");// 上传证件格式为bmp、jpg、jpeg，证件大小不超过2M
			return msgMap;
		}
		return msgMap;
	}
}
