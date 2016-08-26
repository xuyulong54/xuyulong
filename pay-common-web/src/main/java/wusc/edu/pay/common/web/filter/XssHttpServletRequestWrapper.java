package wusc.edu.pay.common.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 过滤输入内容中的的特殊符号，防御跨站脚本攻击.<br/>
 * 对每个post请求的参数过滤一些关键字，替换成安全的，例如：< > ' " \ / # &
 * 方法是实现一个自定义的HttpServletRequestWrapper，然后在Filter里面调用它，替换掉getParameter函数.<br/>
 * 参考：http://www.cnblogs.com/Mainz/archive/2012/11/01/2749874.html
 * 
 * @author WuShuicheng(吴水成).
 * @version 1.0, 2013-4-11,上午3:34:09.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	// private static final Log log = LogFactory.getLog(XssHttpServletRequestWrapper.class);

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}
	
	
	public String[] getParameterValues(String parameter) {
		// log.info("=== getParameterValues");
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		cleanXSS(values); // 传引用
		return values;
	}

	/**
	 * 对单一参数值进行过滤.
	 */
	public String getParameter(String parameter) {
		// log.info("=== getParameter");
		String value = super.getParameter(parameter);
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		// log.info("=== getHeader");
		String value = super.getHeader(name);
		return cleanXSS(value);
	}

	/**
	 * 对字符串数组进行过滤
	 * 
	 * @param values
	 */
	private void cleanXSS(String[] values) {
		if (values != null) {
			for (int num = 0; num < values.length; num++) {
				values[num] = this.cleanXSS(values[num]);
			}
		}
	}

	/**
	 * HTML过滤危险内容.
	 * 
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		// log.info("=== beforce cleanXSS: " + value);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		//不要对<,>,',"进行编码，避免长文本显示出问题
		// You'll need to remove the spaces from the html entities below
		/**
		 * 使用一个 Whitelist 类用来对 HTML 文档进行过滤.<br/>
		 * 参考：http://www.oschina.net/question/12_14127 .
		 */
	    return Jsoup.clean(value, Whitelist.relaxed()); // 最宽松的一个过滤方法
		// log.info("=== after cleanXSS: " + value);
		//return value;
	}

}