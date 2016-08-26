package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;


public interface FeeFlowAccumulatorDao extends BaseDao<FeeFlowAccumulator>{

	//根据计费方式id和时间得到流量累加值
	List<FeeFlowAccumulator> getFlowAccumulatorByTransferDate(Long calWayId, String userNo);

	/**
	 * 根据商户编号，计费方式id，创建时间，查询出流量统计
	 * @param calculateWayId
	 * @param userNo
	 * @param createTime
	 * @return
	 */
	FeeFlowAccumulator getFlowAccumulatorByWayId_UserNo_Time(Long calculateWayId, String userNo, Date createTime);

}
