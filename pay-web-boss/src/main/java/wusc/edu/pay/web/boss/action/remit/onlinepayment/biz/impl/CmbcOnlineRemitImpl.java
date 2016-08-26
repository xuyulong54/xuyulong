package wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.web.boss.action.remit.onlinepayment.biz.RemitBankOnlineService;


public class CmbcOnlineRemitImpl implements RemitBankOnlineService {

	private static final Log log = LogFactory.getLog(CmbcOnlineRemitImpl.class);

	private String custid;

	@Override
	public void BankOnlineExport(File file, List<RemitProcess> remitProcessList, RemitChannel remitChannel) {
		log.info("====info==== 生成民生银行打款文件开始");
		List<RemitProcess> inPublicAndOutList = new ArrayList<RemitProcess>(); // 行内对公和跨行
		List<RemitProcess> inPrivateList = new ArrayList<RemitProcess>(); // 行内对私

		/* 将数据切分为行内对公和跨行和行内对私 开始 */
		for (RemitProcess remitProcess : remitProcessList) {
			if ((remitProcess.getBankChannelNo().substring(0, 3).equals("305") && remitProcess.getAccountType() == BankAccountTypeEnum.PUBLIC_ACCOUNTS
					.getValue()) || (!remitProcess.getBankChannelNo().substring(0, 3).equals("305"))) { // 该接口的批量打款，只支持民生行内对公打款和跨行打款；
				inPublicAndOutList.add(remitProcess);
			} else {
				inPrivateList.add(remitProcess);
			}
		}
		log.info("====info==== 总共有打款记录" + remitProcessList.size() + "条，其中行内对公和跨行有" + inPublicAndOutList.size() + "条,行内对私有"
				+ inPrivateList.size() + "条");

		/* 生成民生银行打款文件 开始 */
		try {
			addCmbcRemitZip(file, inPublicAndOutList, inPrivateList, remitChannel);
		} catch (Exception e) {
			log.error("民生银行打款文件生成异常：",e);
		}
		/* 生成民生银行打款文件 结束 */

		log.info("====info==== 生成民生银行打款文件结束");
	}

	/*
	 * 将打款文件压缩成zip
	 */
	private void addCmbcRemitZip(File file, List<RemitProcess> inPublicAndOutList, List<RemitProcess> inPrivateList,
			RemitChannel remitChannel) throws Exception {
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);

			/* 民生行内对公打款和跨行打款文件生成 开始 */
			if (inPublicAndOutList.size() > 0) {
				ZipEntry zipEntry1 = new ZipEntry("cmbc_InPublicAndOutExcel.xls");
				zos.putNextEntry(zipEntry1);
				ByteArrayOutputStream baos1 = cmbcInPublicAndOutExcel(inPublicAndOutList, remitChannel);
				zos.write(baos1.toByteArray());
			}
			/* 民生行内对公打款和跨行打款文件生成 结束 */

			/* 民生行内对私打款 开始 */
			if (inPrivateList.size() > 0) {
				ZipEntry zipEntry2 = new ZipEntry("cmbc_InPrivateExcel.xls");
				zos.putNextEntry(zipEntry2);
				ByteArrayOutputStream baos2 = cmbcInPrivateExcel(inPrivateList, remitChannel);
				zos.write(baos2.toByteArray());
			}
			/* 民生行内对私打款 结束 */

		} finally {
			try {
				if (zos != null)
					zos.close();
			} catch (Exception e) {
				log.error("民生银行打款文件生成异常：",e);
			}
			try {
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				log.error("民生银行打款文件流关闭异常：",e);
			}
		}
	}

	/**
	 * 民生银行网银打款文件：民生行内对公打款和跨行打款
	 * 
	 * @return
	 * @throws Exception
	 */
	private ByteArrayOutputStream cmbcInPublicAndOutExcel(List<RemitProcess> inPublicAndOutList, RemitChannel remitChannel ) throws Exception {
		ByteArrayOutputStream bos = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		try {
			BigDecimal totalSum = BigDecimal.ZERO;
			int count = 0;
			bos = new ByteArrayOutputStream();
			wwb = Workbook.createWorkbook(bos);
			ws = wwb.createSheet("Sheet1", 0);

			ws.addCell(new Label(0, 0, "FILETYPE")); // FILETYPE:“1”为批量授权,“2”为逐笔授权
			ws.addCell(new Label(1, 0, "1"));

			ws.addCell(new Label(0, 1, "TOAM")); // 总金额

			ws.addCell(new Label(0, 2, "COUT")); // 总笔数

			// 数据标题行
			ws.addCell(new Label(0, 3, "ordertype"));
			ws.addCell(new Label(1, 3, "txserno"));
			ws.addCell(new Label(2, 3, "compresstxserno"));
			ws.addCell(new Label(3, 3, "evouchno"));
			ws.addCell(new Label(4, 3, "custid"));
			ws.addCell(new Label(5, 3, "busitype"));
			ws.addCell(new Label(6, 3, "busscode"));
			ws.addCell(new Label(7, 3, "batchflag"));
			ws.addCell(new Label(8, 3, "txdate"));
			ws.addCell(new Label(9, 3, "txtime"));
			ws.addCell(new Label(10, 3, "bookflag"));
			ws.addCell(new Label(11, 3, "booktsfdate"));
			ws.addCell(new Label(12, 3, "booktsftime"));
			ws.addCell(new Label(13, 3, "userid"));
			ws.addCell(new Label(14, 3, "username"));
			ws.addCell(new Label(15, 3, "payeracct"));
			ws.addCell(new Label(16, 3, "payername"));
			ws.addCell(new Label(17, 3, "payerbranchname"));
			ws.addCell(new Label(18, 3, "payercurr"));
			ws.addCell(new Label(19, 3, "payercurrname"));
			ws.addCell(new Label(20, 3, "payeramt"));
			ws.addCell(new Label(21, 3, "payeeacct"));
			ws.addCell(new Label(22, 3, "payeename"));
			ws.addCell(new Label(23, 3, "payeeaccttype"));
			ws.addCell(new Label(24, 3, "payeebranchname"));
			ws.addCell(new Label(25, 3, "payeecurr"));
			ws.addCell(new Label(26, 3, "payeecurrname"));
			ws.addCell(new Label(27, 3, "payeeamt"));
			ws.addCell(new Label(28, 3, "midcustid"));
			ws.addCell(new Label(29, 3, "midacct"));
			ws.addCell(new Label(30, 3, "midname"));
			ws.addCell(new Label(31, 3, "midbranchname"));
			ws.addCell(new Label(32, 3, "midcurr"));
			ws.addCell(new Label(33, 3, "midcurrname"));
			ws.addCell(new Label(34, 3, "remark"));
			ws.addCell(new Label(35, 3, "txstascode"));
			ws.addCell(new Label(36, 3, "draweename"));
			ws.addCell(new Label(37, 3, "idname"));
			ws.addCell(new Label(38, 3, "idno"));
			ws.addCell(new Label(39, 3, "depttype"));
			ws.addCell(new Label(40, 3, "deptname"));
			ws.addCell(new Label(41, 3, "term"));
			ws.addCell(new Label(42, 3, "totalpaynum"));
			ws.addCell(new Label(43, 3, "wagefilename"));
			ws.addCell(new Label(44, 3, "retmsg"));
			ws.addCell(new Label(45, 3, "hostfile"));
			ws.addCell(new Label(46, 3, "trustid"));
			ws.addCell(new Label(47, 3, "pathcode"));
			ws.addCell(new Label(48, 3, "pathname"));
			ws.addCell(new Label(49, 3, "duedate"));
			ws.addCell(new Label(50, 3, "currdate"));
			ws.addCell(new Label(51, 3, "authmode"));
			ws.addCell(new Label(52, 3, "currauthmode"));
			ws.addCell(new Label(53, 3, "authoperlist"));
			ws.addCell(new Label(54, 3, "authopernamelist"));
			ws.addCell(new Label(55, 3, "delayflag"));
			ws.addCell(new Label(56, 3, "kerntimes"));
			ws.addCell(new Label(57, 3, "svcfee"));
			ws.addCell(new Label(58, 3, "svcfee1"));
			ws.addCell(new Label(59, 3, "corpid"));
			ws.addCell(new Label(60, 3, "corpname"));
			ws.addCell(new Label(61, 3, "billno"));
			ws.addCell(new Label(62, 3, "recseq"));
			ws.addCell(new Label(63, 3, "infoinputacctflag"));
			ws.addCell(new Label(64, 3, "movtelno"));
			ws.addCell(new Label(65, 3, "emailaddr"));
			ws.addCell(new Label(66, 3, "bnkcode"));

			for (int i = 0, j = 4; i < inPublicAndOutList.size(); i++) {
				RemitProcess remitProcess = inPublicAndOutList.get(i);

				if (remitProcess.getBankChannelNo().substring(0, 3).equals("305")) { // ordertype制单类型:1-行内转帐（只允许对公）2-跨行转帐
																						// （对私或对公）
					ws.addCell(new Label(0, j, "1"));
				} else {
					ws.addCell(new Label(0, j, "2"));
				}

				ws.addCell(new Label(1, j, "")); // txserno
				ws.addCell(new Label(2, j, "")); // compresstxserno
				ws.addCell(new Label(3, j, remitProcess.getRequestNo())); // evouchno
																			// 凭证号8位(打款对账时的凭证)
				ws.addCell(new Label(4, j, custid)); // custid银行客户号
				ws.addCell(new Label(5, j, "")); // busitype
				ws.addCell(new Label(6, j, "")); // busscode
				ws.addCell(new Label(7, j, "")); // batchflag
				ws.addCell(new Label(8, j, "")); // txdate
				ws.addCell(new Label(9, j, "")); // txtime
				ws.addCell(new Label(10, j, "0")); // bookflag预约标志:0-不预约
				ws.addCell(new Label(11, j, "")); // booktsfdate
				ws.addCell(new Label(12, j, "")); // booktsftime
				ws.addCell(new Label(13, j, "")); // userid
				ws.addCell(new Label(14, j, "")); // username
				ws.addCell(new Label(15, j, remitChannel.getSrcAccountNum())); // payeracct付款帐号
				ws.addCell(new Label(16, j, "")); // payername
				ws.addCell(new Label(17, j, "")); // payerbranchname
				ws.addCell(new Label(18, j, "")); // payercurr
				ws.addCell(new Label(19, j, "")); // payercurrname
				ws.addCell(new Label(20, j, remitProcess.getAmount().toString())); // payeramt转出金额
				ws.addCell(new Label(21, j, remitProcess.getAccountNo())); // payeeacct
																			// 收款单位帐号
				ws.addCell(new Label(22, j, remitProcess.getAccountName())); // payeename
																				// 收款单位名称
				ws.addCell(new Label(23, j, "")); // payeeaccttype
				if (!remitProcess.getBankChannelNo().substring(0, 3).equals("305")) { // payeebranchname
																						// 收款单位开户行名（行内不允许填写，跨行必输）
					ws.addCell(new Label(24, j, remitProcess.getBankName()));
				}
				ws.addCell(new Label(25, j, "01")); // payeecurr收款单位币种:01-人民币
				ws.addCell(new Label(26, j, "")); // payeecurrname
				ws.addCell(new Label(27, j, "")); // payeeamt
				ws.addCell(new Label(28, j, "")); // midcustid
				ws.addCell(new Label(29, j, "")); // midacct
				ws.addCell(new Label(30, j, "")); // midname
				ws.addCell(new Label(31, j, "")); // midbranchname
				ws.addCell(new Label(32, j, "")); // midcurr
				ws.addCell(new Label(33, j, "")); // midcurrname
				ws.addCell(new Label(34, j, "用途")); // remark 用途
				ws.addCell(new Label(35, j, "")); // txstascode
				ws.addCell(new Label(36, j, "")); // draweename
				ws.addCell(new Label(37, j, "")); // idname
				ws.addCell(new Label(38, j, "")); // idno
				ws.addCell(new Label(39, j, "")); // depttype
				ws.addCell(new Label(40, j, "")); // deptname
				ws.addCell(new Label(41, j, "")); // term
				ws.addCell(new Label(42, j, "")); // totalpaynum
				ws.addCell(new Label(43, j, "")); // wagefilename
				ws.addCell(new Label(44, j, "")); // retmsg
				ws.addCell(new Label(45, j, "")); // hostfile
				ws.addCell(new Label(46, j, "")); // trustid
				if (!remitProcess.getBankChannelNo().substring(0, 3).equals("305")) { // pathcode
																						// 汇路码（行内不允许填写，跨行必输）跨行交易必输为:（汇路说明见下文）6-小额实时支付（逐笔为1）
																						// 7-大额实时支付（逐笔为0）
																						// 10-上海同城实时支付（逐笔为4）
																						// 11-网银互联（逐笔为2）;上海地区走“上海同城支付”；非上海地区，小于5万走“网银互联”，大于5万走“大额实时支付”。
					if (remitProcess.getProvince().contains("上海")) { // 上海地区走“上海同城支付”；
						if (remitProcess.getBankChannelNo().substring(0, 3).equals("403")) { // 邮储银行
							if (remitProcess.getAmount().doubleValue() >= 50000) { // 大于或等于5万走“大额实时支付”。
								ws.addCell(new Label(47, j, "7"));
							} else { // 小于5万走“网银互联”
								ws.addCell(new Label(47, j, "11"));
							}
						} else {
							ws.addCell(new Label(47, j, "10"));
						}
					} else {
						if (remitProcess.getAmount().doubleValue() >= 50000) { // 大于或等于5万走“大额实时支付”。
							ws.addCell(new Label(47, j, "7"));
						} else { // 小于5万走“网银互联”
							ws.addCell(new Label(47, j, "11"));
						}
					}
				}
				
				ws.addCell(new Label(48, j, "")); // pathname
				ws.addCell(new Label(49, j, "")); // duedate
				ws.addCell(new Label(50, j, "")); // currdate
				ws.addCell(new Label(51, j, "")); // authmode
				ws.addCell(new Label(52, j, "")); // currauthmode
				ws.addCell(new Label(53, j, "")); // authoperlist
				ws.addCell(new Label(54, j, "")); // authopernamelist
				ws.addCell(new Label(55, j, "")); // delayflag
				ws.addCell(new Label(56, j, "")); // kerntimes
				ws.addCell(new Label(57, j, "")); // svcfee
				ws.addCell(new Label(58, j, "")); // svcfee1
				ws.addCell(new Label(59, j, "")); // corpid
				ws.addCell(new Label(60, j, "")); // corpname
				ws.addCell(new Label(61, j, "")); // billno
				ws.addCell(new Label(62, j, "")); // recseq
				ws.addCell(new Label(63, j, "0")); // infoinputacctflag是否通知收款方:0-不通知收款方
													// 1-通知收款方

				ws.addCell(new Label(64, j, "")); // movtelno
				ws.addCell(new Label(65, j, "")); // emailaddr
				if (!remitProcess.getBankChannelNo().substring(0, 3).equals("305")) { // bnkcode联行号（行内不允许填写，跨行必输）
					ws.addCell(new Label(66, j, remitProcess.getBankChannelNo() + "&" + remitProcess.getBankName()));
				}

				totalSum = totalSum.add(remitProcess.getAmount());
				count++;
				j++;

			}
			ws.addCell(new Label(1, 1, totalSum.toString())); // 总金额

			ws.addCell(new Label(1, 2, count + "")); // 总笔数
			
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
			log.error(e);
		} finally {
			wwb.write();
			try {
				if (wwb != null)
					wwb.close();
			} catch (Exception e) {
				log.error("民生银行打款文件生成异常：", e);
			}
		}
		return bos;
	}

	/**
	 * 民生银行网银打款文件：民生行内对私打款
	 * 
	 * @return
	 * @throws Exception
	 */
	private ByteArrayOutputStream cmbcInPrivateExcel(List<RemitProcess> inPrivateList, RemitChannel remitChannel)
			throws Exception {
		ByteArrayOutputStream bos = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		try {
			BigDecimal totalSum = BigDecimal.ZERO;
			int count = 0;
			bos = new ByteArrayOutputStream();
			wwb = Workbook.createWorkbook(bos);
			ws = wwb.createSheet("Sheet1", 0);

			ws.addCell(new Label(0, 0, "ATNU"));
			ws.addCell(new Label(1, 0, "0019999")); // ATNU:民生银行设定?

			ws.addCell(new Label(0, 1, "MICN"));
			ws.addCell(new Label(1, 1, "0")); // MICN：民生银行设定?

			ws.addCell(new Label(0, 2, "CUNM"));
			ws.addCell(new Label(1, 2, remitChannel.getSrcAccountName())); // CUNM：付款账户名称

			ws.addCell(new Label(0, 3, "MIAC"));
			ws.addCell(new Label(1, 3, remitChannel.getSrcAccountNum())); // MIAC：付款方的银行账号

			ws.addCell(new Label(0, 4, "EYMD"));
			ws.addCell(new Label(1, 4, "1")); // EYMD：民生银行设定?

			ws.addCell(new Label(0, 5, "TOAM")); // 总金额

			ws.addCell(new Label(0, 6, "COUT")); // 总笔数

			// 数据标题行
			ws.addCell(new Label(0, 7, "个人帐号"));
			ws.addCell(new Label(1, 7, "金额"));
			ws.addCell(new Label(2, 7, "姓名"));

			for (int i = 0, j = 8; i < inPrivateList.size(); i++) {
				RemitProcess remitProcess = inPrivateList.get(i);

				ws.addCell(new Label(0, j, remitProcess.getAccountNo())); // 个人帐号
				ws.addCell(new Label(1, j, remitProcess.getAmount().toString())); // 金额
				ws.addCell(new Label(2, j, remitProcess.getAccountName())); // 姓名

				totalSum = totalSum.add(remitProcess.getAmount());
				count++;
				j++;
			}
			ws.addCell(new Label(1, 5, totalSum.toString())); // 总金额
			ws.addCell(new Label(1, 6, count + "")); // 总笔数
		} catch (BizException e) {
			wwb.removeSheet(0);
			ws = wwb.createSheet("Sheet1", 0);
			ws.addCell(new Label(0, 0, e.getMessage() + "，异常编码：" + e.getCode()));
			log.error(e);
		} catch (Exception e) {
			wwb.removeSheet(0);
			ws = wwb.createSheet("Sheet1", 0);
			ws.addCell(new Label(0, 0, "系统发生异常："));
			log.error(e);
		} finally {
			wwb.write();
			try {
				if (wwb != null)
					wwb.close();
			} catch (Exception e) {
				log.error("民生银行打款文件生成异常：", e);
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
