package wusc.edu.pay.core.fee.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeePrepaidFlowInfoDao;
import wusc.edu.pay.facade.fee.entity.FeePrepaidFlowInfo;


@Repository("feePrepaidFlowInfoDao")
public class FeePrepaidFlowInfoDaoImpl extends BaseDaoImpl<FeePrepaidFlowInfo> implements
FeePrepaidFlowInfoDao {
	/**
	 * 根据支付方式id 查找费率包量包笔信息
	 * @param wayId
	 * @return
	 */
	public FeePrepaidFlowInfo getFlowInfoByWayId(Long wayId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("feeCalWayId", wayId);
		return this.getBy(map);
	}
}
