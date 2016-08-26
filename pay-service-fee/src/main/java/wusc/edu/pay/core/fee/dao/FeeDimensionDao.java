package wusc.edu.pay.core.fee.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeDimension;


public interface FeeDimensionDao extends BaseDao<FeeDimension> {

	/**
	 * 根据节点id、产品名、支付方式查询维度
	 * @param nodeId
	 * @param payProduct
	 * @param frpCode
	 * @return
	 */
	List<FeeDimension> getFeeDimensionbyNodeIdAndPayProductFrpCode(Long nodeId, String payProduct, String frpCode);
	/**
	 * 获取该费率节点下的所有维度集合
	 * @param nodeId
	 * @return
	 */
	List<FeeDimension> queryFeeDimensionByNodeId(Long nodeId);

}
