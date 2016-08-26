/**
 * wusc.edu.pay.facade.limit.params.SystemBackupPayInterface.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.limit.enums.SystemBackupPayInterfaceStatusEnum;


/**
 * 
 * 
 * <ul>
 * <li>Title: 系统备份接口规则</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-8
 */
public class SystemBackupPayInterface extends BaseEntity {

	private static final long serialVersionUID = -4733525372392349156L;

	/**
	 * 原支付接口
	 */
	private String oldPayInterface;

	/**
	 * 备份支付接口
	 */
	private String newPayInterface;

	/**
	 * 系统备份接口规则状态
	 */
	private SystemBackupPayInterfaceStatusEnum status;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyDate;

	/**
	 * 本条系统备份规则是否生效
	 * 
	 * @return
	 */
	public boolean isStatusAvailable() {
		if (SystemBackupPayInterfaceStatusEnum.ACTIVITY.equals(this.status)) {
			return true;
		}
		return false;
	}

	public String getOldPayInterface() {
		return oldPayInterface;
	}

	public void setOldPayInterface(String oldPayInterface) {
		this.oldPayInterface = oldPayInterface;
	}

	public String getNewPayInterface() {
		return newPayInterface;
	}

	public void setNewPayInterface(String newPayInterface) {
		this.newPayInterface = newPayInterface;
	}

	public SystemBackupPayInterfaceStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SystemBackupPayInterfaceStatusEnum status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

}
