package wusc.edu.pay.web.permission.base;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.constant.PermissionConstant;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.web.permission.biz.PmsOperatorLogBiz;
import wusc.edu.pay.web.permission.entity.PmsOperator;
import wusc.edu.pay.web.permission.enums.OperatorLogStatusEnum;
import wusc.edu.pay.web.permission.enums.OperatorLogTypeEnum;


/**
 * 
 * @描述: Web系统权限模块基础支撑Action.
 * @作者: WuShuicheng.
 * @创建: 2014-8-13,下午5:19:28
 * @版本: V1.0
 * 
 */
@SuppressWarnings("serial")
public class PermissionBaseAction extends Struts2ActionSupport implements OperatorLoginedAware {

	@Autowired
	private PmsOperatorLogBiz pmsOperatorLogBiz;

	/**
	 * 取出当前登录操作员对象
	 */
	@Override
	public PmsOperator getLoginedOperator() {
		PmsOperator operator = (PmsOperator) super.getHttpSession().getAttribute(PermissionConstant.OPERATOR_SESSION_KEY);
		return operator;
	}
	
	
	/**
	 * 登录系统时记录日志.
	 * 
	 * @param content
	 */
	protected void logLogin(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.LOGIN, OperatorLogStatusEnum.SUCCESS, content, getLoginedOperator(), super.getIpAddr());
	}

	/**
	 * 登录系统失败时记录日志.
	 * 
	 * @param content
	 */
	protected void logLoginError(String content, PmsOperator operator){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.LOGIN, OperatorLogStatusEnum.ERROR, content, operator, super.getIpAddr());
	}
	
	/**
	 * 保存数据的时记录日志.
	 * 
	 * @param content
	 */
	protected void logSave(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.ADD, OperatorLogStatusEnum.SUCCESS, content, getLoginedOperator(), super.getIpAddr());
	}

	/**
	 * 保存数据的失败时记录日志.
	 * 
	 * @param content
	 */
	protected void logSaveError(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.ADD, OperatorLogStatusEnum.ERROR, content, getLoginedOperator(), super.getIpAddr());
	}
	
	/**
	 * 更新数据时记录日志.
	 * 
	 * @param content
	 */
	protected void logEdit(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.EDIT, OperatorLogStatusEnum.SUCCESS, content, getLoginedOperator(), super.getIpAddr());
	}

	/**
	 * 更新数据失败时记录日志.
	 * 
	 * @param content
	 */
	protected void logEditError(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.EDIT, OperatorLogStatusEnum.ERROR, content, getLoginedOperator(), super.getIpAddr());
	}
	
	/**
	 * 删除数据时记录日志.
	 * 
	 * @param content
	 */
	protected void logDelete(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.DELETE, OperatorLogStatusEnum.SUCCESS, content, getLoginedOperator(), super.getIpAddr());
	}

	/**
	 * 删除数据失败时记录日志.
	 * 
	 * @param content
	 */
	protected void logDeleteError(String content){
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.DELETE, OperatorLogStatusEnum.ERROR, content, getLoginedOperator(), super.getIpAddr());
	}
	
	/**
	 * 查询数据时记录日志.
	 * @param content
	 */
	protected void logQuery(String content) {
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.QUERYA, OperatorLogStatusEnum.SUCCESS, content, getLoginedOperator(), super.getIpAddr());
	}

	/**
	 * 查询数据失败时记录日志.
	 * @param content
	 */
	protected void logQueryError(String content) {
		pmsOperatorLogBiz.createOperatorLog(OperatorLogTypeEnum.QUERYA, OperatorLogStatusEnum.ERROR, content, getLoginedOperator(), super.getIpAddr());
	}

}
