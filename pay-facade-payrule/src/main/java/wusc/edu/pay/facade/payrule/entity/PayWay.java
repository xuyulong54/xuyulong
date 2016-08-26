package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 支付方式实体.
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:33:43
 * @版本: V1.0
 *
 */
public class PayWay extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5077561250259330119L;
	
    /** 修改时间 **/
    private Date modifyTime;

    /** 支付方式编号 **/
    private String payWayCode;

    /** 支付方式名称 **/
    private String payWayName;

    /** 支付产品编号 **/
    private String payProductCode;

    /** 默认支付接口    **/
    private String defaultPayInterface;

    /** 状态(100:正常状态,101非正常状态)  PayProductStatusEnum **/
    private Integer status;
    
    /** 排序(倒序排序,默认值1000) **/
    private Integer sorts;
    
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

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
    }

    public String getPayProductCode() {
        return payProductCode;
    }

    public void setPayProductCode(String payProductCode) {
        this.payProductCode = payProductCode == null ? null : payProductCode.trim();
    }

    public String getDefaultPayInterface() {
        return defaultPayInterface;
    }

    public void setDefaultPayInterface(String defaultPayInterface) {
        this.defaultPayInterface = defaultPayInterface == null ? null : defaultPayInterface.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getSorts() {
        return sorts;
    }

    public void setSorts(Integer sorts) {
        this.sorts = sorts;
    }
}