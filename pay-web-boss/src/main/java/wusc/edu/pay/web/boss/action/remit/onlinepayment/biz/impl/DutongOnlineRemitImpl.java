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
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;
import wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.RemitBankOnlineService;


public class DutongOnlineRemitImpl implements RemitBankOnlineService {

	@Autowired
	private RemitBankInfoFacade remitBankInfoFacade;

	private static final Log log = LogFactory.getLog(DutongOnlineRemitImpl.class);

	private String custid;

	@Override
	public void BankOnlineExport(File file, List<RemitProcess> remitProcessList, RemitChannel remitChannel) {
		log.info("====info==== 总共有打款记录" + remitProcessList.size() + "条");

		/* 生成度通打款文件 开始 */
		try {
			addDutongRemitZip(file, remitProcessList, remitChannel);
		} catch (Exception e) {
			log.error("度通打款文件导出异常：", e);
		}
		/* 生成民生银行打款文件 结束 */

		log.info("====info==== 度通打款文件结束");
	}

	/*
	 * 将打款文件压缩成zip
	 */
	private void addDutongRemitZip(File file, List<RemitProcess> remitProcessList, RemitChannel remitChannel) throws Exception {
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);

			/* 度通打款文件生成 开始 */

			if (remitProcessList.size() > 0) {
				ZipEntry zipEntry2 = new ZipEntry("dutong_Excel.xls");
				zos.putNextEntry(zipEntry2);
				ByteArrayOutputStream baos2 = dutongOutExcel(remitProcessList, remitChannel);
				zos.write(baos2.toByteArray());
			}
			/* 度通打款 结束 */

		} finally {
			try {
				if (zos != null)
					zos.close();
			} catch (Exception e) {
				log.error("度通打款文件导出异常：", e);
			}
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				log.error("度通打款文件导出异常：", e);
			}
		}
	}

	/**
	 * 度通渠道打款文件
	 * 
	 * @return
	 * @throws Exception
	 */
	private ByteArrayOutputStream dutongOutExcel(List<RemitProcess> remitProcessList, RemitChannel remitChannel) throws Exception {
		ByteArrayOutputStream bos = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		try {
			BigDecimal totalSum = BigDecimal.ZERO;
			DecimalFormat nf = new DecimalFormat("#0.00");
			nf.setMaximumFractionDigits(2);
			bos = new ByteArrayOutputStream();
			wwb = Workbook.createWorkbook(bos);
			ws = wwb.createSheet("Sheet1", 0);

			ws.addCell(new Label(0, 0, ""));
			ws.setColumnView(0, 10);
			ws.addCell(new Label(1, 0, "总笔数"));
			ws.setColumnView(1, 10);
			ws.addCell(new Label(2, 0, remitProcessList.size() + ""));
			ws.setColumnView(2, 20);
			ws.addCell(new Label(3, 0, "总金额"));
			ws.setColumnView(3, 20);
			ws.addCell(new Label(4, 0, ""));
			ws.setColumnView(4, 20);
			ws.addCell(new Label(5, 0, "付款账户"));
			ws.setColumnView(5, 20);
			ws.addCell(new Label(6, 0, remitChannel.getSrcAccountNum()));
			ws.setColumnView(6, 20);
			ws.addCell(new Label(7, 0, "付款方户名"));
			ws.setColumnView(7, 10);
			ws.addCell(new Label(8, 0, remitChannel.getSrcAccountName()));
			ws.setColumnView(8, 20);
			ws.addCell(new Label(9, 0, ""));
			ws.setColumnView(9, 10);

			ws.addCell(new Label(0, 1, "序号"));
			ws.addCell(new Label(1, 1, "收款人类型"));
			ws.addCell(new Label(2, 1, "收款账号"));
			ws.addCell(new Label(3, 1, "收款方名称"));
			ws.addCell(new Label(4, 1, "转账方式"));
			ws.addCell(new Label(5, 1, "收款方开户网点"));
			ws.addCell(new Label(6, 1, "收款方联行号"));
			ws.addCell(new Label(7, 1, "币种"));
			ws.addCell(new Label(8, 1, "金额"));
			ws.addCell(new Label(9, 1, "备注"));

			final BigDecimal maxPaidAmount = BigDecimal.valueOf(50000.00);// 每笔最大代付金额
			int k = 0;
			for (int i = 0, j = 2; i < remitProcessList.size(); i++) {
				RemitProcess remitProcess = remitProcessList.get(i);
				if (remitProcess == null)
					throw new Exception("打款信息为空！");
				log.info("==============> 收款人账号：" + remitProcess.getAccountNo() + ",收款人：" + remitProcess.getAccountName());

				BigDecimal amount = remitProcess.getAmount();
				int breakUpNum = amount.divide(maxPaidAmount, 2).intValue();
				breakUpNum = amount.compareTo(maxPaidAmount.multiply(BigDecimal.valueOf(breakUpNum))) == 1 ? breakUpNum + 1 : breakUpNum;// 拆分笔数

				log.info("==============> 拆分笔数：" + breakUpNum + "  打款请求号为：" + remitProcess.getRequestNo());
				RemitBankInfo remitBankInfo = remitBankInfoFacade.getByBankChannelNo(remitProcess.getBankChannelNo());
				if (remitBankInfo == null) {
					throw new Exception("打款银行信息为空！");
				}
				remitBankInfo = remitBankInfoFacade.getByBankChannelNo(remitBankInfo.getClearBankChannelNo());
				if (remitBankInfo == null) {
					throw new Exception("打款银行信息为空！");
				}
				String accountType = remitProcess.getAccountType().intValue() == 1 ? "个人客户" : "企业客户";
				String accountNo = remitProcess.getAccountNo();
				String accountName = remitProcess.getAccountName();
				String bankName = remitBankInfo.getBankName();
				String clearBankChannelNo = remitBankInfo.getClearBankChannelNo();
				String currency = remitProcess.getCurrency() == null ? "人民币" : CurrencyTypeEnum.getEnum(Integer.parseInt(remitProcess.getCurrency())).getDesc();

				for (int h = 1; h <= breakUpNum; h++) {
					ws.addCell(new Label(0, j, ++k + ""));
					ws.addCell(new Label(1, j, accountType));
					ws.addCell(new Label(2, j, accountNo));
					ws.addCell(new Label(3, j, accountName));
					ws.addCell(new Label(4, j, "实时"));

					ws.addCell(new Label(5, j, bankName));
					ws.addCell(new Label(6, j, clearBankChannelNo));
					ws.addCell(new Label(7, j, currency));
					// 每笔金额
					BigDecimal amountTemp = BigDecimal.ZERO;
					if (breakUpNum == 1) {
						amountTemp = remitProcess.getAmount();
					} else {
						if (h < breakUpNum) {
							amountTemp = maxPaidAmount;
						} else {
							amountTemp = amount.subtract(maxPaidAmount.multiply(new BigDecimal(breakUpNum - 1)));
						}
					}
					ws.addCell(new Label(8, j, nf.format(amountTemp)));
					ws.addCell(new Label(9, j, ""));
					j++;
				}
				
				totalSum = totalSum.add(amount);// 金额累加
				
			}
			ws.addCell(new Label(4, 0, totalSum + ""));// 总金额
			log.info("====info==== 行内对公打款和跨行打款,批量更新处理记录的状态信息成功");

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

	public static void main(String[] args) {
		final BigDecimal maxPaidAmount = new BigDecimal(49999.99);// 每笔最大代付金额
		BigDecimal maxPaidAmount1 = new BigDecimal(49999.990000);
		System.out.println("====>" + maxPaidAmount.compareTo(maxPaidAmount1));

	}

}
