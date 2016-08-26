package wusc.edu.pay.facade.user.service;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserOperatorLog;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 
 * @描述: 用户操作员操作日志Dubbo接口 .
 * @作者: WuShuicheng.
 * @创建: 2014-5-28,下午2:14:33
 * @版本: V1.0
 * 
 */
public interface UserOperatorLogFacade {

	/**
	 * 根据ID获取操作员操作日志信息.
	 * 
	 * @param id
	 *            操作记录ID.
	 * @return UserOperatorLog.
	 */
	public UserOperatorLog getUserOperatorLogById(Long id) throws UserBizException;

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
	 * @param userType
	 *            用户类型.<br/>
	 * @param loginName
	 *            操作员登录名.<br/>
	 * @param operateType
	 *            操作类型(0:失败,1:成功).<br/>
	 * @param ip
	 *            IP地址.<br/>
	 * @return pageList.<br/>
	 */
	public PageBean listUserOperatorLogForPage(PageParam pageParam, String beginTime, String endTime, String userNo, String loginName, Integer operateStatus, String ip) throws UserBizException;

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
	long createUserOperatorLog(String loginName, String userNo,OpeStatusEnum opeStatus,String ip,String content) throws UserBizException;
}
