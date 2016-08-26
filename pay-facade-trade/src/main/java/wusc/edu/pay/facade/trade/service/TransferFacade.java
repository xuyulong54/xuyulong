package wusc.edu.pay.facade.trade.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.exception.TradeBizException;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * 
 * @描述: 转账功能，对外服务接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-9,上午10:05:21 .
 * @版本: 1.0 .
 */
public interface TransferFacade {

	/**
	 * 根据交易流水号查找转账记录.
	 * 
	 * @param id
	 *            .
	 * @return TransferRecord .
	 */
	TransferRecord getTransferRecordByTrxNo(String trxNo) throws TradeBizException;

	/**
	 * 分页查询转账记录
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return
	 */
	PageBean queryTransferRecordListPage(PageParam pageParam, Map<String, Object> paramMap) throws TradeBizException;

	/**
	 * 转账
	 * 
	 * @param targetLoginName
	 * @param sourceLoginName
	 * @param transferAmount
	 * @param transferDesc
	 */
	public void createTransfer(String transferTrxNo,String targetLoginName, String sourceLoginName, double transferAmount,
			String transferDesc) throws TradeBizException, AccountBizException,
			FeeBizException, RpcException;
}
