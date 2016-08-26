package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 用户银行账户信息
 * @author Peter
 *
 */
public interface UserBankAccountFacade {
	
	/**
	 * 创建用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 * @throws UserBizException
	 */
	public long creatUserBankAccount(UserBankAccount userBankAccount) throws UserBizException ;
	
	/**
	 * 修改用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 * @throws UserBizException
	 */
	public long updateUserBankAccount(UserBankAccount userBankAccount)  throws UserBizException;
	
	/**
	 * 根据用户名称、银行卡号、证件账号修改银行账户信息
	 * @param userName 用户名称
	 * @param accountNo 银行卡号
	 * @param certNo 证件号码
	 * @param authStatus 鉴权状态
	 * @throws UserBizException
	 */
	public long updateUserBankAccount(String userName , String accountNo , String certNo , int authStatus) throws UserBizException;
	
	/**
	 * 分页查询用户银行卡账户信息
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;
	
	/**
	 * 根据id获取用户银行卡账户信息
	 * @param id
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getById(long id) throws UserBizException;
	
	/**
	 * 根据会员编号获取用户快捷支付银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listFastBankAccountByMemberUserNo(String userNo) throws UserBizException;
	
	/**
	 * 根据会员编号获取会员结算银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listSettlementBankAccountByMemberUserNo(String userNo) throws UserBizException;
	
	/**
	 * 根据商户编号获取结算银行卡信息
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo) throws UserBizException;
	
	/**
	 * 根据银行卡账号、用户编号获取银行卡信息
	 * @param bankAccountNo
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getByBankAccountNoAndUserNo(String bankAccountNo , String userNo) throws UserBizException;
}
