package wusc.edu.pay.facade.settlement.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 结算日调整设置实体
 * @author pc-Along
 *
 */
public class SettDayAdjustSetting extends BaseEntity{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 1854914413479731180L;

    /** 原日期 **/
    private Date oldDate;

    /** 新日期 **/
    private Date newDate;

    /** 描述 **/
    private String remark;

    /**
     * 原日期
     * @return
     */
    public Date getOldDate() {
        return oldDate;
    }
    /**
     * 原日期
     * @param oldDate
     */
    public void setOldDate(Date oldDate) {
        this.oldDate = oldDate;
    }
    /**
     * 新日期
     * @return
     */
    public Date getNewDate() {
        return newDate;
    }
    /**
     * 新日期
     * @param newDate
     */
    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }
    /**
     * 描述
     * @return
     */
    public String getRemark() {
        return remark;
    }
    /**
     *描述
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}