package wusc.edu.pay.core.fee.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.dao.FeeCalculateWayDao;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;


@Repository("feeCalculateWayDao")
public class FeeCalculateWayDaoImpl extends BaseDaoImpl<FeeCalculateWay> implements FeeCalculateWayDao {
	/**
	 * 根据费率维度ID，时间查找计算方式
	 * 
	 * @param dimensionId
	 * @param dayStart
	 * @return
	 */
	public List<FeeCalculateWay> queryCalculateWay(Long dimensionId, Date dayStart) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dimensionId", dimensionId);
		param.put("date", dayStart);
		return super.getSqlSession().selectList(getStatement("queryCalculateWayByFeeDimensionId"), param);
	}

	@Override
	public boolean checkUnique(FeeCalculateWay feeCalculateWay) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("calculateType", feeCalculateWay.getCalculateType());
		param.put("feeDimensionId", feeCalculateWay.getFeeDimensionId());
		param.put("chargeType", feeCalculateWay.getChargeType());
		param.put("feeRole", feeCalculateWay.getFeeRole());
		param.put("billCycleType", feeCalculateWay.getBillCycleType());
		param.put("customizeBillCycleType", feeCalculateWay.getCustomizeBillCycleType());
		param.put("customizeBillDay", feeCalculateWay.getCustomizeBillDay());
		param.put("effectiveDateEnd", feeCalculateWay.getEffectiveDateEnd());
		param.put("effectiveDateStart", feeCalculateWay.getEffectiveDateStart());
		param.put("feeFreeAmount", feeCalculateWay.getFeeFreeAmount());
		List<FeeCalculateWay> list = super.getSqlSession().selectList(getStatement("checkUnique"), param);
		if (list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 分页查询
	 */
	public PageBean listDimensionAndCalWay(PageParam pageParam, Map<String, Object> param) {
		return super.listPage(pageParam, param, "listDimensionAndCalWay");
	}
}
