package wusc.edu.pay.gateway.biz;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;
import wusc.edu.pay.gateway.exceptions.GateWayException;


@Component("userBiz")
public class UserBiz {

	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	private static final Log log = LogFactory.getLog(UserBiz.class);

	/**
	 * 检查用户，并验证签名
	 * 
	 * @param vo
	 * @param p2_LoginName
	 *            会员登录名.
	 */
	public void verifyUser(RechargeOrderVo vo, String p2_LoginName, String hmac) {
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(p2_LoginName);

		// 判断用是是否存在
		if (userInfo == null) {
			throw new GateWayException(GateWayException.GATEWAY_USER_NOT_EXIST, "用户不存在。");
		}

		vo.setPayerAccountType(EnumChangeUtils.getAccountType(userInfo.getUserType()));
		vo.setPayerUserNo(userInfo.getUserNo()); // 付款人编号
		vo.setPayerName(userInfo.getRealName()); // 付款人姓名

		Account account = accountQueryFacade.getAccountByUserNo(vo.getPayerUserNo());
		if (account == null) {
			throw new GateWayException(GateWayException.GATEWAY_ACCOUNT_NOT_EXIST, "虚拟账户不存在");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("p1_Amount=").append(vo.getOrderAmount());
		buffer.append("&p2_LoginName=").append(p2_LoginName);
		buffer.append("&p3_ReturnUrl=").append(vo.getReturnUrl());
		buffer.append("&p4_OrderNo=").append(vo.getMerchantOrderNo());

		String hmacSign = DigestUtils.md5Hex(buffer.toString() + p2_LoginName);
		log.info("===>hmac:" + hmac);
		log.info("===>hmacSign:" + hmacSign);
		if (!hmac.equals(hmacSign)) {
			throw new GateWayException(GateWayException.GATEWAY_VALIDATE_HMAC_IS_FAILED, "验证签名失败。");
		}

		log.info(String.format("订单%s  验证充值账号 并验证签名 ", vo.getMerchantOrderNo()));
	}
}
