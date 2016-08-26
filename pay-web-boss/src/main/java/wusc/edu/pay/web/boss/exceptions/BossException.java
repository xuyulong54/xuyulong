package wusc.edu.pay.web.boss.exceptions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
 *<ul>
 *<li>Title:POS运营异常基类 </li>
 *<li>Description: 
 *				 	 系统默认异常	-1
 * 						共用异常 		0001 - 1000
 * 					权限管理异常    1001 - 2000
 * 					基础数据异常		2001 - 3000
 * 					终端管理异常		3001 - 4000
 * 					系统模块异常		4001 - 5000
 * 					商户管理异常		5001 - 6000
 * 					交易管理异常		6001 - 7000
 * 					渠道管理异常		7001 - 8000
 * 					报表管理异常		8001 - 9000
 *</li>
 *<li>Copyright: www.gzzyzz.com</li>
 *<li>Company:</li>
 *</ul>
 *
 * @author Hill
 * @version 2013-12-16
 */
public class BossException extends Exception {

	private static final long serialVersionUID = -7970846035250972109L;
	
	public static final String SYSTEM_DEFAULT_EXCEPTION_MESSAGE = "系统异常";
	public static final Integer SYSTEM_DEFAULT_EXCEPTION = -1;//系统异常
	

	
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

	public BossException(Integer code) {
		this.code = code;
	}

	public BossException(String msg) {
		super(msg);
	}

	/**
	 * 获取所有错误代码的常量集合
	 * @return
	 */
	public static List<Field> getErrorCodeFields() {
		Class<BossException> exceptionClass = BossException.class;
		//取得所有声明的字段
		Field[] fields = exceptionClass.getDeclaredFields();
		List<Field> errorCodeFields = new ArrayList<Field>();
		int fMod;
		for ( Field f : fields ) {
			fMod = f.getModifiers();
			if ( Modifier.isFinal(fMod)
					&& Modifier.isPublic(fMod)
					&& Modifier.isStatic(fMod)
					&& f.getType().equals(Integer.class) ) {
				//过滤所有的public static final的静态常量，并且类型为Integer的常量，视为ErrorCode
				errorCodeFields.add(f);
			}
		}
		return errorCodeFields;
	}
}
