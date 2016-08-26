package wusc.edu.pay.web.notify.receive.mail.action;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.param.MailParam;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.web.notify.receive.mail.biz.MailBiz;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 邮件发通队列通知接收.
 * @作者: WuShuicheng.
 * @创建: 2014-11-4,下午4:35:20
 * @版本: V1.0
 *
 */
public class MailNotifyReceiveAction extends Struts2ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307213986406912756L;
	private static Log LOG = LogFactory.getLog(MailNotifyReceiveAction.class);
	@Autowired
	private MailBiz mailBiz;

	/**
	 * 接收通知并处理
	 * @return
	 */
	public String notifyReceive() {
		
		String msgBase64 = this.getString_UrlDecode_UTF8("msg");
		if (StringUtils.isBlank(msgBase64)){
			throw new RuntimeException("通知请求参数[msg]为空");
		}
		
		String json = new String(Base64.decodeBase64(msgBase64));
		LOG.info("notifyReceive:" + json);
		
		
		MailParam mailParam = JSONObject.parseObject(json, MailParam.class);
		if (mailParam == null) {
			throw new RuntimeException("通知请求参数[msg]转换的对象为空");
		}
		mailBiz.mailSend(mailParam);
		
			
		// 回写成功字符串
		this.write(SUCCESS);
		return null;
	}

}
