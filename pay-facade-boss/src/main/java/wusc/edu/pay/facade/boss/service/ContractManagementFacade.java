package wusc.edu.pay.facade.boss.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.boss.entity.ContractManagement;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;

/***
 * 
 * @描述: 合同管理接口.
 * @作者: Lanzy.
 * @创建时间: 2014-4-9, 上午10:34:40 .
 * @版本: V1.0.
 *
 */
public interface ContractManagementFacade {
	
	public long create(ContractManagement entity) throws BossBizException;

	public long update(ContractManagement entity) throws BossBizException;

	public ContractManagement getById(long id) throws BossBizException;

	public ContractManagement getBy(Map<String, Object> map) throws BossBizException;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BossBizException;
	
	public List<ContractManagement> getByMerchantId(String merchantId) throws BossBizException;

	public ContractManagement getByUserNo(String merchantNo);

}
