package wusc.edu.pay.facade.payrule.entity.vo;

import java.io.Serializable;

import wusc.edu.pay.common.enums.BankCode;


/**
 * ClassName: FrpSelectVo <br/>
 * Function: 用于运营后 绑定支付方式 <br/>
 * date: 2014-7-18 下午3:18:58 <br/>
 * @author laich
 */
public class FrpSelectVo implements Serializable{
	
	private static final long serialVersionUID = -5222205583937823061L;
	private String frpCode; // 支付渠道编号
	private String bankName; // 银行名称
	private BankCode bankCode; // 银行编号
	private Integer status; // 状态
	private String busType;// 业务类型，B2C,B2B，快捷
	private Integer payType; //付款类型

	private Long	payWayId;
	
	private Boolean isUse;//是否使用
	private String payProductCode;
	private Integer sorts;
    /** 默认支付接口    **/
    private String defaultPayInterface;

	public String getFrpCode() {
		return frpCode;
	}

	public void setFrpCode(String frpCode) {
		this.frpCode = frpCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BankCode getBankCode() {
		return bankCode;
	}

	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(Long payWayId) {
		this.payWayId = payWayId;
	}

	public Boolean getIsUse() {
		return isUse;
	}

	public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	public String getDefaultPayInterface() {
		return defaultPayInterface;
	}

	public void setDefaultPayInterface(String defaultPayInterface) {
		this.defaultPayInterface = defaultPayInterface;
	}
	
	
}
