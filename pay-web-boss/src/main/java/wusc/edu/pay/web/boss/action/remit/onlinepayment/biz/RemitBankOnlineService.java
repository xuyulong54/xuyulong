package wusc.edu.pay.web.boss.action.remit.onlinepayment.biz;

import java.io.File;
import java.util.List;

import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitProcess;


/**
 * 网银打款导出
 */
public interface RemitBankOnlineService {
	/**
	 * 网银打款导出
	 * @param file
	 * @param remitProcessList
	 */
	public void BankOnlineExport(File file,List<RemitProcess> remitProcessList,RemitChannel remitChannel);
}
