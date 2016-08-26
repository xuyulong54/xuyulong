package wusc.edu.pay.facade.fee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.fee.biz.FeeDimensionBiz;
import wusc.edu.pay.core.fee.biz.FeeNodeBiz;
import wusc.edu.pay.core.fee.biz.FeeOrderBiz;
import wusc.edu.pay.core.fee.biz.UserFeeSettingBiz;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;


/**
 * 计费管理相关Facade实现类
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,下午5:22:07
 */
@Component("feeManagerFacade")
public class FeeManagerFacadeImpl implements FeeManagerFacade {
	@Autowired
	private FeeNodeBiz feeNodeBiz;
	@Autowired
	private FeeDimensionBiz feeDimensionBiz;
	@Autowired
	private UserFeeSettingBiz userFeeSettingBiz;
	@Autowired
	private FeeOrderBiz feeOrderBiz;

	/**
	 * 创建节点
	 * 
	 * @param node
	 */
	public long createFeeNode(FeeNode node) {
		return feeNodeBiz.createFeeNode(node);
	}

	/**
	 * 创建维度
	 */
	@Override
	public void createFeeDimension(FeeDimension feeDimension) {
		feeDimensionBiz.createFeeDimension(feeDimension);
	}

	/**
	 * 修改维度
	 */
	@Override
	public void updateFeeDimension(FeeDimension feeDimension) {
		feeDimensionBiz.updateFeeDimension(feeDimension);
	}

	@Override
	public void createUserFeeSetting(UserFeeSetting userFeeSetting) {
		userFeeSettingBiz.createUserFeeSetting(userFeeSetting);
	}

	@Override
	public void updateUserSetting(UserFeeSetting userFeeSetting) {
		userFeeSettingBiz.updateUserSetting(userFeeSetting);
		
	}


	@Override
	public boolean checkUnique(FeeNode node) {
		return feeNodeBiz.checkUnique(node);
	}

	
	/**
	 * 修改计费订单
	 * @param feeOrder
	 * @throws FeeBizException
	 */
	public void updateFeeOrder(FeeOrder feeOrder){
		feeOrderBiz.updateFeeOrder(feeOrder);
	}
	
	/**
	 * 删除计费订单（结算特有，只有结算业务可以调用）
	 * @param trxFlowNo 流水号
	 */
	public void deleteFeeOrder(String trxFlowNo){
		feeOrderBiz.deleteFeeOrder(trxFlowNo);
	} 
	

}
