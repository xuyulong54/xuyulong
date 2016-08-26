package wusc.edu.pay.web.portal.action;

import java.io.InputStream;
import java.util.Map;

import wusc.edu.pay.common.utils.ResourceUtils;
import wusc.edu.pay.common.web.file.FastDFSClient;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport {

	private static final long serialVersionUID = 3058182725162632825L;

	private String fileId;
	private String downloadFileName;

	public InputStream getInputStream() throws Exception {
		InputStream inputStream = FastDFSClient.downloadFile(fileId);
		int pos = fileId.lastIndexOf("/");
		if (pos <= 0) {
			return null;
		}
		String fileName = fileId.substring(pos + 1);
		Map<String, String> fileMap = ResourceUtils.getResource("system").getMap();
		String file = fileMap.get(fileName);
//		String file = new String(fileMap.get(fileName).getBytes("ISO-8859-1"),"utf-8");
		setDownloadFileName(file);
		return inputStream;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

}
