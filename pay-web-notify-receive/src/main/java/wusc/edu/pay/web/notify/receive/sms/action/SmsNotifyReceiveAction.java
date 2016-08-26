package wusc.edu.pay.web.notify.receive.sms.action;

import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.param.SmsParam;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 短息发送通知接收.
 * @作者: WuShuicheng.
 * @创建: 2014-11-4,下午4:35:20
 * @版本: V1.0
 * 
 */
public class SmsNotifyReceiveAction extends Struts2ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307213986406912756L;

	private static Log LOG = LogFactory.getLog(SmsNotifyReceiveAction.class);

	/**
	 * 接收通知并处理
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String notifyReceive() {

		String msgBase64 = this.getString_UrlDecode_UTF8("msg");
		if (StringUtils.isBlank(msgBase64)) {
			throw new RuntimeException("通知请求参数[msg]为空");
		}

		String json = new String(Base64.decodeBase64(msgBase64));
		LOG.info("notifyReceive:" + json);

		SmsParam param = JSONObject.parseObject(json, SmsParam.class);
		// 发送短信
		LOG.info(param.getPhone()+ ":" + param.getContent());

		// 回写成功字符串
		this.write(SUCCESS);
		return null;
	}

}
