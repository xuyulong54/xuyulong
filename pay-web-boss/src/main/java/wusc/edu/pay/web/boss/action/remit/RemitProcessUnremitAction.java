/**
 * wusc.edu.pay.web.boss.action.remit.RemitProcessAction.java
 */
package wusc.edu.pay.web.boss.action.remit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.enums.RemitProcessStatusEnum;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitChannelFacade;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.RemitBankOnlineService;
import wusc.edu.pay.web.boss.action.remit.onlinepayment.springfactory.BankOnlineExportFactory;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * <ul>
 * <li>Title:网银打款（导入，导出）</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-4
 */
public class RemitProcessUnremitAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RemitProcessUnremitAction.class);
	@Autowired
	private RemitProcessFacade remitProcessFacade;
	@Autowired
	private RemitChannelFacade remitChannelFacade;
	@Autowired
	private BankOnlineExportFactory bankOnlineExportFactory;

	private File upload;
	private String uploadContentType;// 上传文件类型
	private String uploadFileName;// 上传文件名

	/**
	 * 网银打款
	 * 
	 * @return
	 */
	@Permission("boss:remitProcessUnremit:view")
	public String remitProcessUnremitList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate1", getString("beginDate1"));
		paramMap.put("endDate1", getString("endDate1"));
		paramMap.put("status", RemitProcessStatusEnum.ONLINE_BANK_WAIT.getValue());// 网银打款处理中
		paramMap.put("channelCode", getString("channelCode"));// 打款渠道
		paramMap.put("requestNo", getString("requestNo"));// 请求流水号
		paramMap.put("accountName", getString("accountName"));// 收款户名
		paramMap.put("bankChannelNo", getString("bankChannelNo"));// 收款账号
		paramMap.put("bankName", getString("bankName"));// 收款发卡行
		paramMap.put("isCount", 1);// 是否统计
		paramMap.put("userType", getString("userType"));
		super.pageBean = remitProcessFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);

		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("status", PublicStatusEnum.ACTIVE.getValue());
		List<RemitChannel> remitChannelList = remitChannelFacade.listBy(paramMap1);// 打款通道
		this.putData("remitChannelList", remitChannelList);
		this.putData("RemitProcessStatusEnums", RemitProcessStatusEnum.values());
		this.putData("UserTypeEnum", UserTypeEnum.toMap());
		return "remitProcessUnremitList";
	}

	/***
	 * 网银导出功能
	 * 
	 * @throws IOException
	 */
	@Permission("boss:remitProcessUnremit:expor")
	public void exportExcel() throws IOException {
		String batchNo = getString("batchNo");
		String channelCode = getString("channelCode");
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		paramMap.put("channelCode", channelCode);
		
		log.info("====info==== 网银打款导出开始，批次号:" + batchNo);
		String filename = sdfFile.format(new Date()) + "_OnlinePayment";
		String directory = "remit_files/" + sdf.format(new Date()) + "/";
		File dir = new File(directory);
		
		if (!dir.exists()){
			dir.mkdirs();
		}
			
		File file = new File(directory + new String((filename + ".zip").getBytes("ISO-8859-1"), "UTF-8"));

		remitBankOnline(file, paramMap);
		
		OutputStream os = getHttpResponse().getOutputStream();
		
		FileInputStream fis = new FileInputStream(file);
		
		int read = 0;
		byte[] buffBytes = new byte[1024];
		while ((read = fis.read(buffBytes)) != -1) {
			os.write(buffBytes, 0, read);
		}
		fis.close();
		log.info("====info==== 网银打款导出结束");
		this.logSave("网银打款导出：" + filename);
		this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + file.getName());
	}


	private void remitBankOnline(File file, Map<String, Object> paramMap) throws RemitBizException, AccountBizException {
		
				try {
					String channelCode = String.valueOf(paramMap.get("channelCode"));
					RemitBankOnlineService remitBankOnlineService = (RemitBankOnlineService) bankOnlineExportFactory.getService(channelCode);
					if (remitBankOnlineService != null) {
						RemitChannel remitChannel = remitChannelFacade.getByChannelCode(channelCode);
						
						List<RemitProcess> remitProcessList = remitProcessFacade.listBy(paramMap);
						remitBankOnlineService.BankOnlineExport(file, remitProcessList, remitChannel);
					}
				} catch (Exception e) {
					log.error("====error==== 没有该银行的导出实现", e);
					try {
						FileOutputStream fos = new FileOutputStream(file);
						ZipOutputStream zos = new ZipOutputStream(fos);
						ZipEntry zipEntry1 = new ZipEntry("bank_error.xls");
						zos.putNextEntry(zipEntry1);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						WritableWorkbook wwb = Workbook.createWorkbook(bos);
						WritableSheet ws = wwb.createSheet("Sheet1", 0);
						ws.addCell(new Label(0, 0, "没有该银行的导出实现(" + paramMap.get("CHANNEL_CODE").toString() + ")"));
						wwb.write();
						wwb.close();
						zos.write(bos.toByteArray());
						zos.close();
						fos.close();
						return;
					} catch (Exception e1) {
						log.error("网银导出数据异常：", e1);
					}
				}
	}

	public void remitProcessExist() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String ids = getString("ids");
		if (!ValidateUtils.isEmpty(ids)) {
			paramMap.put("ids", ids.split(","));// id集合
		} else {
			paramMap.put("beginDate1", getString("beginDate1"));
			paramMap.put("endDate1", getString("endDate1"));

			paramMap.put("status", RemitProcessStatusEnum.ONLINE_BANK_WAIT.getValue());// 网银打款处理中
			paramMap.put("ebankRemit", "ebankRemit");// 标示（网银打款，非自动打款）

			paramMap.put("channelCode", getString("channelCode"));// 打款通道
			paramMap.put("requestNo", getString("requestNo"));// 请求流水号
			paramMap.put("accountName", getString("accountName"));// 收款户名
			paramMap.put("bankChannelNo", getString("bankChannelNo"));// 收款账号
			paramMap.put("bankName", getString("bankName"));// 收款发卡行
		}
		List<RemitProcess> remitProcessList = remitProcessFacade.listBy(paramMap);
		if (remitProcessList.size() == 0) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "没有要导出的记录");
		} else {
			getOutputMsg().put("STATE", "SUCCESS");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * @return
	 */
	public String importExcelUI() {
		return "importExcelUI";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
