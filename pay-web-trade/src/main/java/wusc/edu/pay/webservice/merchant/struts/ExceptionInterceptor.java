package wusc.edu.pay.webservice.merchant.struts;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
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
		} catch (Exception e) {

			ByteArrayOutputStream ostr = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(ostr));
			log.error(ostr.toString());

			ActionSupport actionSupport = (ActionSupport) invocation.getAction();
			actionSupport.addActionError(e.toString());
			return "exception";
		}

	}

}
