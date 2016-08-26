package wusc.edu.pay.facade.settlement.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 结算失败记录
 * @author pc-Along
 *
 */
public class SettErrorRecord extends BaseEntity{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = -597067040989945091L;

    /** 用户编号 **/
    private String userNo;

    /** 用户名称 **/
    private String userName;

    /** 账户编号 **/
    private String accountNo;

    /** 结算日期 **/
    private Date settDate;

    /** 结算发起方式  SettModeTypeEnum **/
    private Integer settMode;

    /** 结算失败原因 **/
    private String errDesc;

    /** 结算失败处理状态  参考 SettErrorRecordStatusEnum **/
    private Integer status;

    /** 结算开始日期 **/
    private Date beginDate;

    /** 结算结束日期 **/
    private Date endDate;

    /** 操作员登录名 **/
    private String operatorLoginName;

    /** 操作员名称 **/
    private String operatorRealName;

    /** 描述 **/
    private String remark;

    /** 用户编号 **/
    public String getUserNo() {
        return userNo;
    }
    /** 用户编号 **/
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }
    /** 用户名称 **/
    public String getUserName() {
        return userName;
    }
    /** 用户名称 **/
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    /** 账户编号 **/
    public String getAccountNo() {
        return accountNo;
    }
    /** 账户编号 **/
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }
    /** 结算日期 **/
    public Date getSettDate() {
        return settDate;
    }
    /** 结算日期 **/
    public void setSettDate(Date settDate) {
        this.settDate = settDate;
    }
    /** 结算发起方式  SettModeTypeEnum **/
    public Integer getSettMode() {
        return settMode;
    }
    /** 结算发起方式  SettModeTypeEnum **/
    public void setSettMode(Integer settMode) {
        this.settMode = settMode;
    }
    /** 结算失败原因 **/
    public String getErrDesc() {
        return errDesc;
    }
    /** 结算失败原因 **/
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc == null ? null : errDesc.trim();
    }
    /** 结算失败处理状态  参考 SettErrorRecordStatusEnum **/
    public Integer getStatus() {
        return status;
    }
    /** 结算失败处理状态  参考 SettErrorRecordStatusEnum **/
    public void setStatus(Integer status) {
        this.status = status;
    }
    /** 结算开始日期 **/
    public Date getBeginDate() {
        return beginDate;
    }
    /** 结算开始日期 **/
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    /** 结算结束日期 **/
    public Date getEndDate() {
        return endDate;
    }
    /** 结算结束日期 **/
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    /** 操作员登录名 **/
    public String getOperatorLoginName() {
        return operatorLoginName;
    }
    /** 操作员登录名 **/
    public void setOperatorLoginName(String operatorLoginName) {
        this.operatorLoginName = operatorLoginName == null ? null : operatorLoginName.trim();
    }
    /** 操作员名称 **/
    public String getOperatorRealName() {
        return operatorRealName;
    }
    /** 操作员名称 **/
    public void setOperatorRealName(String operatorRealName) {
        this.operatorRealName = operatorRealName == null ? null : operatorRealName.trim();
    }
    /** 描述 **/
    public String getRemark() {
        return remark;
    }
    /** 描述 **/
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}