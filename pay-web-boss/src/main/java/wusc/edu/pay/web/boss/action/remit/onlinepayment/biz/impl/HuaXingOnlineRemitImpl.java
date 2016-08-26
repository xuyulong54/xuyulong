package wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.RemitBankOnlineService;


public class HuaXingOnlineRemitImpl implements RemitBankOnlineService {

	private static final Log log = LogFactory.getLog(HuaXingOnlineRemitImpl.class);

	private String custid;

	@Override
	public void BankOnlineExport(File file, List<RemitProcess> remitProcessList, RemitChannel remitChannel) {
		log.info("====info==== 总共有打款记录" + remitProcessList.size() + "条");


		/* 生成华兴打款文件 开始 */
		try {
			addHuaXingRemitZip(file, remitProcessList, remitChannel);
		} catch (Exception e) {
			log.error("广东华兴银行导出打款文件异常",e);
		}
		/* 生成华兴银行打款文件 结束 */

		log.info("====info==== 广东华兴银行打款文件结束");
	}

	/*
	 * 将打款文件压缩成zip
	 */
	private void addHuaXingRemitZip(File file, List<RemitProcess> remitProcessList, RemitChannel remitChannel ) throws Exception {
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);

			/* 华兴打款文件生成 开始 */

			if (remitProcessList.size() > 0) {
				ZipEntry zipEntry2 = new ZipEntry("HuaXing_Excel.xls");
				zos.putNextEntry(zipEntry2);
				ByteArrayOutputStream baos2 = huaxingOutExcel(remitProcessList, remitChannel);
				zos.write(baos2.toByteArray());
			}
			/* 华兴打款 结束 */

		} finally {
			try {
				if (zos != null)
					zos.close();
			} catch (Exception e) {
				log.error("广东华星银行生成打款文件异常：", e);
			}
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				log.error("广东华星银行生成打款文件流关闭异常：", e);
			}
		}
	}

	/**
	 * 华兴渠道打款文件
	 * 
	 * @return
	 * @throws Exception
	 */
	private ByteArrayOutputStream huaxingOutExcel(List<RemitProcess> remitProcessList, RemitChannel remitChannel) throws Exception {
		ByteArrayOutputStream bos = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		WritableCellFormat cellFormat1=new WritableCellFormat();
		cellFormat1.setAlignment(Alignment.RIGHT);//文字居右
		try {
			BigDecimal totalSum = BigDecimal.ZERO;
			DecimalFormat nf = new DecimalFormat("#0.00");
			nf.setMaximumFractionDigits(2);
			bos = new ByteArrayOutputStream();
			wwb = Workbook.createWorkbook(bos);
			ws = wwb.createSheet("Sheet1", 0);

			ws.addCell(new Label(0, 0, ""));
			ws.setColumnView(0, 8);
			ws.addCell(new Label(1, 0, "总笔数"));
			ws.setColumnView(1, 20);
			ws.addCell(new Label(2, 0, remitProcessList.size() + ""));
			ws.setColumnView(2, 30);
			ws.addCell(new Label(3, 0, "总金额"));
			ws.setColumnView(3, 10);
			ws.addCell(new Label(4, 0, ""));
			ws.setColumnView(4, 30);
			ws.addCell(new Label(5, 0, ""));
			ws.setColumnView(5, 10);
			ws.addCell(new Label(6, 0, ""));
			ws.setColumnView(6, 35);
			ws.addCell(new Label(7, 0, ""));
			ws.setColumnView(7, 20);
			ws.addCell(new Label(8, 0, ""));
			ws.setColumnView(8, 10);
			ws.addCell(new Label(9, 0, ""));
			ws.setColumnView(9, 10);
			ws.addCell(new Label(10, 0, ""));
			ws.setColumnView(10, 10);
			ws.addCell(new Label(11, 0, ""));
			ws.setColumnView(11, 10);

			ws.addCell(new Label(0, 1, "序号"));
			ws.addCell(new Label(1, 1, "付款账户"));
			ws.addCell(new Label(2, 1, "付款方户名"));
			ws.addCell(new Label(3, 1, "收款人类型"));
			ws.addCell(new Label(4, 1, "收款账号"));
			ws.addCell(new Label(5, 1, "收款方名称"));
			ws.addCell(new Label(6, 1, "收款方开户网点（本行账号不用填写开户网点和联行号）"));
			ws.addCell(new Label(7, 1, "收款方联行号"));
			ws.addCell(new Label(8, 1, "币种"));
			ws.addCell(new Label(9, 1, "金额	"));
			ws.addCell(new Label(10, 1, "备注"));
			ws.addCell(new Label(11, 1, "其他"));

			int k = 0;
			for (int i = 0, j = 2; i < remitProcessList.size(); i++) {
				RemitProcess remitProcess = remitProcessList.get(i);
				if (remitProcess == null)
					throw new Exception("打款信息为空！");
				log.info("==============> 收款人账号：" + remitProcess.getAccountNo() + ",收款人：" + remitProcess.getAccountName());

				BigDecimal amount = remitProcess.getAmount();

				String accountType = remitProcess.getAccountType().intValue() == 1 ? "个人" : "企业";
				String accountNo = remitProcess.getAccountNo();
				String accountName = remitProcess.getAccountName();
				String currency = remitProcess.getCurrency() == null ? "人民币" : CurrencyTypeEnum.getEnum(Integer.parseInt(remitProcess.getCurrency())).getDesc();

				ws.addCell(new Label(0, j, ++k + ""));
				ws.addCell(new Label(1, j, remitChannel.getSrcAccountNum()));
				ws.addCell(new Label(2, j, remitChannel.getSrcAccountName()));
				ws.addCell(new Label(3, j, accountType));
				ws.addCell(new Label(4, j, accountNo));
				ws.addCell(new Label(5, j, accountName));
				if (remitProcess.getBankName().startsWith("广东华兴银行")) {
					ws.addCell(new Label(6, j, ""));
					ws.addCell(new Label(7, j, ""));
				} else {
					ws.addCell(new Label(6, j, remitProcess.getBankName()));
					ws.addCell(new Label(7, j, remitProcess.getBankChannelNo()));
				}
				ws.addCell(new Label(8, j, currency));
				ws.addCell(new Label(9, j, amount + "",cellFormat1));
				ws.addCell(new Label(10, j, ""));
				ws.addCell(new Label(11, j, ""));
				j++;
				
				totalSum = totalSum.add(amount);
			}
			ws.addCell(new Label(4, 0, totalSum + ""));// 总金额

		} catch (BizException e) {
			wwb.removeSheet(0);
			ws = wwb.createSheet("Sheet1", 0);
			ws.addCell(new Label(0, 0, e.getMessage() + "，异常编码：" + e.getCode()));
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			wwb.removeSheet(0);
			ws = wwb.createSheet("Sheet1", 0);
			ws.addCell(new Label(0, 0, "系统发生异常："));
			log.error("系统发生异常：", e);
		} finally {
			wwb.write();
			try {
				if (wwb != null)
					wwb.close();
			} catch (Exception e) {
				log.error("关闭流异常：", e);
			}
		}
		return bos;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

}
