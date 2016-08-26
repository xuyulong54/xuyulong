package wusc.edu.pay.core.fee.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.core.fee.dao.FeeCalculateWayDao;
import wusc.edu.pay.core.fee.dao.FeeDimensionDao;
import wusc.edu.pay.core.fee.dao.FeeDimensionVODao;
import wusc.edu.pay.facade.fee.dto.FeeCalWayAndDimensionDTO;
import wusc.edu.pay.facade.fee.dto.FeeRuleDTO;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.vo.FeeDimensionBindVO;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;
import wusc.edu.pay.facade.fee.vo.PayWayVO;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;


/**
 * 计费维度biz
 * 
 */
@Component("feeDimensionBiz")
public class FeeDimensionBiz {
	private static final Log log = LogFactory.getLog(FeeDimensionBiz.class);
	@Autowired
	private FeeDimensionVODao feeDimensionVODao;
	@Autowired
	private FeeFormulaBiz feeFormulaBiz;
	@Autowired
	private FeeDimensionDao feeDimensionDao;
	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private PayProductFacade payProductFacade;
	@Autowired
	private FeeCalculateWayDao feeCalculateWayDao;

	/**
	 * 关联查询 获取维度VO
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param transferDate
	 *            交易发起时间
	 * @return
	 */
	public List<FeeDimensionVO> findDimensionVos(Date transferDate, Integer calFeeItem) {
		List<FeeDimensionVO> listDimensions = feeDimensionVODao.getFeeDimensionVO(transferDate, calFeeItem);
		return listDimensions;
	}

	/**
	 * 获取计费维度
	 * 
	 * @param dimensions
	 *            计费维度集合
	 * @param userSet
	 *            用户设置集合
	 * @param calFeeItem
	 *            收付项 《参考 CalculateFeeItemEnum 枚举》
	 * @param payProduct
	 *            支付产品
	 * @param frpCode
	 *            支付方式
	 * @param transferDate
	 *            支付时间
	 * @return
	 */
	public FeeDimensionVO getFeeDimensionVo(List<UserFeeSetting> userSetList, List<FeeDimensionVO> dimensionList, String payProduct, String frpCode, Date transferDate) {

		// 查找适合维度
		List<FeeDimensionVO> dimensions = this.findFeeDimensionVOs(userSetList, dimensionList, payProduct, frpCode, transferDate);

		if (dimensions == null || dimensions.isEmpty()) {
			throw new FeeBizException(FeeBizException.FEE_DIMENSION_NOT_EXIST, "支付产品" + payProduct + "、支付方式为" + frpCode + "的维度不存在");
		}
		// 此处先取第一条 具体还要处理（原则上只会有一条符合条件）
		return dimensions.get(0);
	}

	/**
	 * 获取维度
	 * 
	 * @param userNo
	 * @param userType
	 * @param calFeeItem
	 * @param payProduct
	 * @param frpCode
	 * @param transferDate
	 * @return
	 */
	private List<FeeDimensionVO> findFeeDimensionVOs(List<UserFeeSetting> userSetList, List<FeeDimensionVO> dimensionList, String payProduct, String frpCode, Date transferDate) {

		List<FeeDimensionVO> dimensions = new ArrayList<FeeDimensionVO>();
		// 过滤掉dimensionList中nodeId和userSetList中nodeId不一致的维度

		log.info("===总共维度条数" + dimensionList.size() + "======");
		for (UserFeeSetting userSetting : userSetList) {

			Iterator<FeeDimensionVO> iter = dimensionList.iterator();
			while (iter.hasNext()) {
				FeeDimensionVO feeDimension = iter.next();
				if (feeDimension.getNodeId() == userSetting.getFeeNodeId()) {
					// 过滤掉payProduct,frpCode 不相应的
					if (feeDimension.getPayProduct().equals(payProduct) && feeDimension.getFrpCode().equals(frpCode)) {
						dimensions.add(feeDimension);
					} else {
						log.info("支付产品编号：" + feeDimension.getPayProduct() + "，支付方式" + feeDimension.getFrpCode() + "的维度与所需的【" + payProduct + "--" + frpCode + "】不匹配！");
					}
				} else {
					log.info("支付产品编号：" + feeDimension.getPayProduct() + "，支付方式" + feeDimension.getFrpCode() + "，节点名称" + feeDimension.getNodeName() + "(" + feeDimension.getNodeId() + ")的维度，与用户绑定的节点id" + userSetting.getFeeNodeId() + "不匹配！");
				}
			}
		}

		List<FeeDimensionVO> dimensionTemp = new ArrayList<FeeDimensionVO>();
		log.info("======剩余计费维度条数:" + dimensions.size());
		// 判断维度是否为空
		if (dimensions == null || dimensions.isEmpty()) {
			throw new FeeBizException(FeeBizException.FEE_DIMENSION_NOT_EXIST, String.format("用户编号为%s,用户类型为%s,的维度不存在", userSetList.get(0).getUserNo(), userSetList.get(0).getUserType()));
		} else {

			for (FeeDimensionVO vo : dimensions) {
				// 根据维度去查找计费方式和计费公式（rule里面保存的是方式和该方式下面的公式）
				List<FeeRuleDTO> rules = feeFormulaBiz.getFormulaInfo(vo, transferDate);
				if (!rules.isEmpty()) {
					vo.setFeeRuleList(rules);
					dimensionTemp.add(vo);
				}
			}
		}
		return dimensionTemp;
	}

	/*
	 * private boolean isRuleIllegal(List<FeeRuleDTO> rules,
	 * List<FeeCalculateWay> calWays) { if (null == rules || rules.isEmpty()) {
	 * throw new
	 * IllegalArgumentException("IllegalArgumentException, FeeRuleDTOs is empty!"
	 * ); } if (calWays.size() >= 2) { return false; } for (FeeRuleDTO rule :
	 * rules) { for (FeeCalculateWay way : calWays) { if
	 * (!rule.getEffectiveStart().after(way.getEffectiveDateEnd())) { return
	 * false; } } } return true; }
	 */
	/**
	 * 费率维度列表
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean ListPage(PageParam pageParam, Map<String, Object> map) {
		return feeDimensionDao.listPage(pageParam, map);
	}

	/**
	 * 根据维度ID查询维度
	 * 
	 * @param feeDimensionId
	 * @return
	 */
	public FeeDimension queryFeeDimensionById(Long feeDimensionId) {
		return feeDimensionDao.getById(feeDimensionId);
	}

	/**
	 * 创建费率维度
	 * 
	 * @param feeDimension
	 */
	public void createFeeDimension(FeeDimension feeDimension) {
		feeDimensionDao.insert(feeDimension);
	}

	/**
	 * 修改维度
	 * 
	 * @param feeDimension
	 */
	public void updateFeeDimension(FeeDimension feeDimension) {
		feeDimensionDao.update(feeDimension);
	}

	/**
	 * 根据节点ID查询维度绑定信息
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<FeeDimensionBindVO> queryFeeBindListByNodeId(Long nodeId) {
		// 得到所有的产品
		List<PayProduct> listPayProducts = payProductFacade.listAllProduct();
		// 得到所有的支付方式
		List<PayWay> listPayWays = payWayFacade.listAllPayWay();
		// 将查到的该节点下的维度放入map中供组装用
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("feeNodeId", nodeId);
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		// 根据节点ID查询该节点下的维度
		List<FeeDimension> listDimensions = feeDimensionDao.listBy(paramMap);
		// 把节点下面的维度和相应的信息保存到hashMap中
		Map<String, FeeDimension> tempMap = new HashMap<String, FeeDimension>();
		for (FeeDimension feeDimension : listDimensions) {
			tempMap.put(feeDimension.getFeeNodeId() + feeDimension.getPayProduct() + feeDimension.getFrpCode(), feeDimension);
		}
		// 组装
		List<FeeDimensionBindVO> listFeeDimensionVOs = new ArrayList<FeeDimensionBindVO>();
		for (PayProduct payProduct : listPayProducts) {
			FeeDimensionBindVO bindVO = new FeeDimensionBindVO();
			// 组装VO
			bindVO.setId(payProduct.getId());
			bindVO.setPayProductCode(payProduct.getPayProductCode());
			bindVO.setPayProductName(payProduct.getPayProductName());
			// 挑出支付方式
			int selectedWayCount = 0;
			List<PayWayVO> listPayWaysTemp = new ArrayList<PayWayVO>();
			for (PayWay payWay : listPayWays) {
				if (payWay.getPayProductCode().equals(payProduct.getPayProductCode())) {
					PayWayVO payWayVO = new PayWayVO();
					BeanUtils.copyProperties(payWay, payWayVO);
					// 如果该维度的支付产品编号和支付方式编号相符，那么该维度选中为true
					String strKey = nodeId + payWay.getPayProductCode() + payWay.getPayWayCode();
					payWayVO.setSelected(false);
					if (tempMap.get(strKey) != null) {
						payWayVO.setSelected(true);
						selectedWayCount++;
					}
					listPayWaysTemp.add(payWayVO);
				}
			}
			// 如果有已经选中的支付方式，那么支付产品也选中，否则不选中
			if (selectedWayCount > 0) {
				bindVO.setSelected(true);
			} else {
				bindVO.setSelected(false);
			}
			bindVO.setListPayWay(listPayWaysTemp);
			listFeeDimensionVOs.add(bindVO);
		}

		return listFeeDimensionVOs;
	}

	/**
	 * 根据节点ID，支付产品CODE和支付方式CODE查询费率维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @param payWayCode
	 * @return
	 */
	public FeeDimension queryFeeDimension(Long nodeId, String payProductCode, String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("feeNodeId", nodeId);
		paramMap.put("payProduct", payProductCode);
		paramMap.put("frpCode", payWayCode);
		List<FeeDimension> listFeeDimensions = feeDimensionDao.listBy(paramMap);
		if (listFeeDimensions != null && listFeeDimensions.size() > 0) {
			return listFeeDimensions.get(0);
		}
		return null;
	}

	/**
	 * 根据节点ID和产品CODE查询所有该节点该产品下的维度
	 * 
	 * @param nodeId
	 * @param payProductCode
	 * @return
	 */
	public List<FeeDimension> queryFeeDimensionList(Long nodeId, String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("feeNodeId", nodeId);
		paramMap.put("payProduct", payProductCode);
		List<FeeDimension> listFeeDimensions = feeDimensionDao.listBy(paramMap);
		return listFeeDimensions;
	}

	/**
	 * 根据条件查询维度VO
	 * 
	 * @param map
	 * @return
	 */
	public List<FeeDimensionVO> queryFeeDimensionVOList(Map<String, Object> map) {
		return feeDimensionVODao.queryFeeDimensionVOList(map);
	}

	public List<FeeDimension> queryDimensionByNodeId(Long feeNodeId) {
		return feeDimensionDao.queryFeeDimensionByNodeId(feeNodeId);
	}

	public PageBean queryFeeDimensionVOListPage(PageParam pageParam, Map<String, Object> map) {
		return feeDimensionVODao.queryFeeDimensionVOListPage(pageParam, map);
	}

	/**
	 * 
	 * @描述: 根据节点id 查询其下计费方式.
	 * @作者: Along.shen .
	 * @创建时间:2014-12-24,下午4:21:50.
	 * @版本:
	 */
	public List<FeeCalWayAndDimensionDTO> queryFeeCalculateWayListByNodeId(Long feeNodeId) {
		List<FeeCalWayAndDimensionDTO> feeCalWayAndDimensionList = new ArrayList<FeeCalWayAndDimensionDTO>();
		List<FeeDimension> feeDimensionList = this.queryDimensionByNodeId(feeNodeId);
		if (feeDimensionList == null || feeDimensionList.size() <= 0) {
			return null;
		}
		for (FeeDimension dimension : feeDimensionList) {
			// set 维度信息
			String payProductName = dimension.getPayProductName();
			String payProduct = dimension.getPayProduct();
			String bankChannelCode = dimension.getBankChannelCode();
			String frpCode = dimension.getFrpCode();
			Long dimensionId = dimension.getId();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("feeDimensionId", dimensionId);
			List<FeeCalculateWay> feeCalculateWayList = feeCalculateWayDao.listBy(paramMap);
			if (feeCalculateWayList == null || feeCalculateWayList.size() <= 0) {
				continue;
			}
			for (FeeCalculateWay way : feeCalculateWayList) {
				FeeCalWayAndDimensionDTO dto = new FeeCalWayAndDimensionDTO();
				dto.setBankChannelCode(bankChannelCode);
				dto.setPayProduct(payProduct);
				dto.setPayProductName(payProductName);
				dto.setFrpCode(frpCode);
				dto.setFeeDimensionId(dimensionId);
				dto.setCalculateWayId(way.getId());
				dto.setCalculateType(way.getCalculateType());
				dto.setChargeType(way.getChargeType());
				dto.setFeeFreeAmount(way.getFeeFreeAmount());
				dto.setIsRefundFee(way.getIsRefundFee());
				dto.setEffectiveDateStart(way.getEffectiveDateStart());
				dto.setEffectiveDateEnd(way.getEffectiveDateEnd());
				dto.setIsDelete(way.getIsDelete());
				feeCalWayAndDimensionList.add(dto);
			}
		}

		if (feeCalWayAndDimensionList.size() <= 0) {
			return null;
		} else {
			return feeCalWayAndDimensionList;
		}
	}
}
