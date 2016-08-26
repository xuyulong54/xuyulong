package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;


public interface FeeCalculateWayDao extends BaseDao<FeeCalculateWay>{

	/**
	 * 根据费率维度ID，时间查找计算方式
	 * @param dimensionId
	 * @param dayStart
	 * @return
	 */
	List<FeeCalculateWay> queryCalculateWay(Long dimensionId, Date dayStart);

	/**
	 * 检查计费方式是否唯一
	 * @param feeCalculateWay
	 * @return
	 */
	boolean checkUnique(FeeCalculateWay feeCalculateWay);

	/**
	 * 查询维度和计费方式
	 * @param pageParam
	 * @param param
	 * @return
	 */
	PageBean listDimensionAndCalWay(PageParam pageParam,
			Map<String, Object> param);

}
