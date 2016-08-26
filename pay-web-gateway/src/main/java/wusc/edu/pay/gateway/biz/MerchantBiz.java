package wusc.edu.pay.gateway.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.gateway.exceptions.GateWayException;


@Component("merchantBiz")
public class MerchantBiz {

	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;
	@Autowired
	private  UserManagementFacade userManagementFacade;

	private static final Log log = LogFactory.getLog(MerchantBiz.class);

	/**
	 * 验证账户支付密码是否有效
	 * 
	 * @param accountId
	 * @param trxPassWord 交易密码
	 * @return
	 */
	public String validateAccountPassWord(String loginName, String trxPassWord) {
		int lockMinute = PublicConfig.PWD_ERROR_LIMIT_TIME; // 错误时间范围
		int errMaxTimes = PublicConfig.PWD_ERROR_LIMIT_TIMES; // 错误最大次数
		try { 
			userTradePwdFacade.validTradePwd(loginName, trxPassWord, lockMinute, errMaxTimes);
			return null;
		} catch (UserBizException e) {
			return e.getMsg();
		}
	}
	/**
	 * 用户登陆验证
	 * @param loginName
	 * @param password
	 * @return
	 */
	public String  valiUserPassWord(String loginName, String password,Integer userType){
		try {
			if(userType==UserTypeEnum.CUSTOMER.getValue()){
				//会员登陆
				userManagementFacade.memberLogin(loginName, password, PublicConfig.PWD_ERROR_LIMIT_TIMES, PublicConfig.PWD_ERROR_LIMIT_TIME);
			}else{
				userManagementFacade.merchantLogin(loginName, password, PublicConfig.PWD_ERROR_LIMIT_TIMES, PublicConfig.PWD_ERROR_LIMIT_TIME);
			}
			return null;
		} catch (UserBizException e) {
			return e.getMsg();
		}
		
	}
	/**
	 * 设置商户信息
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	public PaymentRecordVo setMerchantInfo(PaymentOrderVo vo, PaymentRecordVo paymentRecord) throws GateWayException {

		paymentRecord.setMerchantName(vo.getMerchantName()); // 收款方用户名
		paymentRecord.setMerchantNo(vo.getMerchantNo());

		return paymentRecord;
	}

	/**
	 * 检查商户收款功能是否打开
	 * 
	 * @param vo
	 */
	public void checkSwitchPay(PaymentOrderVo vo) {
		// TODO 调用商户限制
	}
	
	/**
	 * 得到登录者信息
	 * 
	 * @param loginName
	 *            登陆名
	 * @return
	 * @throws GateWayException
	 */
	public Account getRegisterInfo(UserInfo userInfo) throws GateWayException {

		Account account = accountQueryFacade.getAccountByAccountNo(userInfo.getAccountNo());
		if (account == null) {
			log.error(String.format("user编号=%s, 账户不存在!", userInfo.getUserNo()));
			throw new GateWayException(GateWayException.GATEWAY_ACCOUNT_NOT_EXIST, "账户不存在");
		}
		return account;
	}

}