package wusc.edu.pay.webservice.merchant.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.web.context.ThreadLocalContext;


/**
 * 自定义过滤器.<br/>
 * 1、对静态资源不处理. <br/>
 * 2、要求管理员一定要先登录才能进入后台（对request进行安全过滤），否则跳转到登录页面. <br/>
 * 3、判断系统是否已安装，未安装则跳转到安装页. <br/>
 * 
 * 对Request里的传值参数进行安全过滤，防御XSS攻击.
 * @author WuShuicheng.
 * @version 1.0, 2013-3-31,下午3:15:06.
 */
public class DispatcherFilter implements Filter {
	private static final Log log = LogFactory.getLog(DispatcherFilter.class);
			
	public void destroy() {
		log.info("=== CustomFilter destroy");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String uri = request.getServletPath(); // 请求路径
		//log.info("=== uri=" + uri);
		
		// 静态资源不处理
		if (uri.startsWith("/statics")){
			chain.doFilter(request, response);
			return;
		}
		
		// 为自定义的上下文工具类设值
		ThreadLocalContext.setHttpRequest(request);
		ThreadLocalContext.setHttpResponse(response);
		// 注意：本项目中的过滤器不能对request中的参数进行过滤，因为接口请求报文中可能会包含特殊字符，过滤后会导致密文无法解密
		// 其他情况
		chain.doFilter(request, response);
		return;
	}

	public void init(FilterConfig arg) throws ServletException {
		log.info("=== CustomFilter init");
	}
}
