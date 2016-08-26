package wusc.edu.pay.core.fee.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeFlowAccumulatorDao;
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;


@Repository("feeFlowAccumulatorDao")
public class FeeFlowAccumulatorDaoImpl extends BaseDaoImpl<FeeFlowAccumulator> implements FeeFlowAccumulatorDao {
	// 根据计费方式id和时间得到流量累加值
	public List<FeeFlowAccumulator> getFlowAccumulatorByTransferDate(Long calWayId, String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("calculateWayId", calWayId);
		map.put("userNo", userNo);
		return this.listBy(map);
	}
	
	/**
	 * 根据商户编号，计费方式id，创建时间，查询出流量统计
	 * @param calculateWayId
	 * @param userNo
	 * @param createTime
	 * @return
	 */
	public FeeFlowAccumulator getFlowAccumulatorByWayId_UserNo_Time(Long calculateWayId, String userNo, Date createTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("calculateWayId", calculateWayId);
		map.put("userNo", userNo);
		map.put("intervalTime", createTime);
		return this.getBy(map);
	}
}
