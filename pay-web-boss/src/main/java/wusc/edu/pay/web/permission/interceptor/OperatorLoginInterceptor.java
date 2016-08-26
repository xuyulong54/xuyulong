package wusc.edu.pay.web.permission.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.web.permission.base.OperatorLoginedAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * @描述: 操作员登录及操作权限拦截器.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-11,下午1:16:02 .
 * @版本: 1.0 .
 */
public class OperatorLoginInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(OperatorLoginInterceptor.class);

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();
		invocation.getClass();
		if (action instanceof OperatorLoginedAware) {
			// 判断用户是否已登录
			OperatorLoginedAware loginedAction = (OperatorLoginedAware) action;

			if (loginedAction.getLoginedOperator() == null) {
				// 用户未登录
				setToGoUrl(invocation.getInvocationContext());
				log.info("action[" + invocation.getProxy().getActionName() + "], Operator no login!");
				return "operatorLogin";
			} else {
				// 已登录
				return invocation.invoke();
			}
		}
		return invocation.invoke();
	}

	/**
	 * 设置登录跳转url
	 * 
	 * @param context
	 */
	private void setToGoUrl(ActionContext context) {
		String action = context.getName() + ".action";
		String para = "?";
		for (Object o : context.getParameters().keySet()) {
			try {
				String[] value = (String[]) context.getParameters().get(o);
				if (value.length > 0) {
					para += o.toString() + "=" + value[0] + "&";
				}
			} catch (Exception e) {
				try {
					para += o.toString() + "=" + context.getParameters().get(o) + "&";
				} catch (Exception e1) {

				}
			}
		}
		context.getSession().put("returnUrlPmsOperator", action + para);
		log.info("跳转URL：" + context.getSession().get("returnUrlPmsOperator"));

	}

}
