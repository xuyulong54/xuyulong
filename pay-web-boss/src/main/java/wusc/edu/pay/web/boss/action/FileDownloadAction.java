package wusc.edu.pay.web.boss.action;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.file.FastDFSClient;
import wusc.edu.pay.facade.boss.entity.ContractManagement;
import wusc.edu.pay.facade.boss.service.ContractManagementFacade;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport {

	private static final long serialVersionUID = 3058182725162632825L;

	@Autowired
	private ContractManagementFacade contractManagementFacade;
	private String downloadFileName;
	private Long id;

	/**
	 * 文件下载
	 * @return InputStream
	 * @throws Exception
	 */
	@Permission("boss:contract:download")
	public InputStream getInputStream() throws Exception {
		
		ContractManagement CMParam = contractManagementFacade.getById(id);
		String fileId = CMParam.getContractFile();
		String fileName = CMParam.getFileName();
		InputStream inputStream = FastDFSClient.downloadFile(fileId);
		
		setDownloadFileName(new String(fileName.getBytes(),"ISO8859-1"));
		return inputStream;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
