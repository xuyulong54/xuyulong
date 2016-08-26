/**
 *  wusc.edu.pay.facade.limit.enums.SystemBackupPayInterfaceStatusEnum.java
 */
package wusc.edu.pay.facade.limit.enums;

/**
 * 
 * 
 * <ul>
 * <li>Title: 系统备份接口规则状态</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-8
 */
public enum SystemBackupPayInterfaceStatusEnum {
	/*
	 * 申明：如果需要添加状态，需要连带的修改SystemBackupPayInterface.isStatusAvailable()
	 */

	/** 正常状态 */
	ACTIVITY,

	/** 非活跃状态 */
	INACTIVITY,

	/** 已删除状态 */
	DELETED;

}
