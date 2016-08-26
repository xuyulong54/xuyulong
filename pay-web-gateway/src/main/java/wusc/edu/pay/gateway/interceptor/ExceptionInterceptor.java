package wusc.edu.pay.gateway.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.trade.exception.TradeBizException;

import com.alibaba.dubbo.rpc.RpcException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 异常拦截器
 * 
 * @author healy
 * 
 */
public class ExceptionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ExceptionInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (BizException e) { // 调用服务业务异常
			ActionContext context = invocation.getInvocationContext();
			log.error(e.toString());
			context.put("errCode", e.getCode());
			context.put("errMsg", e.getMsg());
			if (e.getCode() == TradeBizException.PAYMENT_ORDER_IS_CANCELED.getCode()) {
				return "error_message";
			} else if (e.getCode() == TradeBizException.PAYMENT_ORDER_IS_COMPLETED.getCode()) {
				return "error_message";
			} else if (e.getCode() == BizException.SESSION_IS_OUT_TIME.getCode()){
				return "sessionOutTime";
			}
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
			context.put("errMsg", e.toString());
			return "exception";
		}
	}

}
