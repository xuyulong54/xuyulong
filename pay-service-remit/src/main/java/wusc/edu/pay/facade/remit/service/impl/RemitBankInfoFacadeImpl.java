package wusc.edu.pay.facade.remit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.dao.RemitBankInfoDao;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;


/**
 * @Title: 银行信息服务接口 实现类
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:51:38
 */
@Component("remitBankInfoFacade")
public class RemitBankInfoFacadeImpl implements RemitBankInfoFacade {

	@Autowired
	private RemitBankInfoDao remitBankInfoDao;

	@Override
	public long create(RemitBankInfo remitBankInfo) {
		return remitBankInfoDao.insert(remitBankInfo);
	}

	@Override
	public long update(RemitBankInfo remitBankInfo) {
		return remitBankInfoDao.update(remitBankInfo);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return remitBankInfoDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitBankInfo getById(long id) {
		return remitBankInfoDao.getById(id);
	}

	@Override
	public List<RemitBankInfo> listAll() {
		return remitBankInfoDao.listBy(new HashMap<String, Object>());
	}

	@Override
	public void deleteById(long id) {
		remitBankInfoDao.deleteById(id);
	}

	@Override
	public List<RemitBankInfo> listByBankTypeCodeAndArea(String bankTypeCode, String province, String city) {
		return remitBankInfoDao.listByBankTypeCodeAndArea(bankTypeCode, province, city);
	}

	@Override
	public RemitBankInfo getByBankChannelNo(String bankChannelNo) {
		return remitBankInfoDao.getByBankChannelNo(bankChannelNo);
	}

	@Override
	public List<RemitBankInfo> listByBankName(String bankName) {
		return remitBankInfoDao.listByBankName(bankName);
	}

}
