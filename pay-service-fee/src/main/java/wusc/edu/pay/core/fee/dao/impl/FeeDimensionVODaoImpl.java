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
import wusc.edu.pay.core.fee.dao.FeeDimensionVODao;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


@Repository("feeDimensionVODao")
public class FeeDimensionVODaoImpl extends BaseDaoImpl<FeeDimensionVO> implements FeeDimensionVODao {

	/**
	 * 根据交易开始时间查询费率维度
	 * 
	 * @param userNo
	 * @param userType
	 * @param calFeeItem
	 * @param transferDate
	 * @return
	 */
	@Override
	public List<FeeDimensionVO> getFeeDimensionVO(Date transferDate, Integer calFeeItem) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("transferDate", transferDate);
		paramMap.put("calFeeItem", calFeeItem);

		return getSessionTemplate().selectList(getStatement("getFeeDimensionVOs"), paramMap);
	}

	@Override
	public List<FeeDimensionVO> queryFeeDimensionVOList(Map<String, Object> map) {
		return getSessionTemplate().selectList(getStatement("queryFeeDimensionVOList"), map);
	}

	@Override
	public PageBean queryFeeDimensionVOListPage(PageParam pageParam, Map<String, Object> map) {

		return super.listPage(pageParam, map, "queryFeeDimensionVOListPage");

	}

}
