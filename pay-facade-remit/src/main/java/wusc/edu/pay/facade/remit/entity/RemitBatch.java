package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @Title: 打款处理批次实体
 * @Description:
 * @author zzh
 * @date 2014-7-22 上午11:38:45
 */
public class RemitBatch extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String batchNo; // 打款批次号

	private Integer status; // 状态:1-待处理，2-处理中，3-处理完成

	private Integer totalNum = 0; // 总笔数

	private BigDecimal totalAmount = BigDecimal.ZERO; // 总金额

	private Integer acceptSucNum = 0; // 受理成功笔数

	private BigDecimal acceptSucAmount = BigDecimal.ZERO; // 受理成功金额

	private Integer acceptFailNum = 0; // 受理失败笔数

	private BigDecimal acceptFailAmount = BigDecimal.ZERO; // 受理失败金额

	private Integer processSucNum = 0; // 处理成功笔数

	private BigDecimal processSucAmount = BigDecimal.ZERO; // 处理成功金额

	private Integer processFailNum = 0; // 处理失败笔数

	private BigDecimal processFailAmount = BigDecimal.ZERO; // 处理失败金额

	private Date createDate; // 创建时间

	private Date acceptDate; // 受理时间

	private Date processDate; // 处理时间

	private String confirm;

	private Date confirmDate;

	/**
	 * 打款通道编号
	 */
	private String remitChannelCode;

	/**
	 * 是否支持自动打款:100-是，101-否
	 */
	private Integer isAutoRemit;

	/**
	 * 付款银行账户
	 */
	private String remitBankAccountNo;

	public String getRemitBankAccountNo() {
		return remitBankAccountNo;
	}

	public void setRemitBankAccountNo(String remitBankAccountNo) {
		this.remitBankAccountNo = remitBankAccountNo;
	}

	public Integer getIsAutoRemit() {
		return isAutoRemit;
	}

	public void setIsAutoRemit(Integer isAutoRemit) {
		this.isAutoRemit = isAutoRemit;
	}

	public String getRemitChannelCode() {
		return remitChannelCode;
	}

	public void setRemitChannelCode(String remitChannelCode) {
		this.remitChannelCode = remitChannelCode;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * @return 打款批次号
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param 打款批次号
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return 状态:1-待处理，2-处理中，3-处理完成
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态
	 *            :1-待处理，2-处理中，3-处理完成
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return 总笔数
	 */
	public Integer getTotalNum() {
		return totalNum;
	}

	/**
	 * @param 总笔数
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return 总金额
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param 总金额
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return 受理成功笔数
	 */
	public Integer getAcceptSucNum() {
		return acceptSucNum;
	}

	/**
	 * @param 受理成功笔数
	 */
	public void setAcceptSucNum(Integer acceptSucNum) {
		this.acceptSucNum = acceptSucNum;
	}

	/**
	 * @return 受理成功金额
	 */
	public BigDecimal getAcceptSucAmount() {
		return acceptSucAmount;
	}

	/**
	 * @param 受理成功金额
	 */
	public void setAcceptSucAmount(BigDecimal acceptSucAmount) {
		this.acceptSucAmount = acceptSucAmount;
	}

	/**
	 * @return 受理失败笔数
	 */
	public Integer getAcceptFailNum() {
		return acceptFailNum;
	}

	/**
	 * @param 受理失败笔数
	 */
	public void setAcceptFailNum(Integer acceptFailNum) {
		this.acceptFailNum = acceptFailNum;
	}

	/**
	 * @return 受理失败金额
	 */
	public BigDecimal getAcceptFailAmount() {
		return acceptFailAmount;
	}

	/**
	 * @param 受理失败金额
	 */
	public void setAcceptFailAmount(BigDecimal acceptFailAmount) {
		this.acceptFailAmount = acceptFailAmount;
	}

	/**
	 * @return 处理成功笔数
	 */
	public Integer getProcessSucNum() {
		return processSucNum;
	}

	/**
	 * @param 处理成功笔数
	 */
	public void setProcessSucNum(Integer processSucNum) {
		this.processSucNum = processSucNum;
	}

	/**
	 * @return 处理成功金额
	 */
	public BigDecimal getProcessSucAmount() {
		return processSucAmount;
	}

	/**
	 * @param 处理成功金额
	 */
	public void setProcessSucAmount(BigDecimal processSucAmount) {
		this.processSucAmount = processSucAmount;
	}

	/**
	 * @return 处理失败笔数
	 */
	public Integer getProcessFailNum() {
		return processFailNum;
	}

	/**
	 * @param 处理失败笔数
	 */
	public void setProcessFailNum(Integer processFailNum) {
		this.processFailNum = processFailNum;
	}

	/**
	 * @return 处理失败金额
	 */
	public BigDecimal getProcessFailAmount() {
		return processFailAmount;
	}

	/**
	 * @param 处理失败金额
	 */
	public void setProcessFailAmount(BigDecimal processFailAmount) {
		this.processFailAmount = processFailAmount;
	}

	/**
	 * @return 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return 受理时间
	 */
	public Date getAcceptDate() {
		return acceptDate;
	}

	/**
	 * @param 受理时间
	 */
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	/**
	 * @return 处理时间
	 */
	public Date getProcessDate() {
		return processDate;
	}

	/**
	 * @param 处理时间
	 */
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

}