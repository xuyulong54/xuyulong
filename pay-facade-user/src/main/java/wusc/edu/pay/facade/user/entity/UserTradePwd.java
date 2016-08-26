package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 用户交易密码对象
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-11,下午6:14:33
 */
public class UserTradePwd extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String loginName; // 登录名
	private String userNo; // 用户编号
	private String tradePwd; // 交易密码
	private Integer pwdErrorTimes;// 输入错误次数
	private Date pwdErrorLastTime = new Date();// 最后一次输入时间
	private Integer isInitialPwd;// 是否是初始密码 如果==1，则是，需提示修改

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * 用户编号
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}
	/**
	 * 用户编号
	 * @return
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 * 登录名
	 * 
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 交易密码
	 * 
	 * @return
	 */
	public String getTradePwd() {
		return tradePwd;
	}

	/**
	 * 交易密码
	 * 
	 * @param tradePwd
	 */
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	/**
	 * 密码错误次数
	 * 
	 * @return
	 */
	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}

	/**
	 * 交易密码错误次数
	 * 
	 * @param pwdErrorTimes
	 */
	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}

	/**
	 * 交易密码最后错误时间
	 * 
	 * @return
	 */
	public Date getPwdErrorLastTime() {
		return pwdErrorLastTime;
	}

	/**
	 * 交易密码最后错误时间
	 * 
	 * @param pwdErrorLastTime
	 */
	public void setPwdErrorLastTime(Date pwdErrorLastTime) {
		this.pwdErrorLastTime = pwdErrorLastTime;
	}

	/**
	 * 是否是初始密码
	 * @return
	 */
	public Integer getIsInitialPwd() {
		return isInitialPwd;
	}
	/**
	 * 是否是初始密码
	 * @param isInitialPwd
	 */
	public void setIsInitialPwd(Integer isInitialPwd) {
		this.isInitialPwd = isInitialPwd;
	}

}
