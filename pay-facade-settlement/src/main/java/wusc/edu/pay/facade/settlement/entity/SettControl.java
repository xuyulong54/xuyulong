package wusc.edu.pay.facade.settlement.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 结算控制实体
 * @author pc-Along
 *
 */
public class SettControl extends BaseEntity{
    /**
	 * 序列
	 */
	private static final long serialVersionUID = 946895328050481368L;
	
    /** 控制状态  参考 SettContorlStatusEnum **/
    private Integer settModeType;

    /** 汇总开始时间 **/
    private Date beginTime;

    /** 汇总结束时间 **/
    private Date endTime;


    /**
     * 控制状态  参考 SettContorlStatusEnum
     * @return
     */
    public Integer getSettModeType() {
        return settModeType;
    }
    /**
     * 控制状态  参考 SettContorlStatusEnum
     * @param settModeType
     */
    public void setSettModeType(Integer settModeType) {
        this.settModeType = settModeType;
    }
    /**
     * 汇总开始时间
     * @return
     */
    public Date getBeginTime() {
        return beginTime;
    }
    /**
     * 汇总开始时间
     * @param beginTime
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    /**
     * 汇总结束时间
     * @return
     */
    public Date getEndTime() {
        return endTime;
    }
    /**
     * 汇总结束时间
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}