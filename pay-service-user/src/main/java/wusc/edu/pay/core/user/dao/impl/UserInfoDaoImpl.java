package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * 用户信息Dao接口实现类
 * 
 * @author huangbin
 * 
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao {

	/***
	 * 根据绑定邮箱号查询用户信息
	 * 
	 * @param bindEmail
	 * @return
	 */
	public UserInfo getUserInfoByBindEmail(String bindEmail) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bindEmail", bindEmail);
		return super.getBy(paramMap);
	}

	/***
	 * 根据绑定手机号码查询用户信息
	 * 
	 * @param bindMobileNo
	 * @return
	 */
	public UserInfo getUserInfoByBindMobileNo(String bindMobileNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bindMobileNo", bindMobileNo);
		return super.getBy(paramMap);
	}

	/***
	 * 根据用户编号查询用户信息
	 * 
	 * @param userNo
	 * @return
	 */
	public UserInfo getUserInfoByUserNo(String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		return super.getBy(paramMap);
	}

	/***
	 * 根据账户编号查询用户信息
	 * 
	 * @param accountNo
	 * @return
	 */
	public UserInfo getUserInfoByAccountNo(String accountNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		return super.getBy(paramMap);
	}

	/***
	 * 根据登陆名查询用户信息
	 * 
	 * @param loginName
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		return super.getBy(paramMap);
	}
	
	/**
	 * 生成15位用户编号（在线和POS统一引此方法）.<br/>
	 * 15位的用户编号规范：'888' + 8位序列的前4位  + 4位MCC码 + 8位序列的后4位 , 在线MCC填0000 , by chenjianhua <br/>
	 * @param mcc 行业类型码.
	 * @return userNo.
	 */
	public String buildUserNo(String mcc) {
		// 获取8位的用户编号序列
		String userNoSeq = super.getSeqNextValue("USER_NO_SEQ");
		
		// 15位的用户编号规范：'888' + 8位序列的前4位  + 4位MCC码 + 8位序列的后4位 , 在线MCC填0000
		String userNo = "888" + userNoSeq.substring(0, 4) + mcc + userNoSeq.substring(4, 8);
		
		return userNo;
	}
	
	/***
	 * 更新用户状态,并标记用户为已实名认证
	 * @param userNo
	 * @param changeStatus
	 * @return
	 */
	public int updateUserInfoStatusAndIsRealName(String userNo, int changeStatus, int isRealNameAuth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("status", changeStatus);
		map.put("isRealNameAuth", isRealNameAuth); // 用户标为实名认证
		return this.getSqlSession().update("updateUserInfoStatusAndIsRealName", map);
	}

	/***
	 * 检验法人身份证号是否存在
	 * @param cardNo
	 * @return
	 * @throws UserBizException
	 */
	public UserInfo getUserInfoByCardNo(String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardNo", cardNo);
		return super.getBy(map);
	}
	
	
	/**
	 * 根据userNo模糊查找 
	 * @param userNoKey .
	 * @return List .
	 */
	public List<UserInfo> likeBy(String userNoKey){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userNoKey", userNoKey);
		return super.getSessionTemplate().selectList(getStatement("likeBy"), params);
	}
}
