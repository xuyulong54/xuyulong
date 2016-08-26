package wusc.edu.pay.core.boss.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.boss.dao.MerchantSalesDao;
import wusc.edu.pay.facade.boss.entity.MerchantSales;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;


/***
 * 商户与销售人员管理表
 * @author huangbin
 *
 */
@Component("merchantSalesBiz")
public class MerchantSalesBiz {
	@Autowired
	private MerchantSalesDao merchantSalesDao;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	
	/***
	 * 新增销售人员与商户信息关联表
	 * @param sales
	 * @return
	 */
	public long createMerchantSales(MerchantSales sales){
		return merchantSalesDao.insert(sales);
	}
	
	/***
	 * 更新
	 * @param sales
	 * @return
	 */
	public long updateMerchantSales(MerchantSales sales){
		long result = merchantSalesDao.update(sales);
		//如果商户状态为103时，修改结算规则要修改商户状态
		MerchantOnline merchant = merchantOnlineFacade.getMerchantOnlineByMerchantNo(sales.getMerchantNo());
		if(merchant != null && merchant.getStatus()==MerchantStatusEnum.NOPASS.getValue()){
			merchant.setStatus(MerchantStatusEnum.CREATED.getValue());
			merchantOnlineFacade.update(merchant);
		}
		return result;
	}

	public MerchantSales getById(Long id) {
		return merchantSalesDao.getById(id);
	}

	
	public MerchantSales getByMerchantNo(String merchantNo) {
		return merchantSalesDao.getByMerchantNo(merchantNo);
	}

	public void deteleById(Long sId) {
		merchantSalesDao.deleteById(sId);
	}

	public List<MerchantSales> listByCondition(Map<String, Object> paramMap) {
		return merchantSalesDao.listByCondition(paramMap);
	}
}
