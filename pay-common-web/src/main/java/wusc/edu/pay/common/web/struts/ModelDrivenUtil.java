package wusc.edu.pay.common.web.struts;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * ModelDriven 方式获取的表单对象的参数过滤工具类.
 * 
 * @author WuShuicheng .
 * @CreateTime 2014-02-24.
 * 
 */
public class ModelDrivenUtil {

	private static Logger logger = Logger.getLogger(ModelDrivenUtil.class);

	/**
	 * 传入对象，过滤对象属性值中的危险特殊字符并反回.
	 * 
	 * @param model
	 * @return safeModel .
	 */
	public static Object cleanModel(Object model) {
		Object safeModel = model;
		// /// 过滤model中的值 Bebin /////
		Class<?> clazz = safeModel.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			Field field = null;
			Object fileValue = null;
			for (int num = 0; num < fields.length; num++) {
				field = fields[num];
				field.setAccessible(true); // 抑制Java对其的检查
				if ("java.lang.String".equals(field.getType().getName())) {
					try {
						fileValue = field.get(safeModel);
						// 过滤掉String类型的属性值

						// 当为NULL时，不将NULL传回
						if (fileValue == null) {
							fileValue = "";
						}
						field.set(safeModel, Jsoup.clean(String.valueOf(fileValue), Whitelist.relaxed()));
					} catch (IllegalArgumentException e) {
						logger.error(e);
					} catch (IllegalAccessException e) {
						logger.error(e);
					} // 将 model 中 field 所代表的值 设置为 fileValue
				}
			}
		}
		// /// 过滤model中的值 End /////
		return safeModel;
	}
}
