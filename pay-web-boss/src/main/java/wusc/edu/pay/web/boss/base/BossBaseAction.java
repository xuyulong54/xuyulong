package wusc.edu.pay.web.boss.base;

import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserCheckRealNameStatusEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.web.permission.base.PermissionBaseAction;


/**
 * 
 * @描述: Boss运营管理系统基础支撑Action .
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-31,下午9:49:29 .
 * @版本: 1.0 .
 */
@SuppressWarnings("serial")
public class BossBaseAction extends PermissionBaseAction {

	public static final Integer exportMaxNumber = 500000000;
	
	public void putEnums() {
		putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		putData("UserStatusEnum", UserStatusEnum.toMap());
		putData("UserCheckRealNameStatusEnum", UserCheckRealNameStatusEnum.toMap());
	}

}
