package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitBatchDao;
import wusc.edu.pay.facade.remit.entity.RemitBatch;


/**
 * @Title: 打款批次Dao接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:57:24
 */
@Repository("remitBatchDao")
public class RemitBatchDaoImpl extends BaseDaoImpl<RemitBatch> implements RemitBatchDao {

	@Override
	public String buildRemitBatchNo() {
		return super.getSeqNextValue("REMIT_BATCH_NO_SEQ");
	}

	public RemitBatch getByBatchNo(String batchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		return super.getBy(paramMap);
	}
}
