package wusc.edu.pay.web.boss.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.alibaba.dubbo.rpc.RpcException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 异常拦截器
 * 
 * @author healy
 * 
 */
public class ExceptionInterceptor extends BossBaseAction implements Interceptor {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ExceptionInterceptor.class);

	/***
	 * 拦截器销毁方法
	 */
	public void destroy() {

	}

	/***
	 * 拦截器初始化方法
	 */
	public void init() {

	}

	/***
	 * 异常拦截器
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (BizException e) {
			log.error(e.toString());
			return operateError(e.getMessage()+"，异常编码："+e.getCode());
		} catch (RpcException e) {
			log.error(e.toString());
			return operateError("服务异常，异常代码：" + e.getCode() + "，如有疑问，请联系管理员！");
		} catch (Exception e) {
			ByteArrayOutputStream ostr = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(ostr));
			log.error(ostr.toString());
			return operateError("系统异常，请联系管理员！");
		}
	}
}
