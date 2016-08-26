package wusc.edu.pay.facade.remit.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * @Title: 银行行别信息实体
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:09:43
 */
public class RemitBankType extends BaseEntity{
	private static final long serialVersionUID = 1L;


	private String typeCode; //银行行别代码

    private String typeName; //银行行别名称

    private Integer category; //类别:1-中央银行，2-国有独资商业银行，3-政策性银行，4-其他商业银行，5-非银行金融机构，6-外资在华银行或代表处，7-特区参与者

    private String bankCode; //银行编码
    
    private Integer status; //状态:100-有效，101-无效
    
    /**
     * 银行编码
     * @return
     */
	public String getBankCode() {
		return bankCode;
	}
	
	/**
     * 银行编码
     * @return
     */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return 银行行别代码
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param 银行行别代码
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return 银行行别名称
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param 银行行别名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return 类别:1-中央银行，2-国有独资商业银行，3-政策性银行，4-其他商业银行，5-非银行金融机构，6-外资在华银行或代表处，7-特区参与者
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param 类别:1-中央银行，2-国有独资商业银行，3-政策性银行，4-其他商业银行，5-非银行金融机构，6-外资在华银行或代表处，7-特区参与者
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return 状态:100-有效，101-无效
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态:100-有效，101-无效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

    
}