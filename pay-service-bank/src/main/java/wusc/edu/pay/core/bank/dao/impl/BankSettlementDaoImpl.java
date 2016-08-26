/**
 * wusc.edu.pay.bank.dao.impl.BankSettlementDaoImpl.java
 */
package wusc.edu.pay.core.bank.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.core.bank.dao.BankSettlementDao;
import wusc.edu.pay.facade.bank.entity.BankSettlement;



/**
 * 
 * @描述: 银行结算信息管理数据访问层接口实现类 .
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午6:07:56
 */
@Repository(value = "bankSettlementDao")
public class BankSettlementDaoImpl extends BaseDaoImpl<BankSettlement> implements BankSettlementDao {

	/**
	 * 获取银行渠道状态为可用(100)的银行结算信息，包括以下字段内容:<br/>
	 * "bankChannelCode:银行渠道编号"、<br/>
	 * "tradeGainCheckFileTime:业务对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "fundGainCheckFileTime:清算对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "settleCycle:结算周期：T+X"
	 * @return list.
	 */
	@Override
	public List listAvailableBankSettlementInfo() {
		return super.getSessionTemplate().selectList("listAvailableBankSettlementInfo", null);
	}

	
	/**
	 * 根据银行渠道编号获取银行结算信息.
	 * @param bankChannelCode 银行渠道编号.
	 * @return BankSettlement.
	 */
	@Override
	public BankSettlement getByBankBankChannelCode(String bankChannelCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankChannelCode", bankChannelCode);
		return super.getSessionTemplate().selectOne("getByBankBankChannelCode", params);
	}

	/**
	 * 获取银行渠道可用状态下的银行账号
	 * @return
	 * @throws BizException
	 */
	@Override
	public List listAvailableBankAccount() {
		return super.getSessionTemplate().selectList("listAvailableBankAccount", null);
	}
	
}