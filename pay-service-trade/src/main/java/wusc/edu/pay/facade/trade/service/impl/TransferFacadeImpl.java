package wusc.edu.pay.facade.trade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.trade.biz.TransferBiz;
import wusc.edu.pay.core.trade.dao.TransferRecordDao;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.service.TransferFacade;


@Component("transferFacade")
public class TransferFacadeImpl implements TransferFacade {

	@Autowired
	private TransferBiz transferBiz;

	@Autowired
	private TransferRecordDao transferRecordDao;

	/**
	 * 根据交易流水号查找转账记录.
	 * 
	 * @param trxNo
	 *            交易流水号.
	 * @return TransferRecord .
	 */
	@Override
	public TransferRecord getTransferRecordByTrxNo(String trxNo) {
		return transferRecordDao.getByTrxNo(trxNo);
	}

	/**
	 * 分页查询转账记录
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return
	 */
	@Override
	public PageBean queryTransferRecordListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return transferRecordDao.listPage(pageParam, paramMap);
	}

	/**
	 * 转账
	 * 
	 * @param targetLoginName
	 * @param sourceLoginName
	 * @param transferAmount
	 * @param transferDesc
	 */
	public void createTransfer(String transferTrxNo,String targetLoginName, String sourceLoginName, double transferAmount,
			String transferDesc) {
		transferBiz.createTransfer(transferTrxNo,targetLoginName, sourceLoginName, transferAmount, transferDesc);
	}
}
