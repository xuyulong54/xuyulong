package wusc.edu.pay.web.notify.receive.mail.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.param.MailParam;


/**
 * 
 * @描述: 邮件发送业务逻辑类.
 * @作者: LiLiqiong,WuShuicheng.
 * @创建: 2014-6-4,下午3:34:41
 * @版本: V1.0
 * 
 */
@Component("mailBiz")
public class MailBiz {

	@Autowired
	private JavaMailSender mailSender;// spring配置中定义
	@Autowired
	private SimpleMailMessage simpleMailMessage;// spring配置中定义
	@Autowired
	private ThreadPoolTaskExecutor threadPool;

	/**
	 * 发送模板邮件
	 * 
	 * @param mailParamTemp需要设置四个参数
	 *            templateName,toMail,subject,mapModel
	 * @throws Exception
	 * 
	 */
	public void mailSend(final MailParam mailParam) {
		threadPool.execute(new Runnable() {
			public void run() {
				simpleMailMessage.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
				simpleMailMessage.setTo(mailParam.getTo()); // 接收人
				simpleMailMessage.setSubject(mailParam.getSubject());
				simpleMailMessage.setText(mailParam.getContent());
				mailSender.send(simpleMailMessage);
			}
		});
	}
}
