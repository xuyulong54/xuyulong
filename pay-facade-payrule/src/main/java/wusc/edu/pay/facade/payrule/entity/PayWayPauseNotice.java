package wusc.edu.pay.facade.payrule.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 支付方式暂停公告实体.
 * @作者: WuShuicheng.
 * @创建: 2014-6-26,下午5:32:19
 * @版本: V1.0
 *
 */
public class PayWayPauseNotice extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 551148297367700139L;

    /** 修改时间 **/
    private Date modifyTime;

    /** 支付方式编码 **/
    private String payWayCode;

    /** 暂停服务信息 **/
    private String pauseNoticeMsg;

    /** 暂停服务范围(100:全部,101:部分) **/
    private Integer pauseScope;

    /** 是否有效(100:有效,101:无效) **/
    private Integer isEnable;

    /** 暂停服务起始时 **/
    private Date pauseStartTime;

    /** 暂停服务截止时间 **/
    private Date pauseEndTime;
    
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

    public String getPauseNoticeMsg() {
        return pauseNoticeMsg;
    }

    public void setPauseNoticeMsg(String pauseNoticeMsg) {
        this.pauseNoticeMsg = pauseNoticeMsg == null ? null : pauseNoticeMsg.trim();
    }

    public Integer getPauseScope() {
        return pauseScope;
    }

    public void setPauseScope(Integer pauseScope) {
        this.pauseScope = pauseScope;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getPauseStartTime() {
        return pauseStartTime;
    }

    public void setPauseStartTime(Date pauseStartTime) {
        this.pauseStartTime = pauseStartTime;
    }

    public Date getPauseEndTime() {
        return pauseEndTime;
    }

    public void setPauseEndTime(Date pauseEndTime) {
        this.pauseEndTime = pauseEndTime;
    }
}