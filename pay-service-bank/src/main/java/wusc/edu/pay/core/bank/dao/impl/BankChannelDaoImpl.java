/**
 * wusc.edu.pay.bank.dao.impl.BankChannelDaoImpl.java
 */
package wusc.edu.pay.core.bank.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.bank.dao.BankChannelDao;
import wusc.edu.pay.facade.bank.entity.BankChannel;


/**
 * @author System
 * 
 * @since 2013-11-07
 */
@Repository(value="bankChannelDao")
public class BankChannelDaoImpl extends BaseDaoImpl<BankChannel> implements BankChannelDao {
	/**
	 * 根据银行渠道编号查找 
	 * @param bankChannelCode .
	 * @return BankChannel .
	 */
	public BankChannel getByBankChannelCode(String bankChannelCode){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankChannelCode", bankChannelCode);
		return super.getBy(params);
	}
	
	/**
	 * 根据银行渠道编号模糊查找 
	 * @param bankChannelCode .
	 * @param status .
	 * @return List .
	 */
	public List likeBy(String bankChannelCode, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankChannelCode2", bankChannelCode);
		params.put("status", status);
		return super.getSessionTemplate().selectList(getStatement("likeBy"), params);
	}
	
	/**
	 * 删除银行渠道 .
	 * 
	 * @param bankChannelCode .
	 */
    public void deleteChannelByCode(String bankChannelCode){
    	super.getSessionTemplate().delete(getStatement("deleteChannelByCode"), bankChannelCode);
    }

    /***
	 * 根据银行渠道名称查询银行渠道信息
	 * @param channelName
	 * @return
	 */
	public BankChannel getByBankChannelName(String channelName) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelName", channelName);
		return super.getBy(params);
	}

	@Override
	public BankChannel getByBankAgreementId(long bankAgreementId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankAgreementId", bankAgreementId);
		return super.getBy(params);
	}

	@Override
	public List<BankChannel> listBy(Map<String , Object> param) {
		return super.listBy(param);
	}
	
	/**
	 * 根据协议表中的业务类型和账户表中的账户性质查询出对应的渠道
	 * @param linkType
	 */
	public List<BankChannel> listChannalByAgreementBusTypeAndAccountType(int linkType , int accountType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("linkType", linkType);
		params.put("accountNature", accountType);
		return super.getSessionTemplate().selectList(getStatement("listChannalByAgreementBusType"), params);
	}

	@Override
	public boolean isUsableBankChannel(String bankChannelCode) {
		BankChannel bankChannel = super.getSessionTemplate().selectOne("isUsableBankChannel",bankChannelCode);
		if(bankChannel == null ){
			return false;
		}
		return true;
	}
	
}