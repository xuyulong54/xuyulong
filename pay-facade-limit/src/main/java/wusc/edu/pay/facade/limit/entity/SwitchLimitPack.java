package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 开关限制实体类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午5:33:28 .
 * @版本: V1.0 .
 */
public class SwitchLimitPack extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8118030148214788687L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;

	/**
	 * 修改时间
	 */
	private Date lastModifyTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
