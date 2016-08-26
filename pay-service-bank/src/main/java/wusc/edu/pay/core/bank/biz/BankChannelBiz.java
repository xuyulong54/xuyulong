package wusc.edu.pay.core.bank.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.bank.dao.BankChannelDao;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


@Component("bankChannelBiz")
public class BankChannelBiz extends BaseBizImpl<BankChannel> {

	@Autowired
	private BankChannelDao bankChannelDao;

	@Override
	protected BaseDao<BankChannel> getDao() {
		return bankChannelDao;
	}

	public BankChannel getByBankChannelCode(String bankChannelCode) {
		return bankChannelDao.getByBankChannelCode(bankChannelCode);
	}

	public void deleteChannelByCode(String bankChannelCode) {
		bankChannelDao.deleteChannelByCode(bankChannelCode);
	}

	public BankChannel getByBankChannelName(String channelName) {
		return bankChannelDao.getByBankChannelName(channelName);
	}
	
	public List<BankChannel> listBy(Map<String, Object> paramMap){
		return bankChannelDao.listBy(paramMap);
	}
	/**
	 * 根据银行协议ID获取银行渠道
	 * */
	public BankChannel getByBankAgreementId(long bankAgreementId){
		if (bankAgreementId <= 0) {
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "银行协议ID不能为空:%s", bankAgreementId);
		}
		return bankChannelDao.getByBankAgreementId(bankAgreementId);
	}
	
	/**
	 * 根据协议表中的业务类型和账户表中的账户性质查询出对应的渠道
	 * @param linkType
	 */
	public List<BankChannel> listChannalByAgreementBusTypeAndAccountType(int linkType , int accountType){
		return bankChannelDao.listChannalByAgreementBusTypeAndAccountType(linkType , accountType);
	}
	
	public boolean isUsableBankChannel(String bankChannelCode){
		if(StringUtils.isBlank(bankChannelCode)){
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "银行渠道编号不能为空:%s", bankChannelCode);
		}
		return bankChannelDao.isUsableBankChannel(bankChannelCode);
	}
	
	/**
	 * 根据银行渠道编号模糊查找 
	 * @param bankChannelCode .
	 * @param status .
	 * @return List .
	 */
	public List likeBy(String bankChannelCode, Integer status){
		return bankChannelDao.likeBy(bankChannelCode, status);
	}
}
