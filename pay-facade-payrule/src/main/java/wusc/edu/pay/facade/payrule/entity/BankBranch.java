package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 银行分流参数实体.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午3:34:12
 */
public class BankBranch extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 最后修改时间 */
	private Date lastTime = new Date();
	/** 支付渠道编号 */
	private String frpCode;
	/** 默认银行渠道 */
	private String defaultBankChannelCode;
	/** 备用银行渠道 */
	private String spareBankChannelCode;
	/** 描述 */
	private String remark;


	/**
	 * 最后修改时间
	 * @return
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * 最后修改时间
	 * @param lastTime
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * 支付渠道编号
	 * @return
	 */
	public String getFrpCode() {
		return frpCode;
	}

	/**
	 * 支付渠道编号
	 * @param frpCode
	 */
	public void setFrpCode(String frpCode) {
		this.frpCode = frpCode;
	}

	/**
	 * 默认银行渠道
	 * @return
	 */
	public String getDefaultBankChannelCode() {
		return defaultBankChannelCode;
	}

	/**
	 * 默认银行渠道
	 * @param defaultBankChannelCode
	 */
	public void setDefaultBankChannelCode(String defaultBankChannelCode) {
		this.defaultBankChannelCode = defaultBankChannelCode;
	}

	/**
	 * 备用银行渠道
	 * @return
	 */
	public String getSpareBankChannelCode() {
		return spareBankChannelCode;
	}

	/**
	 * 备用银行渠道
	 * @param spareBankChannelCode
	 */
	public void setSpareBankChannelCode(String spareBankChannelCode) {
		this.spareBankChannelCode = spareBankChannelCode;
	}

	/**
	 * 描述
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 描述
	 * @param desc
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
