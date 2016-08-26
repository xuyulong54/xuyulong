package wusc.edu.pay.facade.bank.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 银行卡BIN参数实体.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-16, 上午11:20:36
 */
public class CardBin extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 卡BIN */
	private String cardBin;
	/** 发卡行代码 */
	private String bankCode;
	/** 发卡行名称 */
	private String bankName;
	/** 卡名 */
	private String cardName;
	/** 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡 */
	private Integer cardKind;
	/** 卡片长度 */
	private Integer cardLength;
	/** 状态:101无效、100有效 */
	private Integer status;
	/** 最后修改人 */
	private Long lastUpdatorId;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 最后修改人姓名*/
	private String lastUpdatorName;
	
	
	/** 最后修改人姓名*/
	public String getLastUpdatorName() {
		return lastUpdatorName;
	}
	/** 最后修改人姓名*/
	public void setLastUpdatorName(String lastUpdatorName) {
		this.lastUpdatorName = lastUpdatorName;
	}

	/**
	 * 卡BIN
	 * @return
	 */
	public String getCardBin() {
		return cardBin;
	}
	
	/**
	 * 卡BIN
	 * @param cardBin
	 */
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	
	/**
	 * 发卡行代码
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}
	
	/**
	 * 发卡行代码
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	/**
	 * 发卡行名称
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}
	
	/**
	 * 发卡行名称
	 * @param bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	/**
	 * 卡名
	 * @return
	 */
	public String getCardName() {
		return cardName;
	}
	
	/**
	 * 卡名
	 * @param cardName
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	/**
	 * 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
	 * @return
	 */
	public Integer getCardKind() {
		return cardKind;
	}
	
	/**
	 * 卡种:1借记卡,2贷记卡,3准贷记卡,4预付费卡
	 * @param cardKind
	 */
	public void setCardKind(Integer cardKind) {
		this.cardKind = cardKind;
	}
	
	/**
	 * 卡片长度
	 * @return
	 */
	public Integer getCardLength() {
		return cardLength;
	}
	
	/**
	 * 卡片长度
	 * @param cardLength
	 */
	public void setCardLength(Integer cardLength) {
		this.cardLength = cardLength;
	}
	
	/**
	 * 状态:101无效、100有效
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 状态:101无效、100有效
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 最后修改人
	 * @return
	 */
	public Long getLastUpdatorId() {
		return lastUpdatorId;
	}
	
	/**
	 * 最后修改人
	 * @param lastUpdatorId
	 */
	public void setLastUpdatorId(Long lastUpdatorId) {
		this.lastUpdatorId = lastUpdatorId;
	}
	
	/**
	 * 最后修改时间
	 * @return
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	/**
	 * 最后修改时间
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
