package wusc.edu.pay.web.portal.action.member;

import java.io.IOException;

/**
 * 转账
 * 
 * @author liliqiong
 * @date 2013-10-9
 * @version 1.0
 */
public class ReceiveTransferAction extends wusc.edu.pay.web.portal.action.merchant.ReceiveTransferAction {

	private static final long serialVersionUID = 5141390104000001658L;

	/**
	 * 转帐列表
	 * 
	 * @return
	 */
	public String listReceiveTransfer() {
		return super.listReceiveTransfer();
	}

	/**
	 * 导出转账交易记录.
	 * 
	 * @throws IOException
	 */
	public void exportReceiveTransferToExcel() throws IOException {
		super.exportReceiveTransferToExcel();
	}

	/**
	 * 转账
	 * 
	 * @return
	 */
	public String transfer() {
		return super.transfer();
	}

	/**
	 * 进入转账页面
	 * 
	 * @return
	 */
	public String ransferPage() {
		return super.ransferPage();
	}

}
