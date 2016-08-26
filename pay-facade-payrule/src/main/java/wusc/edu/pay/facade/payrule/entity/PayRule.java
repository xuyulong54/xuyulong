package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 支付规则实体.
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:34:04
 * @版本: V1.0
 *
 */
public class PayRule extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8766055310382686658L;

    /** 修改时间 **/
    private Date modifyTime;

    /** 规则名称 **/
    private String ruleName;

    /** 规则类型(100:系统,101:为商户定制,默认100) **/
    private Integer ruleType;

    /** 规则描述 **/
    private String description;
    
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}