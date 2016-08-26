package wusc.edu.pay.facade.notify.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;


/**
 * 
 * @描述: 通知记录实体.
 * @作者: WuShuicheng.
 * @创建: 2014-7-18,下午4:57:30
 * @版本: V1.0
 * 
 */
public class NotifyRecord extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6104194914044220447L;


	/** 最后一次通知时间 **/
	private Date lastNotifyTime;

	/** 通知次数 **/
	private Integer notifyTimes = 0;

	/** 限制通知次数 **/
	private Integer limitNotifyTimes = 5;

	/** 通知URL **/
	private String url;

	/** 商户编号 **/
	private String merchantNo;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 通知状态(100:成功:101:未成功,默认101) **/
	private Integer status = 101;

	/** 通知类型 NotifyTypeEnum **/
	private Integer notifyType = 0;

	public NotifyRecord() {
		super();
	}

	public NotifyRecord(Date createTime, Date lastNotifyTime, Integer notifyTimes, Integer limitNotifyTimes, String url, String merchantNo,
			String merchantOrderNo, NotifyStatusEnum status, NotifyTypeEnum type) {
		super();
		this.createTime = createTime;
		this.lastNotifyTime = lastNotifyTime;
		this.notifyTimes = notifyTimes;
		this.limitNotifyTimes = limitNotifyTimes;
		this.url = url;
		this.merchantNo = merchantNo;
		this.merchantOrderNo = merchantOrderNo;
		this.status = status.getValue();
		this.notifyType = type.getValue();
	}


	/** 最后一次通知时间 **/
	public Date getLastNotifyTime() {
		return lastNotifyTime;
	}

	/** 最后一次通知时间 **/
	public void setLastNotifyTime(Date lastNotifyTime) {
		this.lastNotifyTime = lastNotifyTime;
	}

	/** 通知次数 **/
	public Integer getNotifyTimes() {
		return notifyTimes;
	}

	/** 通知次数 **/
	public void setNotifyTimes(Integer notifyTimes) {
		this.notifyTimes = notifyTimes;
	}

	/** 限制通知次数 **/
	public Integer getLimitNotifyTimes() {
		return limitNotifyTimes;
	}

	/** 限制通知次数 **/
	public void setLimitNotifyTimes(Integer limitNotifyTimes) {
		this.limitNotifyTimes = limitNotifyTimes;
	}

	/** 通知URL **/
	public String getUrl() {
		return url;
	}

	/** 通知URL **/
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	/** 商户编号 **/
	public String getMerchantNo() {
		return merchantNo;
	}

	/** 商户编号 **/
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	/** 商户订单号 **/
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/** 商户订单号 **/
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
	}

	/** 通知状态(100:成功:101:未成功,默认101) **/
	public Integer getStatus() {
		return status;
	}

	/** 通知状态(100:成功:101:未成功,默认101) **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}
}
