package wusc.edu.pay.facade.fee.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.dto.FeeCalWayAndDimensionDTO;
import wusc.edu.pay.facade.fee.dto.FeeModelDTO;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.vo.FeeDimensionBindVO;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


/**
 * 
 * @desc 计费查询相关Facade
 * @author shenjialong
 * @date 2014-6-27,下午4:36:55
 */
public interface FeeQueryFacade {

	/**
	 * list查询计费节点
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean queryFeeNodeListPage(PageParam pageParam, Map<String, Object> map) throws FeeBizException;

	/**
	 * list查询计费维度
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean queryFeeDimensionListPage(PageParam pageParam, Map<String, Object> map) throws FeeBizException;

	/**
	 * 根据计费节点ID查询该计费节点
	 * 
	 * @param feeNodeId
	 * @return
	 */
	FeeNode queryFeeNodeByNodeId(Long feeNodeId) throws FeeBizException;

	/**
	 * 根据维度ID查询该维度实体信息
	 * 
	 * @param feeDimensionId
	 * @return
	 */
	FeeDimension queryFeeDimensionById(Long feeDimensionId) throws FeeBizException;

	/**
	 * 查询计费订单分页
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean queryFeeOrderListPage(PageParam pageParam, Map<String, Object> map) throws FeeBizException;

	/**
	 * 根据ID查询计费订单详情
	 * 
	 * @param id
	 * @return
	 */
	FeeOrder queryFeeOrderById(Long id) throws FeeBizException;

	/**
	 * 根据节点ID查询维度绑定信息
	 * 
	 * @param nodeId
	 * @return
	 */
	List<FeeDimensionBindVO> queryFeeBindListByNodeId(Long nodeId) throws FeeBizException;

	/**
	 * 根据节点ID，支付产品CODE和支付方式CODE查询费率维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @param payWayCode
	 * @return
	 */
	FeeDimension queryFeeDimension(Long nodeId, String payProductCode, String payWayCode) throws FeeBizException;

	/**
	 * 根据节点ID和产品CODE查询所有该节点该产品下的维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @return
	 */
	List<FeeDimension> queryFeeDimensionList(Long nodeId, String payProductCode) throws FeeBizException;
	
	/**
	 * 查询用户节点设置列表
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean queryUserFeeSettingList(PageParam pageParam, Map<String, Object> map) throws FeeBizException;

	/**
	 * 查询用户设置节点详情
	 * @param pageParam
	 * @param map
	 * @return
	 */
	List<FeeModelDTO> queryUserFeeSettingAndNode(PageParam pageParam,
			Map<String, Object> map) throws FeeBizException;
	
	
	/**
	 * 根据流水号查询计费订单
	 * @return
	 */
	public FeeOrder getFeeOrderByTrxFlowNo(String trxFlowNo) throws FeeBizException;
	
	/**
	 * 根据商户编号和商户订单好查询计费订单
	 */
	public FeeOrder getFeeOrderByMerchantNoAndOrderNo(String merchantNo,String orderNo) throws FeeBizException;

	/**
	 * 根據条件查询维度vo
	 * @param map
	 * @return
	 */
	List<FeeDimensionVO> queryFeeDimensionVOList(Map<String, Object> map);

	/**
	 * 根据节点ID查询维度
	 * @param feeNodeId
	 * @return
	 */
	List<FeeDimension> queryDimensionByNodeId(Long feeNodeId);
	
	/**
	 * 条件查询计费维度VO
	 * @param pageParam
	 * @param map
	 * @return
	 */
	PageBean queryFeeDimensionVOListPage(PageParam pageParam,
			Map<String, Object> map);

	PageBean queryUserFeeSettingAndNodeListPage(PageParam pageParam,
			Map<String, Object> map);
	
	/**
	 * 根据支付流水号和计费项查询计费订单
	 * @param trxFlowNo
	 * @param feeItem
	 * @return
	 * @throws FeeBizException
	 */
	public FeeOrder getByTrxFlowNoAndFeeItem(String trxFlowNo, Integer feeItem) throws FeeBizException;

	/**
	 * 根据节点id 查询其下计费方式
	 * @描述: .
	 * @作者: Along.shen .
	 * @创建时间:2014-12-24,下午4:20:18.
	 * @版本:
	 */
	public List<FeeCalWayAndDimensionDTO> queryFeeCalculateWayListByNodeId(Long feeNodeId) throws FeeBizException;
	
}
