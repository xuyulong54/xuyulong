package wusc.edu.pay.facade.remit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.dao.RemitBankAreaDao;
import wusc.edu.pay.facade.remit.entity.RemitBankArea;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;


/**
 * @Title: 银行地区信息服务接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-24 下午4:38:25
 */
@Component("remitBankAreaFacade")
public class RemitBankAreaFacadeImpl implements RemitBankAreaFacade {

	@Autowired
	private RemitBankAreaDao remitBankAreaDao;

	@Override
	public long create(RemitBankArea remitBankArea) {
		return remitBankAreaDao.insert(remitBankArea);
	}

	@Override
	public long update(RemitBankArea remitBankArea) {
		return remitBankAreaDao.update(remitBankArea);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitBankAreaDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitBankArea getById(long id) {
		return remitBankAreaDao.getById(id);
	}

	@Override
	public List<Map<String, Object>> getProvince() {
		return remitBankAreaDao.getProvince();
	}

	@Override
	public List<Map<String, Object>> getCityByProvince(String province) throws RemitBizException {
		return remitBankAreaDao.getCityByProvince(province);
	}

	@Override
	public List<RemitBankArea> listByAreaCode(String areaCode) {
		return remitBankAreaDao.listByAreaCode(areaCode);
	}

}
