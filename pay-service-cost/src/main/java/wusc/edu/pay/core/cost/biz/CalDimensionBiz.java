package wusc.edu.pay.core.cost.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.cost.dao.CalDimensionDao;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 计费维度表业务实现类 .
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:28:52
 */
@Component("calDimensionBiz")
public class CalDimensionBiz extends BaseBizImpl<CalDimension> {

	@Autowired
	private CalDimensionDao calDimensionDao;

	protected BaseDao<CalDimension> getDao() {
		return calDimensionDao;
	}

	public long deleteById(long id) {
		return calDimensionDao.deleteById(id);
	}

	/**
	 * 增加计费维度
	 */
	public long createCalDimension(CalDimension entity) {
		List<CalDimension> entityList = getByProductNameAndCalInterface(entity.getCalProduct(), entity.getCalCostInterfaceCode());
		if (entityList.size() > 0) {
			throw CostBizException.DIMENSION_IS_EXIST;
		}
		return calDimensionDao.insert(entity);

	}

	/**
	 * 更新计费维度
	 */
	public long updateCalDimension(CalDimension entity) {
		List<CalDimension> entityList = getByProductNameAndCalInterface(entity.getCalProduct(), entity.getCalCostInterfaceCode());
		if (entityList.size() > 0) {
			throw CostBizException.DIMENSION_IS_EXIST;
		}
		return calDimensionDao.update(entity);
	}

	/**
	 * 根据计费产品名称查询计费维度
	 * 
	 * @param productName
	 * @return
	 */
	public List<CalDimension> getByProductName(String productName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productName", productName);
		return calDimensionDao.listBy(paramMap);
	}

	/**
	 * 根据计费接口编码查询计费维度
	 */
	public List<CalDimension> getByCalCostInterfaceCode(String calCostInterfaceCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("calCostInterfaceCode", calCostInterfaceCode);
		return calDimensionDao.listBy(paramMap);
	}

	/**
	 * 根据计费产品名称和银行渠道查询计费维度
	 * 
	 * @param productName
	 * @return
	 */
	public List<CalDimension> getByProductNameAndCalInterface(String productName, String calCostInterfaceCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productName", productName);
		paramMap.put("calCostInterfaceCode", calCostInterfaceCode);
		return calDimensionDao.listBy(paramMap);
	}

	/**
	 * 
	 * @return
	 */
	public List<CalDimension> listAll() {
		return calDimensionDao.listBy(new HashMap());
	}

	/**
	 * 获取计费维度表中该银行接口的相关信息
	 * 
	 * @param bankInterface
	 *            银行接口名称
	 * @return
	 */
	public List<CalDimension> listByBankInterface(String bankInterface) {
		return calDimensionDao.getCalDimensionByCalInterface(bankInterface);
	}

}