package wusc.edu.pay.core.boss.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.boss.entity.ContractManagement;


/***
 * 
 * @描述: 合同管理DAO.
 * @作者: Lanzy.
 * @创建时间: 2014-4-9, 上午9:49:33 .
 * @版本: V1.0.
 *
 */
public interface ContractManagementDao extends BaseDao<ContractManagement>{
	
	/**
	 * 根据merchantId查找记录.
	 * @param merchantId .
	 * @return entity .
	 */
	List<ContractManagement> getByMerchantId(String merchantId);

	ContractManagement getByUserNo(String merchantNo);

}
