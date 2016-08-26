package wusc.edu.pay.facade.fee.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;


/**
 * 
 * Desc: 计费方式管理接口
 * 
 * @author lichao Date: 2014-7-3
 * 
 */
public interface FeeCalculateWayFacade {

	/**
	 * 根据条件查询计费方式
	 * 
	 * @param pageParam
	 * @param param
	 * @return
	 */
	PageBean queryFeeCalculateWayList(PageParam pageParam, Map<String, Object> param) throws FeeBizException;

	/**
	 * 新增计费方式
	 * 
	 * @param feeCalculateWaythrows
	 *            CalculateFeeException
	 */
	void createFeeCalculateWay(FeeCalculateWay feeCalculateWay) throws FeeBizException;

	/**
	 * 根據ID查詢計費方式
	 * 
	 * @param feeCalculateWayId
	 * @return
	 */
	FeeCalculateWay getById(Long feeCalculateWayId) throws FeeBizException;

	/**
	 * 更新
	 * 
	 * @param feeCalculateWay
	 */
	void updateFeeCalculateWay(FeeCalculateWay feeCalculateWay) throws FeeBizException;

	/**
	 * 检查是否唯一
	 * 
	 * @param feeCalculateWay
	 * @return
	 */
	boolean checkUnique(FeeCalculateWay feeCalculateWay) throws FeeBizException;

	/**
	 * 查询维度和计费方式
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean listDimensionAndCalWay(PageParam pageParam, Map<String, Object> map);

}
