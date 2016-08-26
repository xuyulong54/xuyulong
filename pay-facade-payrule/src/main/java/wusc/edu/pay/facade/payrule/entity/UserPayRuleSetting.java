package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 用户支付规则关联表(一个用户只有一条规则).
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:37:21
 * @版本: V1.0
 *
 */
public class UserPayRuleSetting extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7221682252441508984L;

    /** 修改时间 **/
    private Date modifyTime;

    /** 用户编号 **/
    private String userNo;

    /** 支付规则ID **/
    private Long payRuleId;

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Long getPayRuleId() {
        return payRuleId;
    }

    public void setPayRuleId(Long payRuleId) {
        this.payRuleId = payRuleId;
    }
}