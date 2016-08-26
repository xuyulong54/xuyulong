package wusc.edu.pay.facade.fee.service;

import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;

/**
 * 
 * @desc 计费管理相关Facade
 * @author shenjialong
 * @date 2014-6-27,下午4:36:55
 */
public interface FeeManagerFacade {

	/**
	 * 创建节点
	 * 
	 * @param node
	 */
	long createFeeNode(FeeNode node) throws FeeBizException;

	/**
	 * 创建维度
	 * 
	 * @param feeDimension
	 */
	void createFeeDimension(FeeDimension feeDimension) throws FeeBizException;

	/**
	 * 更新维度实体
	 * 
	 * @param feeDimension
	 */
	void updateFeeDimension(FeeDimension feeDimension) throws FeeBizException;

	/**
	 * 创建用户节点设置实体
	 * 
	 * @param userFeeSetting
	 */
	void createUserFeeSetting(UserFeeSetting userFeeSetting) throws FeeBizException;

	/**
	 * 更新
	 * 
	 * @param userFeeSetting
	 */
	void updateUserSetting(UserFeeSetting userFeeSetting) throws FeeBizException;

	/**
	 * 判断是否有相同
	 * 
	 * @param node
	 * @return
	 */
	boolean checkUnique(FeeNode node) throws FeeBizException;

	/**
	 * 修改计费订单
	 * 
	 * @param feeOrder
	 * @throws FeeBizException
	 */
	public void updateFeeOrder(FeeOrder feeOrder) throws FeeBizException;
	
	/**
	 * 删除计费订单（结算特有，只有结算业务可以调用）
	 * @param trxFlowNo 流水号
	 */
	public void deleteFeeOrder(String trxFlowNo) throws FeeBizException;

}
