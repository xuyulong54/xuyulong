package wusc.edu.pay.web.boss.action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import wusc.edu.pay.common.config.PublicConfig;


/**
 * className：ContextLoaderInit  <br>
 * Function：  Struts2 启动时加载方法  <br>
 * date: 2014-10-16-下午3:56:38  <br>
 * 
 * @author laich
 */
public class ContextLoaderInit extends HttpServlet {

	private static final long serialVersionUID = 7017571374655597934L;

	// 启动加载,初始化平台系统参数
	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		// 设置全局参数
		context.setAttribute("IS_SSL", PublicConfig.IS_SSL);
		// 门户URL地址
		context.setAttribute("PROTAL_URL", PublicConfig.PROTAL_URL);
		// 公司信息
		context.setAttribute("COMPANY_NAME", PublicConfig.COMPANY_NAME);
		context.setAttribute("COMPANY_FOR", PublicConfig.COMPANY_FOR);
		context.setAttribute("COMPANY_LOGO", PublicConfig.COMPANY_LOGO);
		context.setAttribute("COMPANY_TEL", PublicConfig.COMPANY_TEL);
		context.setAttribute("COMPANY_ADDRESS", PublicConfig.COMPANY_ADDRESS);
		context.setAttribute("COMPANY_EMAIL", PublicConfig.COMPANY_EMAIL);
		context.setAttribute("COMPANY_NET_ICP", PublicConfig.COMPANY_NET_ICP);
		context.setAttribute("COMPANY_HR_EMAIL", PublicConfig.COMPANY_HR_EMAIL);
		//是否 是 域名 + 应用名 
		context.setAttribute("IS_USE_DOMAIN_NAME", PublicConfig.IS_USE_DOMAIN_NAME);
		context.setAttribute("FILE_SYS_URL", PublicConfig.FILE_SYS_URL);
	}

}
