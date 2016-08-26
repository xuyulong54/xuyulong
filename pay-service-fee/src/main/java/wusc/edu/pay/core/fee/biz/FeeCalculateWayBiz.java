package wusc.edu.pay.core.fee.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.dao.FeeCalculateWayDao;
import wusc.edu.pay.core.fee.dao.FeeDimensionDao;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeDimension;

/**
 * 
 * Desc: 费率计费方式管理
 * @author lichao
 * Date: 2014-7-3
 *
 */
@Component("feeCalculateWayBiz")
public class FeeCalculateWayBiz {
	@Autowired
	private FeeCalculateWayDao feeCalculateWayDao;
	@Autowired
	private FeeDimensionDao feeDimensionDao;
	
	/**
	 * 根据查询条件计费方式LIST
	 * @param pageParam
	 * @param param
	 * @return
	 */
	public PageBean queryFeeCalculateWayList(PageParam pageParam,
			Map<String, Object> param) {
		PageBean listPageBean = feeCalculateWayDao.listPage(pageParam, param);
		PageBean pageBean = new PageBean();
		List<Object> recordList = new ArrayList<Object>(); 
		for(Object obj : listPageBean.getRecordList()){
			FeeCalculateWay feeCalculateWay = (FeeCalculateWay)obj;
			FeeDimension feeDimension = feeDimensionDao.getById(feeCalculateWay.getFeeDimensionId());
			if(feeDimension != null){
				feeCalculateWay.setProductName(feeDimension.getPayProductName());
				recordList.add(feeCalculateWay);
			}
		}
		pageBean.setRecordList(recordList);
		pageBean.setTotalCount(listPageBean.getTotalCount());
		pageBean.setPageCount(listPageBean.getPageCount());
		pageBean.setNumPerPage(listPageBean.getNumPerPage());
		pageBean.setCurrentPage(listPageBean.getCurrentPage());
		pageBean.setCountResultMap(listPageBean.getCountResultMap());
		pageBean.setBeginPageIndex(listPageBean.getBeginPageIndex());
		return pageBean;
	}
	
	/**
	 * 新增计费方式
	 * @param feeCalculateWay
	 */
	public void createFeeCalculateWay(FeeCalculateWay feeCalculateWay) {
		feeCalculateWayDao.insert(feeCalculateWay);
	}

	/**
	 * 根據ID查找計費方式
	 * @param feeCalculateWayId
	 * @return
	 */
	public FeeCalculateWay getById(Long feeCalculateWayId) {
		return feeCalculateWayDao.getById(feeCalculateWayId);
	}

	/**
	 * 更新
	 * @param feeCalculateWay
	 */
	public void updateFeeCalculateWay(FeeCalculateWay feeCalculateWay) {
		feeCalculateWayDao.update(feeCalculateWay);
	}

	/**
	 * 检查计费方式是否唯一
	 * @param feeCalculateWay
	 * @return 
	 */
	public boolean checkUnique(FeeCalculateWay feeCalculateWay) {
		return feeCalculateWayDao.checkUnique(feeCalculateWay);
	}

	/**
	 * 查询计费方式和维度
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean listDimensionAndCalWay(PageParam pageParam,
			Map<String, Object> map) {
		return feeCalculateWayDao.listDimensionAndCalWay(pageParam, map);
	}

}
