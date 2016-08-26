package wusc.edu.pay.webservice.merchant.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.util.ByteArrayBuffer;

import wusc.edu.pay.common.constant.CacheConstant;
import wusc.edu.pay.common.utils.cache.redis.RedisUtils;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;


/**
 * 
 * */
public class FastPayBaseAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -4491721068265200279L;
	private static final Log LOG = LogFactory.getLog(FastPayBaseAction.class);

	/**
	 * 获取请求request中的输入流
	 * */
	public String getRequestInputStreamToString() {
		String param = null;
		try {
			HttpServletRequest res = getHttpRequest();
			// System.out.println(res.getSession().getId());
			// System.out.println("cookie====" + res.getHeader("cookie"));
			ServletInputStream in = res.getInputStream();
			param = readStringFromInputStream(in);
			LOG.info("request data 【" + param + "】");
		} catch (IOException e) {
			LOG.error("get fast pay inputsteam exception :", e);
		}
		return param;
	}

	/***
	 * 获取cookie的方法
	 * 
	 * @param content
	 * @return
	 */
	public String getCookieSub(String content) {
		int num = content.indexOf("=");
		String returnContent = "";
		if (num >= 0) {
			returnContent = content.substring(num + 1, content.length());
			return returnContent;
		}
		return content;
	}

	/**
	 * 将输入流转换成字符串
	 * */
	// private String inputStream2String (InputStream in) throws IOException {
	// StringBuffer out = new StringBuffer();
	// byte[] b = new byte[4096];
	// for (int n; (n = in.read(b)) != -1;) {
	// out.append(new String(b, 0, n));
	// }
	// return out.toString();
	// }

	private String readStringFromInputStream(InputStream is) throws IOException {
		byte[] buf = new byte[4096];
		int len = 0;
		ByteArrayBuffer bytes = new ByteArrayBuffer(4096);

		while (true) {
			len = is.read(buf);
			if (len >= 0) {
				bytes.append(buf, 0, len);
			} else {
				break;
			}
		}
		return new String(bytes.toByteArray(), "UTF-8");
	}

	/***
	 * 将session放入缓存
	 * 
	 * @param sessionId
	 * @param session
	 * @return
	 */
	public void saveRedisSession(String sessionId, HttpSession session) {
		StringBuffer buffer = new StringBuffer(CacheConstant.FAST_PAY_SESSION).append(sessionId);
		RedisUtils.save(buffer.toString(), session, 15 * 60);
	}

	/***
	 * 将session从缓存中取出
	 * 
	 * @return
	 */
	public HttpSession getRedisSession() {
		String sessionId = getHttpRequest().getHeader("cookie");
		if ("".equals(sessionId) || sessionId == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(CacheConstant.FAST_PAY_SESSION).append(sessionId);
		Object obj = RedisUtils.get(buffer.toString());
		if (obj == null) {
			return null;
		}
		return (HttpSession) obj;
	}

}
