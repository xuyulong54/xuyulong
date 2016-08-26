package wusc.edu.pay.facade.boss.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * @描述: 合同管理.
 * @作者: Lanzy.
 * @创建时间: 2014-4-9, 上午10:35:20 .
 * @版本: V1.0.
 * 
 */
public class ContractManagement extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2388736278506878498L;

	private String userNo; // 商户ID或者银行序号
	private String userName; // 商户名称或者银行接口名称
	private String contractNo; // 合同编号
	private String contractFile;// 合同保存路径
	private Integer contractType;// 合同类型
	private String creater; // 创建人
	private Date modifyTime; // 最后修改时间
	private String fileName; // 源文件名
	private Integer fileProperties;// 文件性质(1、首次签约，2、续约，3、变更资料，4、其它)
	private String remark; // 描述
	private Date signTime; // 签约日期
	private Date contractValid; // 合同到期日期

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getFileProperties() {
		return fileProperties;
	}

	public void setFileProperties(Integer fileProperties) {
		this.fileProperties = fileProperties;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractFile() {
		return contractFile;
	}

	public void setContractFile(String contractFile) {
		this.contractFile = contractFile;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getContractValid() {
		return contractValid;
	}

	public void setContractValid(Date contractValid) {
		this.contractValid = contractValid;
	}

}
