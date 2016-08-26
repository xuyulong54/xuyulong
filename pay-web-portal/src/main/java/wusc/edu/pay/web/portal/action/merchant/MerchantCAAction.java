//package wusc.edu.pay.web.portal.action.merchant;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//
//import wusc.edu.pay.common.enums.OpeStatusEnum;
//import wusc.edu.pay.common.enums.ca.CAAddrEnum;
//import wusc.edu.pay.common.enums.ca.CAEnum;
//import wusc.edu.pay.common.utils.string.StringTools;
//import wusc.edu.pay.common.web.annotation.Permission;
//import wusc.edu.pay.facade.user.entity.PortalCa;
//import wusc.edu.pay.facade.user.service.PortalCaFacade;
//import wusc.edu.pay.web.portal.base.BaseAction;
//import wusc.edu.pay.web.portal.base.BaseConsts;
//
//
///**
// * 数字证书管理
// * 
// * @author liliqiong
// * @date 2014-1-7
// * @version 1.0
// */
//@Scope("prototype")
//public class MerchantCAAction extends BaseAction {
//	private static final long serialVersionUID = 1L;
//	@Autowired
//	private PortalCaFacade portalCaFacade;
//
//	/**
//	 * certApplyFor: 证书申请<br/>
//	 */
//	@Permission("SecurityCenter:Ca")
//	public void certApplyFor() {
//		try {
//			String p10Str = getString("p10Str");
//			PortalCa caVo = CaUtils.certApplyDown(getCurrentUserOperator().getId(), p10Str.replace(" ", ""));// 申请证书与下载
//			getOutputMsg().put("RANDS", String.valueOf(cfca.ca.toolkit.util.CommonUtil.genRandomNum(6)));
//			getOutputMsg().put("SIGNCERT", caVo.getSignCert());
//			getOutputMsg().put("SN", caVo.getSn());
//			getOutputMsg().put("STATE", "SUCCESS");
//		} catch (Exception e) {
//			getOutputMsg().put("STATE", "FAIL");
//			getOutputMsg().put("MSG", "申请证书，出现错误！");
//			e.printStackTrace();
//		}
//		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
//	}
//
//	// 签名认证之后保存用户DN信息
//	@Permission("SecurityCenter:Ca")
//	public void certInsert() throws Exception {
//		String sn = getString("sn");
//		String addrId = getString("addrId");
//
//		// 设置CA信息
//		Cert cert = CaUtils.CertInfoQuery(sn);
//		PortalCa vo = new PortalCa();
//		vo.setAddrId(Integer.parseInt(addrId));
//		vo.setSn(sn);
//		vo.setDn(cert.getDn());
//		vo.setUserNo(getCurrentUserInfo().getUserNo());
//		vo.setUserId(getCurrentUserOperator().getId());
//		vo.setUserType(getCurrentUserInfo().getUserType());
//		vo.setStatus(CAEnum.REGULAR.getValue());
//
//		// 保存用户CA证书信息
//		portalCaFacade.saveOrUpdateProtalCA(vo);
//
//		// 保存到当前应用 的Session中
//		getSessionMap().put(BaseConsts.CURRENT_SN, sn);
//
//		// 记录操作日志
//		this.addUserLog(OpeStatusEnum.SUCCESS, String.format("%s在%s安装数字证书成功。", getCurrentUserInfo().getLoginName(), CAAddrEnum.getEnum(Integer.parseInt(addrId)).getDesc()));
//
//		outPrint(this.getHttpResponse(), "[{\"mgs\":\"申请成功\"}]");
//	}
//
//	/**
//	 * 数字证书列表
//	 */
//	@Permission("SecurityCenter:Ca")
//	public String listCA() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		//map.put("userId", getCurrentUserOperator().getId());
//		map.put("userNo", super.getCurrentUserOperator().getUserNo());
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
//	@Permission("SecurityCenter:Ca")
//	public String addCAIntro() {
//		putData("isMobileAuth", getCurrentUserInfo().getIsMobileAuth());
//		putData("bindMobileNo", StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
//		return "addCAIntro";
//	}
//
//	/**
//	 * 2.申请数字证书_去输入信息页面
//	 * 
//	 * @return
//	 */
//	@Permission("SecurityCenter:Ca")
//	public String addCAImInfoUI() {
//		putData("addrs", CAAddrEnum.values());
//		putData("isMobileAuth", getCurrentUserInfo().getIsMobileAuth());
////		if (getCurrentUserInfo().getUserType() == UserTypeEnum.MERCHANT.getValue()) {
//			putData("merchantShortName", this.getMerchantOnline().getShortName());
////		}
//		return "addCAImInfoUI";
//	}
//
//	/**
//	 * 3.申请数字证书_保存输入信息
//	 */
//	@Permission("SecurityCenter:Ca")
//	public String addCAImInfo() {
//
//		return "addCAImInfo";
//	}
//
//	/**
//	 * 删除数字证书 ==>证书的吊销
//	 */
//	@Permission("SecurityCenter:Ca")
//	public void delCA() {
//		long caId = getInteger("id");
//		PortalCa vo = portalCaFacade.getProtalCAById(caId);
//
//		// 删除数字证书
//		CaUtils.certRevoke(vo.getSn());
//		portalCaFacade.deleteProtalCA(caId);
//
//		// 清空session的证书
//		String currentSn = (String) this.getSessionMap().get("CURRENT_SN");
//		if (currentSn != null && currentSn.equals(vo.getSn())) {
//			this.getSessionMap().put(BaseConsts.CURRENT_SN, null);
//		}
//
//		// 记录操作日志
//		this.addUserLog(OpeStatusEnum.SUCCESS, String.format("%s删除数字证书", getCurrentUserInfo().getLoginName()));
//	}
//
//	/**
//	 * 下载CA控件
//	 */
//	public String downloadCAControl() {
//		return "downloadCAControl";
//	}
//}
