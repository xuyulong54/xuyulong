package wusc.edu.pay.core.banklink.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.core.banklink.netpay.util.BankConfUtil;


/**
 * 文件读取工具类
 * 
 * @author Administrator
 * 
 */
public class BankFileUtil {

	private static final Log logger = LogFactory.getLog(BankFileUtil.class);

	/**
	 * 获得文件的真实路径
	 * 
	 * <pre>
	 * 文件根路径+相对路径=真实路径
	 * </pre>
	 * 
	 * @param publicKeyPath2
	 * @return
	 */
	public static String getRealKeyPath(String relativePath) {
		String rpath = BankConfUtil.getCertRoot() + relativePath;
		logger.info("读取证书文件路径：" + rpath);
		return rpath;
	}

	/**
	 * 读取文件字节流
	 * <p>
	 * 根据相对路径，拼接绝对路径，读取文件
	 * </p>
	 * 
	 * @param realPath
	 * @return
	 */
	public static byte[] getCertFileByte(String realPath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(realPath);
			byte[] result = new byte[fis.available()];
			fis.read(result);
			return result;
		} catch (FileNotFoundException e) {
			logger.error("读取文件失败，文件不存在[path = {}]" + realPath);
			throw new RuntimeException("文件路径错误:" + realPath);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取文件失败，文件字节流获取失败[path = {}]" + realPath);
			throw new RuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取文件数据为对象
	 * 
	 * @param realPath
	 * @return
	 */
	public static Object getCertFileObject(String realPath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(realPath);
			ObjectInputStream p = new ObjectInputStream(fis);
			Object target = p.readObject();
			return target;
		} catch (FileNotFoundException e) {
			logger.error("读取文件失败，文件不存在[path = {}]" + realPath);
			throw new RuntimeException("文件路径错误:" + realPath);
		} catch (IOException e) {
			logger.error("读取文件失败，文件字节流获取失败[path = {}]" + realPath);
			throw new RuntimeException("读取文件失败，文件字节流获取失败[path = " + realPath + "]", e);
		} catch (ClassNotFoundException e) {
			logger.error("读取文件失败，文件读取为对象失败[path = {}]" + realPath);
			throw new RuntimeException("读取文件失败，文件读取为对象失败[path = " + realPath + "]", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取文件字符串
	 * <p>
	 * 根据相对路径，拼接绝对路径，读取文件
	 * </p>
	 * 
	 * @param realPath
	 * @return
	 */
	public static String getCertFileString(String realPath) {
		FileReader fis = null;
		BufferedReader bufferedReader = null;
		try {
			fis = new FileReader(realPath);
			bufferedReader = new BufferedReader(fis);
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			return result.toString().trim();
		} catch (FileNotFoundException e) {
			logger.error("读取文件失败，文件不存在[path = {}]" + realPath);
			throw new RuntimeException("文件路径错误:" + realPath);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取文件失败，文件内容获取失败[path = {}]" + realPath);
			throw new RuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void writeFile(String content, String path, String charSet) {
		BufferedWriter checkFileB = null;
		try {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), charSet);
			// FileWriter checkFileW = new FileWriter(file);
			checkFileB = new BufferedWriter(osw, 1024 * 3);
			checkFileB.write(content);
		} catch (IOException e) {
			logger.error("写文件失败，文件内容写失败[path = " + path + "]", e);
			throw new RuntimeException(e);
		} finally {
			if (checkFileB != null) {
				try {
					checkFileB.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
