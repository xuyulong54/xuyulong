package wusc.edu.pay.web.portal.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;

import com.alibaba.dubbo.rpc.RpcException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 业务异常拦截器
 * 
 * @author healy
 * 
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
			log.error(e.toString());
			ActionContext context = invocation.getInvocationContext();
			context.put("errCode", e.getCode());
			context.put("errMsg", e.getMsg());
			return "bizException";
		} catch (RpcException e) { // 调用服务网络异常
			log.error(e.toString());
			ActionContext context = invocation.getInvocationContext();
			context.put("errCode", e.getCode());
			context.put("errMsg", e.toString());
			return "bizException";
		} catch (Exception e) {
			ByteArrayOutputStream ostr = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(ostr));
			log.error(ostr.toString());
			ActionContext context = invocation.getInvocationContext();
			context.put("errCode", ostr.toString());
			return "exception";
		}
	}
}
