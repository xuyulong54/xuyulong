package wusc.edu.pay.core.banklink.netpay.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;

/**
 * FTP工具类
 * @author Administrator
 *
 */
public class BankFtpUtil {

	private static final Log logger = LogFactory.getLog(BankFtpUtil.class);

	public static byte[] ftpDownload(String ftpKey, String ftpFilePath) {
		String ftpConf = BankConfUtil.get(ftpKey);
		if (ftpConf == null || ftpConf.equals("")) {
			throw new RuntimeException("获取FTP配置失败，请检查是否正确配置[ftpKey = "
					+ ftpKey + "]");
		}
		String[] ftpVals = ftpConf.split("|");
		if (ftpVals.length < 3) {
			throw new RuntimeException("获取FTP配置失败，请检查是否正确配置[ftpKey = "
					+ ftpKey + "]");
		}
		return BankFtpUtil.ftpDownload(ftpVals[0], ftpVals[1], ftpVals[2],
				ftpFilePath);
	}

	public static byte[] ftpDownload(String hostname, String username,
			String password, String ftpFilePath) {
		FTPClient client = new FTPClient();
		InputStream is = null;
		ByteArrayOutputStream out = null;
		try {
			client.connect(hostname);
			if (!client.login(username, password)) {
				logger.info("登陆FTP服务器失败[ftp = " + hostname + "]");
				throw new RuntimeException(
						"登陆FTP服务器失败，请检查用户名和密码是否正确[ftp = " + hostname + "]");
			}
			logger.info("登陆FTP服务器成功[ftp = " + hostname + "]");

			if (ftpFilePath.indexOf("/") != -1
					&& !client.changeWorkingDirectory(ftpFilePath.substring(0,
							ftpFilePath.lastIndexOf("/") + 1))) {
				logger.info("路径错误或者没有此路径的访问权限[ftpFilePath = "
						+ ftpFilePath.substring(0,
								ftpFilePath.lastIndexOf("/") + 1) + "]");
				throw new RuntimeException(
						"路径错误或者没有此路径的访问权限[ftpFilePath = "
								+ ftpFilePath.substring(0,
										ftpFilePath.lastIndexOf("/") + 1) + "]");
			}
			String[] names = client.listNames();
			client.setBufferSize(1024 * 3);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);

			for (int i = 0; i < names.length; i++) {
				String fileName = ftpFilePath.indexOf("/") == -1 ? ftpFilePath
						: ftpFilePath
								.substring(ftpFilePath.lastIndexOf("/") + 1);
				if (names[i].equals(fileName)) {
					is = client.retrieveFileStream(fileName);
					out = new ByteArrayOutputStream(1024);
					int count = -1;
					while ((count = is.read()) != -1) {
						out.write(count);
					}
					logger.info("获取文件完毕[ftpFilePath = " + ftpFilePath + "]");
					return out.toByteArray();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ftp exception", e);
		} finally {
			try {
				if (is != null)
					is.close();
				if (out != null) {
					out.flush();
					out.close();
				}
				client.logout();
				client.disconnect();
				logger.info("FTP服务器退出[hostname = " + hostname + "]");
			} catch (IOException e) {
			}
		}
		logger.error("FTP服务器没有此文件[ftpFilePath = " + ftpFilePath + "]");
		return null;
	}

	public static void main(String[] args) {
		String hostname = "172.17.102.102";
		String username = "ci";
		String password = "Zj4xyBkgjd";
		byte[] str = BankFtpUtil.ftpDownload(hostname, username, password,
				"cebfile/SHOP.105110089999093.20130526.02.success.txt.gz");
		GZIPInputStream zis = null;
		try {
			zis = new GZIPInputStream(new BufferedInputStream(
					new ByteArrayInputStream(str)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					zis, "gbk"));
			String tempString = null;
			StringBuffer sbuf = new StringBuffer();
			while ((tempString = reader.readLine()) != null) {
				//System.out.println(tempString);
				sbuf.append(tempString).append(System.getProperty("line.separator"));
			}
			System.out.println(sbuf.toString().substring(0, 2000));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
