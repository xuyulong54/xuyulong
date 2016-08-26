/**
 * wusc.edu.pay.bank.dao.BankSettlementDao.java
 */
package wusc.edu.pay.core.bank.dao;


import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.bank.entity.BankSettlement;


/**
 * 
 * @描述: 银行结算信息管理数据访问层接口 .
 * @作者: HuPitao,WuShuicheng .
 * @创建时间: 2014-4-15, 下午6:07:17
 */
public interface BankSettlementDao extends BaseDao<BankSettlement> {
	
	/**
	 * 根据银行渠道编号获取银行结算信息.
	 * @param bankChannelCode 银行渠道编号.
	 * @return BankSettlement.
	 */
	BankSettlement getByBankBankChannelCode(String bankChannelCode);
	
	/**
	 * 获取银行渠道状态为可用(100)的银行结算信息，包括以下字段内容:<br/>
	 * "bankChannelCode:银行渠道编号"、<br/>
	 * "tradeGainCheckFileTime:业务对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "fundGainCheckFileTime:清算对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "settleCycle:结算周期：T+X"
	 * @return list.
	 */
	List listAvailableBankSettlementInfo();
	
	/**
	 * 获取银行渠道可用状态下的银行账号
	 * @return
	 * @throws BizException
	 */
	List listAvailableBankAccount();

}