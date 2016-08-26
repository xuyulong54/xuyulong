package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.user.dao.UserBankAccountDao;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

@Repository("userBankAccountDao")
public class UserBankAccountDaoImpl extends BaseDaoImpl<UserBankAccount> implements UserBankAccountDao {
	/**
	 * 根据商户编号获取结算银行卡信息
	 * @param userNo
	 * @return
	 */
	@Override
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("isDefault", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("isDelete", PublicStatusEnum.INACTIVE.getValue());
		return super.getBy(paramMap);
	}
	/**
	 * 根据会员编号获取用户快捷支付银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public List<UserBankAccount> listFastBankAccountByMemberUserNo(String userNo) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("isDelete", PublicStatusEnum.INACTIVE.getValue());
		paramMap.put("isAuth", PublicStatusEnum.ACTIVE.getValue());
		return super.listBy(paramMap);
	}
	/**
	 * 根据会员编号获取会员结算银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public List<UserBankAccount> listSettlementBankAccountByMemberUserNo(
			String userNo) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("isDelete", PublicStatusEnum.INACTIVE.getValue());
		paramMap.put("bankAccountType", BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue());
		return super.listBy(paramMap);
	}
	
	/**
	 * 根据银行卡账号、用户编号获取银行卡信息
	 * @param bankAccountNo
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public UserBankAccount getByBankAccountNoAndUserNo(String bankAccountNo,
			String userNo) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("bankAccountNo", bankAccountNo);
		return super.getBy(paramMap);
	}
	
	/**
	 * 根据用户名称、银行卡号、证件账号修改银行账户信息
	 * @param userName 用户名称
	 * @param accountNo 银行卡号
	 * @param certNo 证件号码
	 * @param authStatus 鉴权状态
	 * @throws UserBizException
	 */
	@Override
	public long updateUserBankAccount(String userName, String accountNo,
			String certNo , int authStatus) {
		
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("bankAccountName", userName);
		paramMap.put("bankAccountNo", accountNo);
		paramMap.put("cardNo", certNo);
		paramMap.put("isAuth", authStatus);
		
		return super.getSessionTemplate().update("updateAuthStatusByUserNameAndAccountNo", paramMap);
	}
	
}
