package wusc.edu.pay.core.bank.biz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.core.bank.dao.BankSettlementDao;
import wusc.edu.pay.facade.bank.entity.BankSettlement;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行结算信息管理业务逻辑层实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午6:04:48
 */
@Component("bankSettlementBiz")
public class BankSettlementBiz extends BaseBizImpl<BankSettlement> {

	@Autowired
	private BankSettlementDao bankSettlementDao;

	@Override
	protected BaseDao<BankSettlement> getDao() {
		return bankSettlementDao;
	}

	/**
	 * 获取银行渠道状态为可用(100)的银行结算信息，包括以下字段内容:<br/>
	 * "bankChannelCode:银行渠道编号"、<br/>
	 * "tradeGainCheckFileTime:业务对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "fundGainCheckFileTime:清算对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "settleCycle:结算周期：T+X"
	 * @return list.
	 */
	public List listAvailableBankSettlementInfo() {
		return bankSettlementDao.listAvailableBankSettlementInfo();
	}
    
	/**
	 * 根据银行渠道编号获取银行结算信息.
	 * @param bankChannelCode 银行渠道编号.
	 * @return BankSettlement.
	 */
	public BankSettlement getByBankBankChannelCode(String bankChannelCode){
		if(StringUtils.isBlank(bankChannelCode)){
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "银行渠道编号不能为空:%s", bankChannelCode);
		}
		return bankSettlementDao.getByBankBankChannelCode(bankChannelCode);
	}
	
	/**
	 * 获取银行渠道可用状态下的银行账号
	 * @return
	 * @throws BizException
	 */
	public List listAvailableBankAccount(){
		return bankSettlementDao.listAvailableBankAccount();
	}
}
