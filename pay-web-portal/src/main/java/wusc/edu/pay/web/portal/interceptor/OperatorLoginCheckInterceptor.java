package wusc.edu.pay.web.portal.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import wusc.edu.pay.web.portal.action.LoginAction;
import wusc.edu.pay.web.portal.base.BaseConsts;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 
 * @描述: 操作员登录及操作权限拦截器.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-11,下午1:16:02 .
 * @版本: 1.0 .
 */
public class OperatorLoginCheckInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OperatorLoginCheckInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		log.info("==>OperatorLoginCheckInterceptor doIntercept");
		Action action = (Action) invocation.getAction();
		// 判断是否请求为登录界面(login),如果是则不拦截
		if (LoginAction.class == action.getClass()) {
			log.info("==>OperatorLoginCheckInterceptor LoginAction");
			return invocation.invoke();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		// 如果是请求其他页面，进行拦截
		if (null == request.getSession().getAttribute(BaseConsts.CURRENT_USER)) {
			log.info("==>OperatorLoginCheckInterceptor no session");
			String url = request.getRequestURI();// 域名映射中去掉了项目名称
			String redirectUrl = request.getQueryString() != null ? url + "?" + request.getQueryString() : url;
			// String redirectUrl = request.getQueryString() != null ?
			// url.substring(url.lastIndexOf("/")) + "?" +
			// request.getQueryString() : url.substring(url.lastIndexOf("/"));
			request.getSession().setAttribute(BaseConsts.MERCHANT_REDIRECT_URL, redirectUrl);
			request.setAttribute("loginMsg", "会话超时或未登录。");
			return "login";
		}

		return invocation.invoke();

	}
}
