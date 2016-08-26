package wusc.edu.pay.gateway.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.gateway.biz.MerchantBiz;
import wusc.edu.pay.gateway.utils.Context;


/**
 * ClassName: GateWayAction <br/>
 * Function: 基础类<br/>
 * date: 2014-7-30 下午12:10:56 <br/>
 * 
 * @author laich
 */
public class GateWayAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 9022381194463683927L;
	private static final Log log = LogFactory.getLog(GateWayAction.class);

	@Autowired
	private MerchantBiz merchantBiz;

	/**
	 * 设置用户的CA证书
	 */
	public void setCASession() {
		super.getSessionMap().put(Context.CURRENT_SN, getString("SN"));
	}

	public String loginPayment() {
		
		//进入登陆页面,系统是否启用密码控件
		if(Context.USE_KEYBOARD){
			super.putData("USE_KEYBOARD", Context.USE_KEYBOARD);
		}
		
		if (this.getCurrentUserInfo() == null || this.getPaymentOrderVo() == null) {// 未登陆
			return "loginPayment";
		} else {
			// 查出
			this.confirmOrderInfo(Double.parseDouble(getPaymentOrderVo().getOrderAmount()), getCurrentUserInfo().getLoginName(),
					getCurrentUserInfo());
			return "confirmPayment";
		}
	}

	/**
	 * 获取SESSION的用户信息.<br/>
	 * 
	 * @return
	 */
	public UserInfo getCurrentUserInfo() {
		return (UserInfo) this.getSessionMap().get(Context.CURRENT_USER);
	}

	/**
	 * 设置SESSION值
	 * 
	 * @param userVo
	 */
	public void setCurrentUserInfo(UserInfo userInfo) {
		this.getSessionMap().put(Context.CURRENT_USER, userInfo);
	}

	/**
	 * 设置PaymentOrderVo 值 每次从进入网关订单信息都存放到Session中
	 * 
	 * @param userVo
	 */
	public void setPaymentOrderVo(PaymentOrderVo vo) {
		this.getSessionMap().put(Context.CURRENT_ORDER, vo);
	}

	public PaymentOrderVo getPaymentOrderVo() {
		return (PaymentOrderVo) this.getSessionMap().get(Context.CURRENT_ORDER);
	}

	/**
	 * 确认订单信息
	 * 
	 * @param orderAmount
	 * @param userName
	 * @param userInfo
	 */
	protected void confirmOrderInfo(Double orderAmount, String loginName, UserInfo userInfo) {
		Account account = merchantBiz.getRegisterInfo(userInfo);
		// 查找出会员的账户
		double balance = AmountUtil.sub(account.getBalance(), account.getUnBalance());// 得到可用余额
		// 显示账户余额
		putData("balance", balance);
		getSessionMap().put("userInfo", userInfo);
		getSessionMap().put("token", "token");
	}
	
	/**
	 * 包括是否启用密码键盘
	 * @param key
	 */
	public String getPwd(String key){
		return StringTools.stringToTrim(getString(key));
	}
}
