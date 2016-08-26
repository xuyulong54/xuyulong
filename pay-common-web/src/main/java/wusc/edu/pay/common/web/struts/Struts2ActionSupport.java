package wusc.edu.pay.common.web.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.themes.dwz.DwzParam;
import wusc.edu.pay.common.web.utils.UdpGetClientMacAddr;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 自定义Struts2支撑类.
 * 
 * @author healy
 * 
 * 
 * @修改版本:1.1 .
 * @修改人：WuShuicheng .
 * @修改时间: 2013-07-08 .
 * @修改内容: 添加结合DWZ-UI框架的Action层可共用方法 .
 * 
 * @修改版本:1.2 .
 * @修改人：WuShuicheng .
 * @修改时间: 2013-07-09 .
 * @修改内容: 添加结合DWZ-UI框架的分页参数获取方法 .
 * 
 */
public class Struts2ActionSupport extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(Struts2ActionSupport.class);

	private static ThreadLocal<Map<String, Object>> outPutMsg = new ThreadLocal<Map<String, Object>>();

	/**
	 * 编码类型 ISO-8859-1.
	 */
	// private static final String ISO8859_1 = "iso8859-1";
	/**
	 * 编码类型 UTF-8.
	 */
	private static final String UTF_8 = "utf-8";

	private static final String GBK = "GBK";

	protected JsonConfig getDefaultJsonConfig() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			public Object processObjectValue(String key, Object value, JsonConfig cfg) {
				if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
			}

			public Object processArrayValue(Object value, JsonConfig cfg) {
				if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
			}
		});
		return config;
	}

	/**
	 * 线程绑定，其内容会在outPrint方法调用后清空
	 * 
	 * @return the outputMsg
	 */
	public Map<String, Object> getOutputMsg() {
		Map<String, Object> output = outPutMsg.get();
		if (output == null) {
			output = new HashMap<String, Object>();
			outPutMsg.set(output);
		}
		return output;
	}

	protected void setOutputMsg(String key, String value) {
		getOutputMsg().put(key, value);
	}

	/**
	 * 输出，同时清空outPutMsg
	 * 
	 * @param response
	 * @param result
	 */
	public void outPrint(HttpServletResponse response, Object result) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.print(result.toString());
			getOutputMsg().clear();
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			out.close();
		}
	}

	/**
	 * 输出，直接输入结果数据
	 * 
	 * @param response
	 * @param result
	 */
	public void outWrite(HttpServletResponse response, Object result) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(result.toString());
			out.close();
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			out.close();
		}
	}

	public PageBean pageBean;

	public Integer pageNum;

	/**
	 * pageBean.
	 * 
	 * @return the pageBean
	 */
	public PageBean getPageBean() {
		return pageBean;
	}

	/**
	 * @param pageBean
	 *            the pageBean to set
	 */
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	/**
	 * 取得当前request
	 * 
	 * @return
	 */
	public HttpServletRequest getHttpRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得当前的HttpSession
	 * 
	 * @return HttpSession
	 */
	public HttpSession getHttpSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得会话ID(sessionId).
	 * 
	 * @return sessionId .
	 */
	public String getSessionId() {
		return ServletActionContext.getRequest().getSession().getId();
	}

	/**
	 * 取得当前response
	 * 
	 * @return
	 */
	public HttpServletResponse getHttpResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获取session里面的属性
	 * 
	 * @return
	 */
	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 获取request中的application对象.
	 */
	public Map<String, Object> getApplicationMap() {
		return ActionContext.getContext().getApplication();
	}

	/**
	 * 取得当前web应用的根路径
	 * 
	 * @return
	 */
	public String getWebRootPath() {
		return ServletActionContext.getServletContext().getRealPath("/");
	}

	/**
	 * 供AJAX返回使用(ajax的一种返回方式)
	 * 
	 * @param msg
	 */
	public void write(String msg) {
		PrintWriter write = null;
		try {
			ServletActionContext.getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
			write = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			LOG.error(e);
		}
		write.write(msg);
		write.close();
	}

	/**
	 * 添加cookie
	 * 
	 * @param path
	 * @param key
	 * @param value
	 * @param maxAge
	 */
	public void addCookie(String path, String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		if (path != null) {
			cookie.setPath(path);
		}
		cookie.setMaxAge(maxAge);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.addCookie(cookie);
	}

	/**
	 * 添加cookie
	 * 
	 * @param key
	 * @param value
	 */
	public void addCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.addCookie(cookie);
	}

	// ////////////////////////////////////////////////////////////////////////
	// /////////////// 添加结合DWZ-UI框架的Action层可共用方法 //////////////////////
	// /////////////// WuShuicheng 2013-07-08 /////////////////////////////////
	/**
	 * 响应DWZ-UI的Ajax成功请求（statusCode="200"）,<br/>
	 * 跳转到operateSuccess视图并提示“操作成功”.
	 * 
	 * @author WuShuicheng.
	 * @param message
	 *            提示消息.
	 * @return operateSuccess .
	 */
	public String operateSuccess() {
		ajaxDone("200", "操作成功");
		return "operateSuccess";
	}

	/**
	 * 响应DWZ的Ajax成功请求（statusCode="200"）,<br/>
	 * 跳转到operateSuccess视图，提示设置的消息内容.
	 * 
	 * @author WuShuicheng.
	 * @param message
	 *            提示消息.
	 * @return operateSuccess .
	 */
	public String operateSuccess(String message) {
		ajaxDone("200", message);
		return "operateSuccess";
	}

	/**
	 * 响应DWZ的ajax失败请求（statusCode="300"）,跳转到ajaxDone视图.
	 * 
	 * @author WuShuicheng.
	 * @param message
	 *            提示消息.
	 * @return ajaxDone .
	 */
	public String operateError(String message) {
		ajaxDone("300", message);
		return "operateError";
	}

	/**
	 * 给不是从页面提交跳转提供类似于下面的参数 <input type="hidden" name="navTabId" value="list">
	 * <input type="hidden" name="callbackType" value="closeCurrent"> <input
	 * type="hidden" name="forwardUrl" value="">
	 * 
	 * @param str1
	 * @param str2
	 */
	public void oprateparameter(String str1, String str2) {
		HttpServletRequest req = ServletActionContext.getRequest();
		req.setAttribute("navTabId", "list");
		req.setAttribute("callbackType", str1);
		req.setAttribute("forwardUrl", str2);
	}

	/**
	 * 响应DWZ的Ajax请求.
	 * 
	 * @author WuShuicheng.
	 * @param statusCode
	 *            statusCode:{ok:200, error:300, timeout:301}.
	 * @param message
	 *            提示消息.
	 */
	private void ajaxDone(String statusCode, String message) {
		DwzParam param = getDwzParam(statusCode, message);
		ActionContext.getContext().getValueStack().push(param);
	}

	/**
	 * 根据request对象，获取页面提交过来的DWZ框架的AjaxDone响应参数值.
	 * 
	 * @author WuShuicheng.
	 * @param statusCode
	 *            状态码.
	 * @param message
	 *            操作结果提示消息.
	 * @return DwzParam :封装好的DwzParam对象 .
	 */
	public DwzParam getDwzParam(String statusCode, String message) {
		// 获取DWZ Ajax响应参数值,并构造成参数对象
		HttpServletRequest req = ServletActionContext.getRequest();
		String navTabId = req.getParameter("navTabId");
		String dialogId = req.getParameter("dialogId");
		String callbackType = req.getParameter("callbackType");
		String forwardUrl = req.getParameter("forwardUrl");
		String rel = req.getParameter("rel");
		return new DwzParam(statusCode, message, navTabId, dialogId, callbackType, forwardUrl, rel, null);
	}

	// ///////////////////////////////////////////////////////////////
	// ///////////////// 结合DWZ-UI的分页参数获取方法 ///////////////////////////
	/**
	 * 获取当前页（DWZ-UI分页查询参数）.<br/>
	 * 如果没有值则默认返回1.
	 * 
	 * @author WuShuicheng.
	 */
	private int getPageNum() {
		// 当前页数
		String pageNumStr = getHttpRequest().getParameter("pageNum");
		int pageNum = 1;
		if (StringUtils.isNotBlank(pageNumStr)) {
			pageNum = Integer.valueOf(pageNumStr);
		}
		return pageNum;
	}

	/**
	 * 获取每页记录数（DWZ-UI分页查询参数）.<br/>
	 * 如果没有值则默认返回15.
	 * 
	 * @author WuShuicheng.
	 */
	private int getNumPerPage() {
		String numPerPageStr = getHttpRequest().getParameter("numPerPage");
		int numPerPage = 20;
		if (StringUtils.isNotBlank(numPerPageStr)) {
			numPerPage = Integer.parseInt(numPerPageStr);
		}
		return numPerPage;
	}

	/**
	 * 获取分页参数，包含当前页、每页记录数.
	 * 
	 * @return PageParam .
	 */
	public PageParam getPageParam() {
		return new PageParam(getPageNum(), getNumPerPage());
	}

	// //////////////////////// 存值方法 /////////////////////////////////
	/**
	 * 将数据放入Struts2上下文的值栈中.<br/>
	 * ActionContext.getContext().getValueStack().push(obj);
	 */
	public void pushData(Object obj) {
		ActionContext.getContext().getValueStack().push(obj);
	}

	/**
	 * 将数据放入Struts2上下文中.<br/>
	 * ActionContext.getContext().put(key, value);
	 */
	public void putData(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	// ///////////////////////getHttpRequest()方法扩展//////////////////////////
	/**
	 * 根据参数名从HttpRequest中获取Double类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return DoubleValue or null .
	 */
	public Double getDouble(String key) {
		String value = getHttpRequest().getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Double.parseDouble(value);
		}
		return null;
	}

	/**
	 * 根据参数名从HttpRequest中获取Integer类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return IntegerValue or null .
	 */
	public Integer getInteger(String key) {
		String value = getHttpRequest().getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Integer.parseInt(value);
		}
		return null;
	}

	/**
	 * 根据参数名从HttpRequest中获取Long类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return LongValue or null .
	 */
	public Long getLong(String key) {
		String value = getHttpRequest().getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			return Long.parseLong(value);
		}
		return null;
	}

	/**
	 * 根据参数名从HttpRequest中获取String类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return String or null .
	 */
	public String getString(String key) {
		return getHttpRequest().getParameter(key);
	}

	/**
	 * 根据参数名从HttpRequest中获取String类型的参数值，无值则返回"" .
	 * 
	 * @param key
	 *            .
	 * @return String .
	 */
	public String getString_UrlDecode_UTF8(String key) {
		try {
			return URLDecoder.decode(this.getString(key), UTF_8);
		} catch (Exception e) {
			return "";
		}

	}

	public String getString_UrlDecode_GBK(String key) {
		try {
			return new String(getString(key.toString()).getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 根据参数名从HttpRequest中获取String[] 类型的参数值 有 返回字符串数组 无 返回null;
	 * 
	 * @param key
	 *            .
	 * @return String[] or null .
	 */
	public String[] getStringArr(String key) {
		return getHttpRequest().getParameterValues(key);
	}

	/**
	 * 根据参数名从HttpRequest中获取Integer[] 类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return Integer[] or null .
	 */
	public Integer[] getIntegerArr(String key) {
		String[] values = getHttpRequest().getParameterValues(key);
		Integer[] returnArr = null;
		if (values != null) {
			int valueLength = values.length;
			returnArr = new Integer[valueLength];
			for (int i = 0; i < valueLength; i++) {
				returnArr[i] = Integer.parseInt(values[i]);
			}
		}
		return returnArr;
	}

	/**
	 * 根据参数名从HttpRequest中获取Long[] 类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return Long[] or null .
	 */
	public Long[] getLongArr(String key) {
		String[] values = getHttpRequest().getParameterValues(key);
		Long[] returnArr = null;
		if (values != null) {
			int valueLength = values.length;
			returnArr = new Long[valueLength];
			for (int i = 0; i < valueLength; i++) {
				returnArr[i] = Long.parseLong(values[i]);
			}
		}
		return returnArr;
	}

	/**
	 * 根据参数名从HttpRequest中获取Long[] 类型的参数值，无值则返回null .
	 * 
	 * @param key
	 *            .
	 * @return Long[] or null .
	 */
	public Double[] getDoubleArr(String key) {
		String[] values = getHttpRequest().getParameterValues(key);
		Double[] returnArr = null;
		if (values != null) {
			int valueLength = values.length;
			returnArr = new Double[valueLength];
			for (int i = 0; i < valueLength; i++) {
				returnArr[i] = Double.parseDouble(values[i]);
			}
		}
		return returnArr;
	}

	/**
	 * 与DWZ框架结合的表单属性长度校验方法.
	 * 
	 * @param propertyName
	 *            要校验的属性中文名称，如“登录名”.
	 * @param property
	 *            要校验的属性值，如“gzzyzz”.
	 * @param isRequire
	 *            是否必填:true or false.
	 * @param minLength
	 *            最少长度:大于或等于0，如果不限制则可请设为0.
	 * @param maxLength
	 *            最大长度:对应数据库字段的最大长度，如不限制则可设为0.
	 * @return 校验结果消息，校验通过则返回空字符串 .
	 */
	protected String lengthValidate(String propertyName, String property, boolean isRequire, int minLength, int maxLength) {
		int propertyLenght = StrUtil.strLengthCn(property);
		if (isRequire && propertyLenght == 0) {
			return propertyName + "不能为空，"; // 校验不能为空
		} else if (isRequire && minLength != 0 && propertyLenght < minLength) {
			return propertyName + "不能少于" + minLength + "个字符，"; // 必填情况下校验最少长度
		} else if (maxLength != 0 && propertyLenght > maxLength) {
			return propertyName + "不能多于" + maxLength + "个字符，"; // 校验最大长度
		} else {
			return ""; // 校验通过则返回空字符串 .
		}
	}

	/**
	 * 获取客户端的IP地址
	 * 
	 * @return
	 */
	public String getIpAddr() {
		String ipAddress = null;
		ipAddress = this.getHttpRequest().getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = this.getHttpRequest().getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = this.getHttpRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = this.getHttpRequest().getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取客户端MAC地址
	 * 
	 * @return
	 */
	public String getClientMacAddr() {
		try {
			return new UdpGetClientMacAddr(this.getIpAddr()).GetRemoteMacAddr();
		} catch (SocketTimeoutException e) {
			return "00:00:00:00";
		} catch (Exception e) {
			return "00:00:00:00";
		}

	}

	/**
	 * 获取请求中的参数值
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map map = getHttpRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			parameters.put(key.toString(), this.getString(key.toString()));
		}
		return parameters;
	}
	
	/**
	 * 获取请求中的参数值，如果参数值为null刚转为空字符串""
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap_NullStr() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map map = getHttpRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			String value = this.getString(key.toString());
			if (value == null){
				value = "";
			}
			parameters.put(key.toString(), value);
		}
		return parameters;
	}

	/**
	 * 获取请求中的参数值
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getParamMap_Utf8() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map map = getHttpRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			parameters.put(key.toString(), this.getString_UrlDecode_UTF8(key.toString()));
		}
		return parameters;
	}

	/**
	 * 获取请求中的参数值
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getParamMap_GBK() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map map = getHttpRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			parameters.put(key.toString(), this.getString_UrlDecode_GBK(key.toString()));
		}
		return parameters;
	}

	/**
	 * 获取refererUrl
	 */
	public String getRefererUrl() {
		return getHttpRequest().getHeader("referer");
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.isNotNull(" "));
	}

	/**
	 * 获取字符串的编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
}
