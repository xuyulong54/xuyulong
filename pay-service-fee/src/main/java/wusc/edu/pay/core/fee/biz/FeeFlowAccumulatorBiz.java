package wusc.edu.pay.core.fee.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.fee.dao.FeeFlowAccumulatorDao;
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;


/**
 * 流量累加器biz定义
 * 
 */
@Component("feeFlowAccumulatorBiz")
public class FeeFlowAccumulatorBiz {

	@Autowired
	private FeeFlowAccumulatorDao feeFlowAccumulatorDao;

	// 根据计费方式id和时间得到流量累加值
	public List<FeeFlowAccumulator> getFlowAccumulator(Long calWayId, String userNo) {
		return feeFlowAccumulatorDao.getFlowAccumulatorByTransferDate(calWayId, userNo);
	}

	/**
	 * 修改流量累加
	 * 
	 * @param feeFlowAccumulator
	 */
	public void update(FeeFlowAccumulator feeFlowAccumulator) {
		feeFlowAccumulatorDao.update(feeFlowAccumulator);
	}

	/**
	 * 创建流量累加
	 * 
	 * @param feeFlowAccumulator
	 */
	public void create(FeeFlowAccumulator feeFlowAccumulator) {
		feeFlowAccumulatorDao.insert(feeFlowAccumulator);
	}

	/**
	 * 根据商户编号，计费方式id，创建时间，查询出流量统计
	 * @param calculateWayId
	 * @param userNo
	 * @param createTime
	 * @return
	 */
	public FeeFlowAccumulator getFlowAccumulatorByWayId_UserNo_Time(Long calculateWayId, String userNo, Date createTime) {
		return feeFlowAccumulatorDao.getFlowAccumulatorByWayId_UserNo_Time(calculateWayId, userNo, createTime);
	}

}
