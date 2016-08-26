package wusc.edu.pay.web.permission.exception;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @描述: 管理系统权限模块异常.
 * @作者: WuShuicheng.
 * @创建: 2014-8-13,下午5:01:40
 * @版本: V1.0
 * 
 */
public class PermissionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5371846727040729628L;
	/** 该用户没有分配菜单权限 */
	public static final Integer PERMISSION_USER_NOT_MENU = 1001;
	/** 根据角色查询菜单出现错误 **/
	public static final Integer PERMISSION_QUERY_MENU_BY_ROLE_ERROR = 1002;
	/** 分配菜单权限时，角色不能为空 **/
	public static final Integer PERMISSION_ASSIGN_MENU_ROLE_NULL = 1003;

	/**
	 * 错误代码
	 */
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public PermissionException(Integer code) {
		this.code = code;
	}

	public PermissionException(String msg) {
		super(msg);
	}

	/**
	 * 获取所有错误代码的常量集合
	 * 
	 * @return
	 */
	public static List<Field> getErrorCodeFields() {
		Class<PermissionException> exceptionClass = PermissionException.class;
		// 取得所有声明的字段
		Field[] fields = exceptionClass.getDeclaredFields();
		List<Field> errorCodeFields = new ArrayList<Field>();
		int fMod;
		for (Field f : fields) {
			fMod = f.getModifiers();
			if (Modifier.isFinal(fMod) && Modifier.isPublic(fMod) && Modifier.isStatic(fMod) && f.getType().equals(Integer.class)) {
				// 过滤所有的public static final的静态常量，并且类型为Integer的常量，视为ErrorCode
				errorCodeFields.add(f);
			}
		}
		return errorCodeFields;
	}
}
