package wusc.edu.pay.web.boss.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.utils.sftp.Sftp;
import wusc.edu.pay.web.boss.exceptions.BossException;

import com.jcraft.jsch.ChannelSftp;


/***
 * @ClassName: FileUtil
 * @Description: 上传文件到sftp服务器
 * @author huangbin
 * @date 2015-3-5 下午1:57:24
 *
 */
public class FileUtil {
	
	private static final Log log = LogFactory.getLog(FileUtil.class);
	
	// SFTP服务器连接信息
	// SFTP服务器连接信息
	private static String SFTP_IP = PublicConfig.SFTP_IP;
	private static int SFTP_PORT = PublicConfig.SFTP_PORT;
	private static String SFTP_USER = PublicConfig.SFTP_USER;
	private static String SFTP_PWD = PublicConfig.SFTP_PWD;
	private static String SFTP_DIR = PublicConfig.SFTP_DIR;
	
	
	/**
	 * 对于内容为非中文的大文件读取，可根据该方法读取文件制定位置的字符串。
	 * @param filePath  文件路径
	 * @param beginIndex  开始字符位置
	 * @param ByteLength  读取字符长度
	 * @return  读取的字符串
	 * @throws IOException
	 */
	public static String getStringFromFile(String filePath , int beginIndex , int ByteLength) throws IOException{
		String returnStr = null;
		RandomAccessFile randomFile = new RandomAccessFile(filePath, "r");
	        randomFile.seek(beginIndex);
	        byte[] bytes = new byte[ByteLength];//每次读取多少次
	        while ((randomFile.read(bytes)) != -1) {
	        	returnStr = new String(bytes);
	        	break;
	        }
	        randomFile.close();
		return returnStr;
	}
	
	/**
	 * 传入文件夹路径，该方法能够实现创建整个路径
	 * @param path 文件夹路径，不包含文件名称及后缀名
	 */
	public static void isDir(String path){
		String[] paths = path.split("/");
			String filePath = "";
			for(int i = 0 ; i < paths.length ; i++){
				if(i == 0){
					filePath = paths[0];
				}else{
					filePath += "/" + paths[i];
				}
				creatDir(filePath);
			}
	}
	
	/**
	 * 该方法用来判断文件夹是否存在，如果不存在则创建，存在则什么都不做
	 * @param filePath
	 */
	public static void creatDir(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
				file.mkdir();
		}
	}
	
	/**
	 * 上传文件到文件服务器
	 * @param srcFile 源文件
	 * @param bankChannelCode 银行渠道编码
	 * @param fileDate 文件日期
	 * @param fileType 文件类型 1：银行原始对账文件 ，2：转换后的对账文件
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String uploadFileToSFTP(File srcFile , String bankChannelCode , Date fileDate , int fileType){
		if(srcFile == null){
			log.error("File is null");
			return null;//文件为空，直接返回null值
		}
		StringBuffer filePath = new StringBuffer();//Context.SYSTEM_CONFIG.get("sftp.dir") + ""
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(fileDate);
		if(fileType == 1){ //银行系统源文件
			filePath.append(SFTP_DIR).append("src/").append(bankChannelCode).append("/").append(dateStr.substring(0, 4)).append("/")
					.append(dateStr.substring(5,7)).append("/");
		}else if(fileType == 2){ //解析后对账文件
			filePath.append(SFTP_DIR).append("conver/").append(bankChannelCode).append("/").append(dateStr.substring(0, 4)).append("/")
					.append(dateStr.substring(5,7)).append("/");
		}else if (fileType == 3){
			// 上传小pos的数字签名-by huangbin
			filePath.append(SFTP_DIR).append("posticket/").append(dateStr).append("/");
		}else{//错误的业务类型
			log.error("FileType is err");
			return null;//文件类型异常，返回null
		}
		Sftp sf = new Sftp();
		String host = SFTP_IP;
		int port = SFTP_PORT ;
		String username = SFTP_USER;
		String password = SFTP_PWD;
		ChannelSftp sftp = null;
		try{
			log.info("host:" + host);
			log.info("port:" + port);
			log.info("username:" + username);
			log.info("password:" + password);
			sftp = sf.connect(host, port, username, password);
			if(sftp == null){
				log.error("文件上传SFTP服务器连接失败");
				return null;
			}
			sf.creatDir(filePath.toString(), sftp);
			sf.upload(filePath.toString(), srcFile.getPath(), sftp);
			return filePath.append(srcFile.getName()).toString();
		}catch(Exception e){
			log.error("文件上传SFTP服务器失败：" + e);
			return null;
		}finally{
			if(sftp != null){
				sf.sftpClose(sftp);	
			}
		}
	}
	
	
	/**
	 * 从SFTP服务器上下载文件
	 * 
	 * @return
	 * @throws BossException 
	 */
	public static File downFileFromSFTP(final String filePath) throws BossException {
		// 创建临时目录，用来存放下载的文件
		StringBuffer tempFilePath = new StringBuffer(System.getProperty("user.dir")).append(File.separator).append("temp");
		isDir(tempFilePath.toString());
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		String tempPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);

		// 创建临时返回文件
		String saveFile = tempFilePath + "/" + fileName;
		File returnFile = null;

		Sftp sftp = new Sftp();
		ChannelSftp channelSftp = null;
		try {
			returnFile = new File(saveFile);
			channelSftp = sftp.connect(SFTP_IP, SFTP_PORT, SFTP_USER, SFTP_PWD);
			Sftp.download(tempPath, fileName, saveFile, channelSftp);
		} catch (Exception e) {
			log.error("==>对账文件下载失败：", e);
			throw new BossException("下载失败");
		} finally {
			if (channelSftp != null) {
				Sftp.sftpClose(channelSftp);
			}
		}
		return returnFile;
	}
	
}
