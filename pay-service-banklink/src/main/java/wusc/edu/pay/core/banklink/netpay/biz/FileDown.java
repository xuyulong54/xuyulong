package wusc.edu.pay.core.banklink.netpay.biz;

import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.vo.FileDownResult;


/**
 * 对账文件下载
 * 
 * @author Administrator
 * 
 */
public interface FileDown {

	/**
	 * 对帐文件下载，获得文件输出
	 * 
	 * @param payInterface
	 *            银行接口
	 * @param fileDate
	 * @return
	 */
	FileDownResult fileDown(String payInterface, Date fileDate);

}
