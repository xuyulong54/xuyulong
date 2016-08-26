package wusc.edu.pay.facade.remit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.dao.RemitBankTypeDao;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;


/**
 * @Title: 银行行别信息服务接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-24 下午4:37:26
 */
@Component("remitBankTypeFacade")
public class RemitBankTypeFacadeImpl implements RemitBankTypeFacade {
	@Autowired
	private RemitBankTypeDao remitBankTypeDao;

	@Override
	public long create(RemitBankType remitBankType) {
		return remitBankTypeDao.insert(remitBankType);
	}

	@Override
	public long update(RemitBankType remitBankType) {
		return remitBankTypeDao.update(remitBankType);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitBankTypeDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitBankType getById(long id) {
		return remitBankTypeDao.getById(id);
	}

	@Override
	public List<RemitBankType> listAll() {
		return remitBankTypeDao.listAll();
	}

	@Override
	public List<RemitBankType> listActiveBank() throws RemitBizException {
		return remitBankTypeDao.listActiveBank();
	}

	@Override
	public List<RemitBankType> listActiveBankByIn(List<String> bankCodeList) throws RemitBizException {
		return remitBankTypeDao.listActiveBankByIn(bankCodeList);
	}

	@Override
	public List<RemitBankType> listActiveBankByNotIn(List<String> bankCodeList) throws RemitBizException {
		return remitBankTypeDao.listActiveBankByNotIn(bankCodeList);
	}

	@Override
	public RemitBankType getByTypeCode(String typeCode) {
		return remitBankTypeDao.getByTypeCode(typeCode);
	}

	@Override
	public RemitBankType getByBankCode(String bankCode) throws RemitBizException {
		return remitBankTypeDao.getByBankCode(bankCode);
	}

}
