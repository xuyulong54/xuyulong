package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 支付产品实体.
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:15:22
 * @版本: V1.0
 *
 */
public class PayProduct extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6331930937186024177L;
	
	/** 修改时间 **/
    private Date modifyTime;

    /** 支付产品编号 **/
    private String payProductCode;

    /** 支付产品名称 **/
    private String payProductName;

    /** 状态(100 正常,102非正常) **/
    private Integer status;


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
        this.payProductCode = payProductCode == null ? null : payProductCode.trim();
    }

    public String getPayProductName() {
        return payProductName;
    }

    public void setPayProductName(String payProductName) {
        this.payProductName = payProductName == null ? null : payProductName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}