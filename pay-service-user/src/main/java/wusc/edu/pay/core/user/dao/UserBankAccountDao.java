package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

/**
 * 用户银行卡账户信息表
 * @author Peter
 *
 */
public interface UserBankAccountDao extends BaseDao<UserBankAccount>{
	
	/**
	 * 根据商户编号获取结算银行卡信息
	 * @param userNo
	 * @return
	 */
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo);
	
	/**
	 * 根据会员编号获取用户快捷支付银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listFastBankAccountByMemberUserNo(String userNo);
	
	/**
	 * 根据会员编号获取会员结算银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listSettlementBankAccountByMemberUserNo(String userNo);
	
	/**
	 * 根据银行卡账号、用户编号获取银行卡信息
	 * @param bankAccountNo
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getByBankAccountNoAndUserNo(String bankAccountNo,String userNo);
	
	/**
	 * 根据用户名称、银行卡号、证件账号修改银行账户信息
	 * @param userName 用户名称
	 * @param accountNo 银行卡号
	 * @param certNo 证件号码
	 * @param authStatus 鉴权状态
	 * @throws UserBizException
	 */
	public long updateUserBankAccount(String userName, String accountNo, String certNo , int authStatus);
}
