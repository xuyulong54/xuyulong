package wusc.edu.pay.core.agent.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.agent.dao.AgentMerchantRelationDao;
import wusc.edu.pay.core.user.biz.UserAuditBiz;
import wusc.edu.pay.core.user.dao.MerchantFileDao;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.UserAuditRecordStatusDao;
import wusc.edu.pay.core.user.dao.UserBankAccountDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.core.user.dao.UserTradePwdDao;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.vo.AgentSplidProfitVo;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.service.TradeLimitRouterFacade;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * 代理商商户关系 biz 类
 * 
 * @author huangbin
 * 
 */
@Component("agentMerchantRelationBiz")
public class AgentMerchantRelationBiz {

	private static final Log log = LogFactory.getLog(AgentMerchantRelationBiz.class);

	@Autowired
	private AgentMerchantRelationDao agentMerchantRelationDao;
	@Autowired
	private MerchantOnlineDao merchantOnlineDao;
	@Autowired
	private UserInfoDao userInfoDao; // 用户信息
	@Autowired
	private UserBankAccountDao userBankAccountDao; // 商户银行账户信息
	@Autowired
	private MerchantFileDao merchantFileDao; // 商户资质文件
	@Autowired
	private UserTradePwdDao userTradePwdDao; // 商户交易密码信息
	@Autowired
	private UserOperatorDao userOperatorDao; // 门户用户操作员表
	@Autowired
	private AccountManagementFacade accountManagementFacade; // 账户信息
	@Autowired
	private SettManagementFacade settManagementFacade; // 结算信息
	@Autowired
	private UserAuditBiz userAuditBiz;
	@Autowired
	private UserAuditRecordStatusDao userAuditRecordStatusDao;
	@Autowired
	private TradeLimitRouterFacade tradeLimitRouterFacade;
	@Autowired
	private FeeManagerFacade feeManagerFacade;

	/***
	 * 根据ID查询实体信息
	 * 
	 * @param id
	 * @return
	 */
	public AgentMerchantRelation getById(long id) {
		return agentMerchantRelationDao.getById(id);
	} 

	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentMerchantRelationDao.listPage(pageParam, paramMap);
	}

	/**
	 * 更新代理商和商户关系
	 * 
	 * @return result
	 */
	public long update(AgentMerchantRelation relation) {
		return agentMerchantRelationDao.update(relation);
	}

	/**
	 * 添加代理商和商户关系
	 * 
	 * @return result
	 */
	public long create(AgentMerchantRelation relation) {
		return agentMerchantRelationDao.insert(relation);
	}

	/***
	 * 根据商户编号查询代理商和商户的关联表
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @return
	 */
	public AgentMerchantRelation getByMerchantNo(String merchantNo, String firstAgentNo, String secondAgentNo, String thirdAgentNo, String isDirectly) {
		return agentMerchantRelationDao.getByMerchantNo(merchantNo, firstAgentNo, secondAgentNo, thirdAgentNo, isDirectly);
	}

	/**
	 * 根据商户编号找代理商层级分润
	 * 
	 * @param merchantNo
	 * @return
	 */
	public AgentSplidProfitVo getAgentSplidProfitVo(String merchantNo) {
		return agentMerchantRelationDao.getAgentSplidProfitVo(merchantNo);
	}
	

	/***
	 * 重构所有关联的商户编号
	 * 
	 * @param oldMerchNo
	 *            变更前的商户编号
	 * @param mcc
	 *            商户的mcc码
	 * @return
	 */
	public long reBindMerchantNo(String oldMerchNo, String mcc, List<UserFeeSetting> userFeeList, 
			TradeLimitRouter tradeLimitRouterModel) {
		
		if (StringUtil.isEmpty(oldMerchNo) || StringUtil.isEmpty(mcc)) {
			throw new UserBizException(UserBizException.MERCHANT_IS_NULL, "商户编号为空.");
		}
		
		// 1.修改商户信息表商户编号 TBL_USER_MERCHANT_ONLINE
		MerchantOnline merchant = merchantOnlineDao.getByMerchantNo(oldMerchNo);
		if (merchant == null) {
			throw new UserBizException(UserBizException.MERCHANT_IS_NULL, "商户信息为空!");
		}
		
		// 2.修改用户表商户编号 TBL_USER_INFO
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(oldMerchNo);
		if (userInfo == null) {
			throw new UserBizException(UserBizException.USERINFO_IS_EXIST, "用户信息为空!");
		}
		
		// 3.修改用户银行账户表 TBL_USER_BANK_ACCOUNT
		UserBankAccount bankaccount = userBankAccountDao.getSettlementBankAccountByMerchantUserNo(oldMerchNo);
		if (bankaccount == null) {
			throw new UserBizException(UserBizException.USER_BANK_ACCOUNT_IS_NULL, "结算账户信息不存在.");
		}
		
		// 4.修改商户资质文件表 TBL_USER_MERCHANT_FILE
		MerchantFile file = merchantFileDao.getByMerchantNo(oldMerchNo);
		if (file == null) {
			throw new UserBizException(UserBizException.MERCHANT_FILE_IS_NULL, "商户资质文件信息不存在.");
		}
		
		// 5.修改交易密码表 TBL_USER_TRADE_PWD
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", oldMerchNo);
		UserTradePwd pwd = userTradePwdDao.getBy(paramMap);
		if (pwd == null) {
			throw new UserBizException(UserBizException.USER_TRADEPWD_NOT_EXIT, "交易密码不存在");
		}
		
		// 6.修改代理商商户关系表 TBL_AGENT_MERCHANT_RELATION
		AgentMerchantRelation relation = agentMerchantRelationDao.getByMerchantNo(oldMerchNo, null, null, null, null);
		if (relation == null) {
			throw new UserBizException(UserBizException.AGENT_MERCHANT_RELATION_IS_NULL, "代理商商户关系信息不存在");
		}
		
		// 7.修改门户系统的用户操作员表 TBL_PORTAL_USER_OPERATOR
		UserOperator userOperator = userOperatorDao.getBy(paramMap);
		if (userOperator == null) {
			throw new UserBizException(UserBizException.USER_OPERATOR_IS_NULL, "用户操作员信息不存在");
		}
		
		log.info("==============> 重构商户编号,旧商户编号[" + oldMerchNo + "],MCC码[" + mcc + "] . ");
		String newMerchNo = oldMerchNo.substring(0, 7) + mcc + oldMerchNo.substring(11);
		// 把前3位888改成880
		newMerchNo = "880" + newMerchNo.substring(3);
		
		log.info("==============> 替换MCC后最终的商编为[" + newMerchNo + "]<============== ");
		
		// 更新审核记录表
		long statNum = userAuditRecordStatusDao.updateMerchNo(oldMerchNo, newMerchNo);
		log.info("==============> 更新商户状态审核记录表成功 , 返回值为： " + statNum);
		
		merchant.setMcc(mcc);
		merchant.setMerchantNo(newMerchNo);
		long merchantNum = merchantOnlineDao.update(merchant);
		log.info("==============> 更新商户信息表成功 , 返回值为： " + merchantNum);

		userInfo.setUserNo(newMerchNo);
		long userNum = userInfoDao.update(userInfo);
		log.info("==============> 更新用户信息表成功 , 返回值为： " + userNum);
		
		bankaccount.setUserNo(newMerchNo);
		long bankNum = userBankAccountDao.update(bankaccount);
		log.info("==============> 更新用户银行账户表成功  , 返回值为： " + bankNum);

		file.setMerchantNo(newMerchNo);
		long fileNum = merchantFileDao.update(file);
		log.info("==============> 更新商户资质文件信息表成功   , 返回值为： " + fileNum);

		pwd.setUserNo(newMerchNo);
		long pwdNum = userTradePwdDao.update(pwd);
		log.info("==============> 更新交易密码表信息表成功   , 返回值为： " + pwdNum);

		relation.setMerchantNo(newMerchNo);
		long relationNum = agentMerchantRelationDao.update(relation);
		log.info("==============> 更新代理商商户关系表成功   , 返回值为： " + relationNum);

		userOperator.setUserNo(newMerchNo);
		long operNum = userOperatorDao.update(userOperator);
		log.info("==============> 更新门户系统的用户操作员表成功   , 返回值为： " + operNum);

		// 8.修改结算规则表 TBL_SETT_RULE
		long settNum = settManagementFacade.reBindUserNo(merchant.getAccountNo(), newMerchNo);
		log.info("==============> 更新结算规则表成功   , 返回值为： " + settNum);
		
		// 设置商户的计费节点
		for(UserFeeSetting sett : userFeeList){
			sett.setUserNo(newMerchNo);
			feeManagerFacade.createUserFeeSetting(sett);
		}
		log.info(String.format("==============> 创建商户商户的计费节点成功， 共插入：%s条数据", userFeeList.size()));
		
		// 设置商户的关联限制包
		tradeLimitRouterModel.setMerchantNo(newMerchNo);
		long limitId = tradeLimitRouterFacade.saveTradeLimitRouter(tradeLimitRouterModel);
		log.info(String.format("==============> 创建商户关联限制包成功， 返回值为：%s", limitId));
		
		
		// 9.修改账户表关联的商户编号 TBL_ACCOUNT
		long accountNum = accountManagementFacade.reBindUserNo(merchant.getAccountNo(), newMerchNo);
		log.info("==============> 更新账户信息成功   , 返回值为： " + accountNum);

		return settNum;
	}

	/***
	 * 统计当前代理商下的商户状态
	 * 
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List summaryMerchStatusByAgentNo(String firstAgentNo, String secondAgentNo, String thirdAgentNo) {
		return agentMerchantRelationDao.summaryMerchStatusByAgentNo(firstAgentNo, secondAgentNo, thirdAgentNo);
	}

	/**
	 * 查询商户所有信息
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectMerchantAllInfo(Map<String, Object> paramMap) {
		return agentMerchantRelationDao.selectMerchantAllInfo(paramMap);
	}

	public List<AgentMerchantRelation> listBy(Map<String, Object> paramMap) {
		return agentMerchantRelationDao.listBy(paramMap);
	}

	/***
	 * @Title: auditMerch_updMerch
	 * @Description:  审核商户，并且重新生成商户编号
	 * @param @param id
	 * @param @param userNo
	 * @param @param loginName
	 * @param @param realName
	 * @param @param string
	 * @param @param string2
	 * @param @param value
	 * @param @param status
	 * @param @param mcc
	 * @param @return    设定文件
	 * @return long    返回类型
	 * @throws
	 */
	@Transactional(rollbackFor = Exception.class)
	public long auditMerch_updMerch(Long id, String userNo, String auditOperatorLoginName, String auditOperatorName, 
			String applyDesc, String auditDesc, int changeStatus, Integer currentStatus, String mcc,
			List<UserFeeSetting> userFeeList, TradeLimitRouter tradeLimitRouterModel) {
		
		// 更新审核记录
		userAuditBiz.auditPass_Status(id, userNo, auditOperatorLoginName, auditOperatorName, applyDesc, 
				auditDesc, changeStatus, currentStatus);
		
		log.info(String.format("================> 更新审核记录完毕，重构商户编号开始，商编 [%s]，MCC[%s]", userNo, mcc));
		// 重构商户编号
		long result = this.reBindMerchantNo(userNo, mcc, userFeeList, tradeLimitRouterModel);
		if(result > 0){
			log.info(String.format("================> 更新商编完毕!原始商编 [%s]，MCC[%s]", userNo, mcc));
		}
		return result;
	}

	/***
	 * @Description: 修改商户绑定的终端号
	 * @param merchantNo   
	 * @param operFlag 操作方向 （1-加，2-减） 
	 * @return void  
	 * @throws
	 * @author Huang Bin
	 * @date 2015-4-2
	 */
	public long updateTerminalNum(String merchantNo, int operFlag) {
		log.info(String.format("===> 获取到的商户编号为{%s}", merchantNo));
		AgentMerchantRelation relation = agentMerchantRelationDao.getByMerchantNo(merchantNo, null, null, null, null);
		if(relation == null){
			throw new UserBizException(UserBizException.AGENT_MERCHANT_RELATION_IS_NULL, "商户信息为空");
		}
		log.info(String.format("===> 准备更新商户绑定的终端数量，标示为{%s}", operFlag));
		if(operFlag == 1){ //终端数量+1
			int terNum = 0;
			if(relation.getTerminalNum() != null){
				terNum = relation.getTerminalNum();
			}
			relation.setTerminalNum(terNum + 1);
		}else{	// 终端数量减1
			if(relation.getTerminalNum() != null && relation.getTerminalNum() > 0){
				relation.setTerminalNum(relation.getTerminalNum() - 1);
			}else{
				relation.setTerminalNum(0);
			}
		}
		log.info("===> 商户当前绑定终端的数量是：" + relation.getTerminalNum());
		return agentMerchantRelationDao.update(relation);
	}
}
