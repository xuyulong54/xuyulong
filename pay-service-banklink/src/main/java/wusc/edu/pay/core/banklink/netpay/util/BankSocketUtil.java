package wusc.edu.pay.core.banklink.netpay.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

/**
 * Scoket通信工具类
 * @author Administrator
 *
 */
public class BankSocketUtil {

	private static final Log logger = LogFactory.getLog(BankFtpUtil.class);

	private final static int DEFAULT_CONNECT_TIMEOUT = 60000;
	
	private final static int DEFAULT_READ_TIMEOUT = 60000 * 3;

	/**
	 * 发送SOCKET请求（默认超时时间）
	 */
	public static Document sendRequest(String IP, int port,
			String requestContent) {
		return BankSocketUtil.sendRequest(IP, port, requestContent,
				DEFAULT_READ_TIMEOUT, false);
	}

	/**
	 * 发送SOCKET请求（默认超时时间）
	 */
	public static Document sendRequest(String IP, int port,
			String requestContent, int timeout, boolean synchFlag) {
		if (synchFlag) {
			BankSocketUtil.sendRequestSynch(IP, port, requestContent, timeout);
			return null;
		} else {
			return BankSocketUtil.sendRequest(IP, port, requestContent,
					DEFAULT_READ_TIMEOUT);
		}
	}

	/**
	 * 发送SOCKET请求
	 */
	public static void sendRequestSynch(final String IP, final int port,
			final String requestContent, final int timeout) {
		logger.info("异步执行SOCKET请求[ip= " + IP + ", thread = "
				+ Thread.currentThread().getName() + "]");
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("异步任务开始[ip= " + IP + ", thread = "
				+ Thread.currentThread().getName() + "]");
				BankSocketUtil.sendRequest(IP, port, requestContent, timeout);
			}
		}).start();
	}

	public static Document sendRequest(String IP, int port,
			String requestContent, int timeout) {
		logger.info("执行socket请求[ip = " + IP + ", requestContent = "
				+ requestContent + ", timeout = " + timeout + "]");
		Document requestResultDoc = null;
		OutputStream requestStream = null;
		InputStream requestResultStream = null;
		Socket socket = null;
		try {
			// 加入银行超时策略
			socket = new Socket();
			socket.connect(new InetSocketAddress(IP, port), DEFAULT_CONNECT_TIMEOUT);
			socket.setSoTimeout(timeout);
			requestStream = socket.getOutputStream();
			requestStream.write(requestContent.getBytes());// 发送数据
			requestStream.flush();
			requestResultStream = socket.getInputStream();
			if (requestResultStream == null) {
				throw new RuntimeException("SOCKET通信获得结果异常");
			}
			SAXBuilder builder = new SAXBuilder();
			requestResultDoc = builder.build(requestResultStream);
		} catch (SocketTimeoutException e) {
			logger.error("SOCKET通信超时异常");
			throw new RuntimeException("SOCKET通信超时异常");
		} catch (ConnectException e) {
			logger.error("SOCKET通信连接异常");
			throw new RuntimeException("SOCKET通信连接异常");
		} catch (IOException e) {
			logger.error("SOCKET通信异常");
			throw new RuntimeException("SOCKET通信异常");
		} catch (Exception e) {
			logger.error("SOCKET通信未知异常");
			throw new RuntimeException("SOCKET通信未知异常");
		} finally {
			try {
				if (requestResultStream != null) {
					requestResultStream.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				logger.error("SOCKET通信时关闭通信流异常");
			}
		}
		return requestResultDoc;
	}

}
