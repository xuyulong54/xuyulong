package wusc.edu.pay.facade.fee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.biz.FeeDimensionBiz;
import wusc.edu.pay.core.fee.biz.FeeNodeBiz;
import wusc.edu.pay.core.fee.biz.FeeOrderBiz;
import wusc.edu.pay.core.fee.biz.UserFeeSettingBiz;
import wusc.edu.pay.core.fee.dao.FeeOrderDao;
import wusc.edu.pay.facade.fee.dto.FeeCalWayAndDimensionDTO;
import wusc.edu.pay.facade.fee.dto.FeeModelDTO;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.facade.fee.vo.FeeDimensionBindVO;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


/**
 * 计费管理相关Facade实现类
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,下午5:22:07
 */
@Component("feeQueryFacade")
public class FeeQueryFacadeImpl implements FeeQueryFacade {
	@Autowired
	private FeeNodeBiz feeNodeBiz;
	@Autowired
	private FeeDimensionBiz feeDimensionBiz;
	@Autowired
	private FeeOrderBiz feeOrderBiz;
	@Autowired
	private UserFeeSettingBiz userFeeSettingBiz;
	@Autowired
	private FeeOrderDao feeOrderDao;

	/**
	 * list查询计费节点
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean queryFeeNodeListPage(PageParam pageParam, Map<String, Object> map) {
		return feeNodeBiz.ListPage(pageParam, map);
	}

	/**
	 * list查询计费维度
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	@Override
	public PageBean queryFeeDimensionListPage(PageParam pageParam, Map<String, Object> map) {
		return feeDimensionBiz.ListPage(pageParam, map);
	}

	/**
	 * 根据计费节点查询节点
	 */
	@Override
	public FeeNode queryFeeNodeByNodeId(Long feeNodeId) {
		return feeNodeBiz.getById(feeNodeId);
	}

	@Override
	public FeeDimension queryFeeDimensionById(Long feeDimensionId) {
		return feeDimensionBiz.queryFeeDimensionById(feeDimensionId);
	}

	/**
	 * 计费订单分页
	 */
	@Override
	public PageBean queryFeeOrderListPage(PageParam pageParam, Map<String, Object> map) {
		return feeOrderBiz.listPage(pageParam, map);
	}

	/**
	 * 根据计费订单编号查询订单详情
	 */
	@Override
	public FeeOrder queryFeeOrderById(Long id) {
		return feeOrderBiz.getById(id);
	}

	/**
	 * 根据节点ID查询维度绑定信息
	 * 
	 * @param nodeId
	 * @return
	 */
	@Override
	public List<FeeDimensionBindVO> queryFeeBindListByNodeId(Long nodeId) {
		return feeDimensionBiz.queryFeeBindListByNodeId(nodeId);
	}

	/**
	 * 根据节点ID，支付产品CODE和支付方式CODE查询费率维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @param payWayCode
	 * @return
	 */
	@Override
	public FeeDimension queryFeeDimension(Long nodeId, String payProductCode, String payWayCode) {
		return feeDimensionBiz.queryFeeDimension(nodeId, payProductCode, payWayCode);
	}

	/**
	 * 根据节点ID和产品CODE查询所有该节点该产品下的维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @return
	 */
	@Override
	public List<FeeDimension> queryFeeDimensionList(Long nodeId, String payProductCode) {
		return feeDimensionBiz.queryFeeDimensionList(nodeId, payProductCode);
	}

	/**
	 * 分页查询计费用户设置信息
	 */
	public PageBean queryUserFeeSettingList(PageParam pageParam,
			Map<String, Object> map) {
		return userFeeSettingBiz.queryUserFeeSettingList(pageParam,map);
	}

	/**
	 * 分页查询用户设置和节点的信息 
	 */
	public List<FeeModelDTO> queryUserFeeSettingAndNode(PageParam pageParam,
			Map<String, Object> map) {
		return userFeeSettingBiz.queryUserFeeSettingAndNode(pageParam, map);
	}
	
	/**
	 * 根据流水号查询计费订单
	 * @return
	 */
	public FeeOrder getFeeOrderByTrxFlowNo(String trxFlowNo){
		return feeOrderDao.getFeeOrderByTrxFlowNo(trxFlowNo);
	}
	
	/**
	 * 根据商户编号和商户订单好查询计费订单
	 */
	public FeeOrder getFeeOrderByMerchantNoAndOrderNo(String merchantNo,String orderNo){
		return feeOrderDao.getFeeOrderByMerchantNoAndOrderNo(merchantNo,orderNo);
	}

	@Override
	public List<FeeDimensionVO> queryFeeDimensionVOList(Map<String, Object> map) {
		return feeDimensionBiz.queryFeeDimensionVOList(map);
	}

	@Override
	public List<FeeDimension> queryDimensionByNodeId(Long feeNodeId) {
		return feeDimensionBiz.queryDimensionByNodeId(feeNodeId);
	}

	@Override
	public PageBean queryFeeDimensionVOListPage(PageParam pageParam,
			Map<String, Object> map) {
		return feeDimensionBiz.queryFeeDimensionVOListPage(pageParam, map);
	}

	@Override
	public PageBean queryUserFeeSettingAndNodeListPage(PageParam pageParam,
			Map<String, Object> map) {
		return userFeeSettingBiz.queryUserFeeSettingAndNodeListPage(pageParam,map);
	}

	@Override
	public FeeOrder getByTrxFlowNoAndFeeItem(String trxFlowNo, Integer feeItem)
			throws FeeBizException {
		return feeOrderDao.getByTrxFlowNoAndFeeItem(trxFlowNo, feeItem);
	}
	
	/**
	 * 根据节点id 查询其下计费方式
	 * @描述: .
	 * @作者: Along.shen .
	 * @创建时间:2014-12-24,下午4:20:18.
	 * @版本:
	 */
	public List<FeeCalWayAndDimensionDTO> queryFeeCalculateWayListByNodeId(Long feeNodeId){
		return feeDimensionBiz.queryFeeCalculateWayListByNodeId(feeNodeId);
	}

}
