package wusc.edu.pay.core.remit.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitBatch;


/**
 * @Title: 打款批次Dao接口
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:55:30
 */
public interface RemitBatchDao extends BaseDao<RemitBatch> {

	public String buildRemitBatchNo();

	public RemitBatch getByBatchNo(String batchNo);
}
