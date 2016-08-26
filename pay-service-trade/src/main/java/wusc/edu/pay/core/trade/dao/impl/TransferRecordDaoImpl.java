package wusc.edu.pay.core.trade.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.trade.dao.TransferRecordDao;
import wusc.edu.pay.facade.trade.entity.TransferRecord;


/**
 * 
 * @描述: 转账记录表数据访问层接口实现类 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-9,上午9:54:53 .
 * @版本: 1.0 .
 */
@Repository("transferRecordDao")
public class TransferRecordDaoImpl extends BaseDaoImpl<TransferRecord> implements TransferRecordDao {

	@Override
	public TransferRecord getByTrxNo(String trxNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("trxNo", trxNo);
		return super.getBy(params);
	}

}
