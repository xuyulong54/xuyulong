package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 支付方式开关实体.
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:36:06
 * @版本: V1.0
 *
 */
public class PayWaySwitch extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7571204124327059004L;

    /** 修改时间 **/
    private Date modifyTime;

    /** 支付方式编号 **/
    private String payWayCode;

    /** 支付产品编号 **/
    private String payProductCode;

    /** 支付规则ID **/
    private Long payRuleId;

    /** 开关状态(100 开;101关,默认100) **/
    private Integer status;
    
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
    }

    public String getPayProductCode() {
        return payProductCode;
    }

    public void setPayProductCode(String payProductCode) {
        this.payProductCode = payProductCode == null ? null : payProductCode.trim();
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
}