package wusc.edu.pay.common.web.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import wusc.edu.pay.common.web.constant.PermissionConstant;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @描述: 自定义权限权标签 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-17,下午4:04:24 .
 * @版本: 1.0 .
 */
@SuppressWarnings("serial")
public class PermissionTaglib extends BodyTagSupport {
	
	//private static final Log log = LogFactory.getLog(PermissionTaglib.class);

	private String value; //权限值

	@Override
	public int doStartTag() throws JspException {
		//log.info("permission tag value=" + value);
		if (StringUtils.isNotBlank(value)){
			@SuppressWarnings("unchecked")
			final List<String> permissions = (List<String>) ActionContext.getContext().getSession().get(PermissionConstant.ACTIONS_SESSION_KEY);
			if (permissions.contains(value.trim())){ // 拥有此功能点权限
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	} 
}
