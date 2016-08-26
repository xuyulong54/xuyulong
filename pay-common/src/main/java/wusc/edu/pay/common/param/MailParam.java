package wusc.edu.pay.common.param;

/**
 * 
 * @描述: 邮件参数封装类.
 * @作者: LiLiqiong,WuShuicheng.
 * @创建: 2014-6-4,下午4:03:48
 * @版本: V1.0
 *
 */
public class MailParam {

	/** 发件人 **/
	private String from;
	/** 收件人 **/
	private String to;
	/** 主题 **/
	private String subject;
	/** 邮件内容 **/
	private String content;

	public MailParam() {
	}

	public MailParam(String to, String subject, String content) {
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
