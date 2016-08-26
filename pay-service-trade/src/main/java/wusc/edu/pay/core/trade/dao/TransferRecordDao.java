package wusc.edu.pay.core.trade.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.trade.entity.TransferRecord;


/**
 * 
 * @描述: 转账记录表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-9,上午9:55:31 .
 * @版本: 1.0 .
 */
public interface TransferRecordDao extends BaseDao<TransferRecord> {

	TransferRecord getByTrxNo(String trxNo);

}
