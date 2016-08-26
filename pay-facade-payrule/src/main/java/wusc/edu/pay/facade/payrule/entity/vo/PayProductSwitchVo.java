package wusc.edu.pay.facade.payrule.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: PayProductSwitchVo  <br/>
 * Function:  用于前台展示<br/>
 * date: 2014-7-1 下午4:29:40 <br/>
 * 
 * @author laich
 */
public class PayProductSwitchVo  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	/** 修改时间 **/
    private Date modifyTime;

    /** 支付产品编号 **/
    private String payProductCode;
    
    /** 支付产品名称 **/
    private String payProductName;

    /** 支付规则ID **/
    private Long payRuleId;

    /** 开关状态(100 开;101关,默认101) **/
    private Integer status;
    //是否已被使用 默认为 false
    private boolean isUse = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public Long getPayRuleId() {
		return payRuleId;
	}

	public void setPayRuleId(Long payRuleId) {
		this.payRuleId = payRuleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}
    
}
