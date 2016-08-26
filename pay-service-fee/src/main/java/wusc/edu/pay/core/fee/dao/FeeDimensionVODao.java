package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


public interface FeeDimensionVODao extends BaseDao<FeeDimensionVO> {

	/**
	 * 根据时间和计费项去查找符合条件的维度
	 * @param transferDate
	 * @param calFeeItem
	 * @return
	 */
	List<FeeDimensionVO> getFeeDimensionVO(Date transferDate, Integer calFeeItem);

	List<FeeDimensionVO> queryFeeDimensionVOList(Map<String, Object> map);

	PageBean queryFeeDimensionVOListPage(PageParam pageParam,
			Map<String, Object> map);

}
