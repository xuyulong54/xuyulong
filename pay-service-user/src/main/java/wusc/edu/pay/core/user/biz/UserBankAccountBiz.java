package wusc.edu.pay.core.user.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.UserBankAccountDao;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


@Component("userBankAccountBiz")
public class UserBankAccountBiz {
	
	@Autowired
	private UserBankAccountDao userBankAccountDao;
	@Autowired
	private MerchantOnlineDao merchantDao;
	
	/**
	 * 创建用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 */
	public long creatUserBankAccount(UserBankAccount userBankAccount){
		return userBankAccountDao.insert(userBankAccount);
	}
	
	/**
	 * 修改用户银行卡账户信息
	 * @param userBankAccount
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public long updateUserBankAccount(UserBankAccount userBankAccount){
		
		MerchantOnline merchant = merchantDao.getByMerchantNo(userBankAccount.getUserNo());
		if(merchant != null && merchant.getStatus()==MerchantStatusEnum.NOPASS.getValue()){
			merchant.setStatus(MerchantStatusEnum.CREATED.getValue());
			merchantDao.update(merchant);
		}
		return userBankAccountDao.update(userBankAccount);
	}
	
	/**
	 * 根据用户名称、银行卡号、证件账号修改银行账户信息
	 * @param userName 用户名称
	 * @param accountNo 银行卡号
	 * @param certNo 证件号码
	 * @param authStatus 鉴权状态
	 * @throws UserBizException
	 */
	public long updateUserBankAccount(String userName, String accountNo,
			String certNo , int authStatus){
		return userBankAccountDao.updateUserBankAccount(userName, accountNo, certNo ,authStatus);
	}
	
	/**
	 * 分页查询用户银行卡账户信息
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return userBankAccountDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 根据id获取用户银行卡账户信息
	 * @param id
	 * @return
	 */
	public UserBankAccount getById(long id){
		return userBankAccountDao.getById(id);
	}
	
	/**
	 * 根据会员编号获取用户快捷支付银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listFastBankAccountByMemberUserNo(String userNo){
		return userBankAccountDao.listFastBankAccountByMemberUserNo(userNo);
	}
	
	/**
	 * 根据会员编号获取会员结算银行卡账户信息列表
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public List<UserBankAccount> listSettlementBankAccountByMemberUserNo(String userNo) {
		return userBankAccountDao.listSettlementBankAccountByMemberUserNo(userNo);
	}
	
	/**
	 * 根据商户编号获取结算银行卡信息
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo){
		return userBankAccountDao.getSettlementBankAccountByMerchantUserNo(userNo);
	}
	
	/**
	 * 根据银行卡账号、用户编号获取银行卡信息
	 * @param bankAccountNo
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getByBankAccountNoAndUserNo(String bankAccountNo,
			String userNo) throws UserBizException {
		return userBankAccountDao.getByBankAccountNoAndUserNo(bankAccountNo,userNo);
	}
}
