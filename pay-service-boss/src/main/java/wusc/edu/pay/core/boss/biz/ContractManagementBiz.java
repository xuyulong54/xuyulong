package wusc.edu.pay.core.boss.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.dao.ContractManagementDao;
import wusc.edu.pay.facade.boss.entity.ContractManagement;


/***
 * 
 * @描述: 合同管理BIZ.
 * @作者: Lanzy.
 * @创建时间: 2014-4-9, 上午10:23:33 .
 * @版本: V1.0.
 *
 */
@Component("contractManagementBiz")
public class ContractManagementBiz {
	
	@Autowired
	private ContractManagementDao contractManagementDao;
	/**
	 * 事务控制:
	 * 上传文件和插入数据
	 */
	public long create(ContractManagement contractManagement) {
		return contractManagementDao.insert(contractManagement);
	}

	public long update(ContractManagement entity) {
		return contractManagementDao.update(entity);
	}

	public ContractManagement getById(long id) {
		return contractManagementDao.getById(id);
	}

	public ContractManagement getBy(Map<String, Object> map) {
		return contractManagementDao.getBy(map);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return contractManagementDao.listPage(pageParam, paramMap);
	}
	
	public List<ContractManagement> getByMerchantId(String merchantId){
		return contractManagementDao.getByMerchantId(merchantId);
	}

	public ContractManagement getByUserNo(String merchantNo) {
		return contractManagementDao.getByUserNo(merchantNo);
	}

}
