package wusc.edu.pay.core.user.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.UserOperatorLogDao;
import wusc.edu.pay.facade.user.entity.UserOperatorLog;


/**
 * 
 * @描述: 用户操作员业务层接口 .
 * @作者: WuShuicheng.
 * @创建: 2014-5-28,上午11:16:35
 * @版本: V1.0
 * 
 */
@Component("userOperatorLogBiz")
public class UserOperatorLogBiz {

	@Autowired
	private UserOperatorLogDao userOperatorLogDao;

	/**
	 * 根据ID获取操作员操作日志信息.
	 * 
	 * @param id
	 *            操作记录ID.
	 * @return UserOperatorLog.
	 */
	public UserOperatorLog getUserOperateLogById(Long id) {
		return userOperatorLogDao.getById(id);
	}

	/**
	 * 分页查询用户操作员操作日志信息.<br/>
	 * 
	 * @param pageParam
	 *            分页参数.<br/>
	 * @param beginTime
	 *            开始时间.<br/>
	 * @param endTime
	 *            结束时间.<br/>
	 * @param userNo
	 *            用户编号.<br/>
	 * @param loginName
	 *            操作员登录名.<br/>
	 * @param operateType
	 *            操作类型(0:失败,1:成功).<br/>
	 * @param ip
	 *            IP地址.<br/>
	 * @return pageList.<br/>
	 */
	public PageBean listUserOperatorLogForPage(PageParam pageParam, String beginTime, String endTime, String userNo, String loginName, Integer operateStatus, String ip) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("userNo", userNo);
		map.put("loginName", loginName);
		map.put("operateStatus", operateStatus);
		map.put("ip", ip);
		return userOperatorLogDao.listPage(pageParam, map);
	}

	/**
	 * 创建操作日志
	 * 
	 * @param loginName
	 *            登录名
	 * @param userNo
	 *            用户编号
	 * @param opeStatus
	 *            操作状态
	 * @param ip
	 *            客户端IP
	 * @param content
	 *            操作日志
	 * @return
	 */
	public long createUserOperatorLog(String loginName, String userNo, OpeStatusEnum opeStatus, String ip, String content) {
		UserOperatorLog userLog = new UserOperatorLog();
		userLog.setContent(content);
		userLog.setCreateTime(new Date());
		userLog.setIp(ip);
		userLog.setLoginName(loginName);
		userLog.setOperateStatus(opeStatus.getValue());
		userLog.setUserNo(userNo);
		return userOperatorLogDao.insert(userLog);
	}

}
