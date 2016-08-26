//package wusc.edu.pay.web.portal.action.member;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import wusc.edu.pay.common.enums.ca.CAAddrEnum;
//import wusc.edu.pay.common.enums.ca.CAEnum;
//import wusc.edu.pay.common.utils.string.StringTools;
//import wusc.edu.pay.common.utils.validate.ValidateUtils;
//import wusc.edu.pay.facade.user.entity.PortalCa;
//import wusc.edu.pay.facade.user.enums.UserTypeEnum;
//import wusc.edu.pay.facade.user.service.PortalCaFacade;
//import wusc.edu.pay.web.portal.base.BaseAction;
//
//
///**
// * ClassName: MemberCAAction <br/>
// * Function: 会员数字证书管理   
// * <per>
// * 1 步 证书申请 ，证书申请时生成 p10Str 和一个随机数返回客户端验证
// * 2 客户端安装证书
// * 3 保存用户证书信息
// * </per>
// * <br/>
// * date: 2014-1-10 下午8:17:20 <br/>
// * 
// * @author laich
// */
//@Scope("prototype")
//public class MemberCAAction extends BaseAction {
//	private static final long serialVersionUID = 1L;
//	private static final Log LOG = LogFactory.getLog(MemberCAAction.class);
//	@Autowired
//	private PortalCaFacade portalCaFacade;
//	
//	/**
//	 * certApplyFor: 证书申请<br/>
//	 */
//	public void certApplyFor() {
//		HttpServletResponse resp = null;
//		long userId = getCurrentUserOperator().getId();
//		String p10Str = getString("p10Str");
//		// 申请证书
//		PortalCa caVo = CaUtils.certApplyDown(userId, p10Str.replace(" ", ""));// 申请证书与下载
//		// 生成随机数
//		String rands = cfca.ca.toolkit.util.CommonUtil.genRandomNum(6);
//		resp = getHttpResponse();
//		resp.setCharacterEncoding("utf-8");
//		try {
//			resp.getWriter().write("[{\"rands\":\"" + rands + "\",\"signCert\":\"" + caVo.getSignCert() + "\",\"sn\":\"" + caVo.getSn() + "\"}]");
//		} catch (IOException e) {
//			LOG.error("certApplyFor fail:", e);
//		} finally {
//			try {
//				if(!ValidateUtils.isEmpty(resp)){
//				resp.getWriter().close();}
//			} catch (IOException e) {
//				LOG.error("certApplyFor fail:", e);
//			}
//		}
//	}
//
//	// 签名认证之后保存用户DN信息
//	public void certInsert() throws Exception {
//		String sn = getString("sn");
//		Integer userType = UserTypeEnum.CUSTOMER.getValue();
//		String addrId = getString("addrId");
//		HttpServletResponse resp = null;
//		try {
//			// cfca.util.cipher.lib.Session session = (Session)
//			// JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
//			// 对签名解密
//			// 保存DN信息
//			// 证书信息查询
//
//			Cert cert = CaUtils.CertInfoQuery(sn);
//			PortalCa vo = new PortalCa();
//			vo.setAddrId(Integer.parseInt(addrId));
//			vo.setSn(sn);
//			vo.setDn(cert.getDn());
//			// vo.setMerchantId(getMerchant().getId());
//			vo.setUserId(getCurrentUserOperator().getId());
//			vo.setUserNo(getCurrentUserInfo().getUserNo());
//			vo.setUserType(userType);//
//			vo.setStatus(CAEnum.REGULAR.getValue());
//			portalCaFacade.saveOrUpdateProtalCA(vo);// 保存用户CA证书信息
////			logSave(null, getCurrentUserBySession().getAccountType().getValue(), "安全数字证书成功:[地点：" + CAAddrEnum.getEnum(Integer.parseInt(addrId)).getDesc() + "]");
//			getSessionMap().put("CURRENT_SN", sn);// 保存到当前应用 的Session中
//			resp = getHttpResponse();
//			resp.setCharacterEncoding("utf-8");
//			resp.getWriter().write("[{\"mgs\":\"申请成功\"}]");
//		} catch (Exception e) {
//			LOG.error("certInsert fail:", e);
//		} finally {
//			try {
//				if(!ValidateUtils.isEmpty(resp)){
//				resp.getWriter().close();}
//			} catch (IOException e) {
//				LOG.error("certInsert fail:", e);
//			}
//		}
//	}
//
//	/**
//	 * 数字证书列表
//	 */
//	public String listCA() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", getCurrentUserOperator().getId());
//		map.put("userType", UserTypeEnum.CUSTOMER.getValue());
//		pageBean = portalCaFacade.queryProtalCAListPage(getPageParam(), map);
//		putData("addrs", CAAddrEnum.values());
//		putData("proMap", map);
//		pushData(pageBean);
//		return "listCA";
//	}
//
//	/**
//	 * 1.申请数字证书_数字证书简介
//	 */
//	public String addCAIntro() {
//		putData("bindMobileNo", StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
//		return "addCAIntro";
//	}
//
//	/**
//	 * 2.申请数字证书_去输入信息页面
//	 * 
//	 * @return
//	 */
//	public String addCAImInfoUI() {
//		putData("addrs", CAAddrEnum.values());
//		putData("bindMobileNo", StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
//		putData("membertInfoParam", getMemberInfo());
//		return "addCAImInfoUI";
//	}
//
//	/**
//	 * 3.申请数字证书_保存输入信息
//	 */
//	public String addCAImInfo() {
//		return "addCAImInfo";
//	}
//
//	/**
//	 * 删除数字证书 ==>证书的吊销
//	 */
//	public void delCA() {
//		long caId = getInteger("id");
//		PortalCa vo = portalCaFacade.getProtalCAById(caId);
//		CaUtils.certRevoke(vo.getSn());
//		// delete
//		portalCaFacade.deleteProtalCA(caId);
////		logDelete(null, getCurrentUser().getUserType(), "删除数字证书成功:[id：" + vo.getId() + "]"); TODO 加操作日志
//		String currentSn = (String) this.getSessionMap().get("CURRENT_SN");
//		if (currentSn != null && currentSn.equals(vo.getSn())) {
//			this.getSessionMap().put("CURRENT_SN", "");
//		}
//	}
//
//	/**
//	 * 下载CA控件
//	 */
//	public String downloadCAControl() {
//		return "downloadCAControl";
//	}
//
//}
