package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * 用户信息Dao接口
 * 
 * @author huangbin
 * 
 */
public interface UserInfoDao extends BaseDao<UserInfo> {

	/***
	 * 根据绑定邮箱号查询用户信息
	 * 
	 * @param bindEmail
	 * @return
	 */
	UserInfo getUserInfoByBindEmail(String bindEmail);

	/***
	 * 根据绑定手机号码查询用户信息
	 * 
	 * @param bindMobileNo
	 * @return
	 */
	UserInfo getUserInfoByBindMobileNo(String bindMobileNo);

	/***
	 * 根据用户编号查询用户信息
	 * 
	 * @param userNo
	 * @return
	 */
	UserInfo getUserInfoByUserNo(String userNo);

	/***
	 * 根据账户编号查询用户信息
	 * 
	 * @param accountNo
	 * @return
	 */
	UserInfo getUserInfoByAccountNo(String accountNo);

	/***
	 * 根据登陆名查询用户信息
	 * 
	 * @param loginName
	 * @return
	 */
	UserInfo getUserInfoByLoginName(String loginName);
	
	/**
	 * 生成15位用户编号（在线和POS统一引此方法）.<br/>
	 * 15位的用户编号规范：'888' + 8位序列的前4位  + 4位MCC码 + 8位序列的后4位 , 在线MCC填0000 , by chenjianhua <br/>
	 * @param mcc 行业类型码.
	 * @return userNo.
	 */
	String buildUserNo(String mcc);

	/***
	 * 更新用户状态,并标记用户为已实名认证
	 * @param userNo
	 * @param changeStatus
	 * @return
	 */
	int updateUserInfoStatusAndIsRealName(String userNo, int changeStatus, int isRealNameAuth);

	/***
	 * 检验法人身份证号是否存在
	 * @param cardNo
	 * @return
	 * @throws UserBizException
	 */
	UserInfo getUserInfoByCardNo(String cardNo);
	
	/**
	 * 根据userNo模糊查找 
	 * @param userNoKey .
	 * @return List .
	 */
	List<UserInfo> likeBy(String userNoKey);
}
