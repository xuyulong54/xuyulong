package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.UserBankAccountBiz;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;

/**
 * 用户银行账户信息表
 * @author Peter
 *
 */
@Component("userBankAccountFacade")
public class UserBankAccountFacadeImpl implements UserBankAccountFacade {
	
	@Autowired
	private UserBankAccountBiz userBankAccountBiz;
	
	/**
	 * 创建用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 */
	@Override
	public long creatUserBankAccount(UserBankAccount userBankAccount) {
		return userBankAccountBiz.creatUserBankAccount(userBankAccount);
	}
	/**
	 * 修改用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 */
	@Override
	public long updateUserBankAccount(UserBankAccount userBankAccount) {
		return userBankAccountBiz.updateUserBankAccount(userBankAccount);
	}

	/**
	 * 分页查询用户银行卡账户信息
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userBankAccountBiz.listPage(pageParam, paramMap);
	}
	
	/**
	 * 根据id获取用户银行卡账户信息
	 * @param id
	 * @return
	 */
	@Override
	public UserBankAccount getById(long id) {
		return userBankAccountBiz.getById(id);
	}
	
	/**
	 * 根据会员编号获取用户快捷支付银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public List<UserBankAccount> listFastBankAccountByMemberUserNo(String userNo) {
		return userBankAccountBiz.listFastBankAccountByMemberUserNo(userNo);
	}
	
	/**
	 * 根据会员编号获取会员结算银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public List<UserBankAccount> listSettlementBankAccountByMemberUserNo(String userNo) {
		return userBankAccountBiz.listSettlementBankAccountByMemberUserNo(userNo);
	}
	
	/**
	 * 根据商户编号获取结算银行卡信息
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo)
			throws UserBizException {
		return userBankAccountBiz.getSettlementBankAccountByMerchantUserNo(userNo);
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
			String userNo) throws UserBizException {
		return userBankAccountBiz.getByBankAccountNoAndUserNo(bankAccountNo,userNo);
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
			String certNo , int authStatus) throws UserBizException {
		return userBankAccountBiz.updateUserBankAccount(userName, accountNo, certNo , authStatus);
	}

}
