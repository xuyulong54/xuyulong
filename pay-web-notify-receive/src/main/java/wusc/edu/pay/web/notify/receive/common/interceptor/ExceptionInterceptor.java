package wusc.edu.pay.web.notify.receive.common.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import wusc.edu.pay.common.exceptions.BizException;

import com.alibaba.dubbo.rpc.RpcException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * ClassName: ExceptionInterceptor <br/>
 * Function: 银行异常拦截器 <br/>
 * date: 2014-8-1 下午5:06:19 <br/>
 * 
 * @author laich
 */
public class ExceptionInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ExceptionInterceptor.class);

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (BizException e) { // 调用服务业务异常
			ActionContext context = invocation.getInvocationContext();
			log.error(e.toString());
			context.put("errCode", e.getCode());
			context.put("errMsg", e.getMsg());

			HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("errCode:" + e.getCode() + " errMsg:" + e.getMsg());
			return null;
		} catch (RpcException e) { // 调用服务网络异常
			log.error("RpcException:", e);
			ActionContext context = invocation.getInvocationContext();
			context.put("errCode", e.getCode());
			context.put("errMsg", e.toString());

			HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("errCode:" + e.getCode() + " errMsg:" + e.toString());
			return null;
		} catch (Exception e) {
			log.error("interceptor exception:", e);
			ActionContext context = invocation.getInvocationContext();
			context.put("errMsg", e.toString());

			HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("errMsg:" + e.toString());
			return null;
		}
	}
}
