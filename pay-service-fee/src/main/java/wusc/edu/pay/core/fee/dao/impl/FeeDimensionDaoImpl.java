package wusc.edu.pay.core.fee.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeDimensionDao;
import wusc.edu.pay.facade.fee.entity.FeeDimension;


@Repository("feeDimensionDao")
public class FeeDimensionDaoImpl extends BaseDaoImpl<FeeDimension> implements FeeDimensionDao {
	/**
	 * 根据节点id、产品名、支付方式查询维度
	 * @param nodeId
	 * @param payProduct
	 * @param frpCode
	 * @return
	 */
	public List<FeeDimension> getFeeDimensionbyNodeIdAndPayProductFrpCode(Long nodeId, String payProduct, String frpCode){
		Map <String , Object> map = new HashMap<String, Object>();
		map.put("feeNodeId", nodeId);
		map.put("payProduct", payProduct);
		map.put("frpCode", frpCode);
		return this.listBy(map);
	}

	@Override
	public List<FeeDimension> queryFeeDimensionByNodeId(Long nodeId) {
		Map <String , Object> map = new HashMap<String, Object>();
		map.put("feeNodeId", nodeId);
		return this.listBy(map);
	}
}
