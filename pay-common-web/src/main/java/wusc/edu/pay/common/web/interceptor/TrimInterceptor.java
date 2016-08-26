package wusc.edu.pay.common.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @描述: 请求参数前合空格过滤器.
 * @作者: WuShuicheng.
 * @创建时间: 2014-1-1,下午9:01:12.
 * @版本号: V1.0 .
 *
 */
public class TrimInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	//private static final Logger LOG = Logger.getLogger(TrimInterceptor.class);

	/**
	 * <p>
	 * 方法描述: [trime掉空格]
	 * </p>
	 * 
	 * @param invocation
	 *            参数说明
	 * 
	 * @return 返回结果的说明
	 * 
	 * @throws Exception
	 *             抛出异常的原因
	 */
	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation invocation) throws Exception {
		//LOG.info("过滤请求数据的首尾空格");
		Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		Set entrySet = parameters.entrySet();
		String[] strings = null;
		String[] values = null;
		int strLength = 0;
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof String[]) {
				values = (String[]) value;// 类型转换
				strLength = values.length;
				strings = new String[strLength];
				for (int i = 0; i < strLength; i++) {
					strings[i] = values[i].trim();
				}

				parameters.put((String) key, strings);
			}
		}

		invocation.getInvocationContext().setParameters(parameters);
		invocation.invoke();
		return null;
	}
}
