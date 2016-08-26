/**
 * 
 */
package wusc.edu.pay.web.permission.biz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.web.permission.dao.PmsOperatorLogDao;
import wusc.edu.pay.web.permission.entity.PmsOperator;
import wusc.edu.pay.web.permission.entity.PmsOperatorLog;
import wusc.edu.pay.web.permission.enums.OperatorLogStatusEnum;
import wusc.edu.pay.web.permission.enums.OperatorLogTypeEnum;


/**
 * @描述: 权限管理-操作员操作日表业务层.
 * @作者: WuShuicheng.
 * @创建时间: 2013-12-31,下午4:22:22.
 * @版本号: V1.0 .
 * 
 */
@Component("pmsOperatorLogBiz")
public class PmsOperatorLogBiz {

	private static final Log log = LogFactory.getLog(PmsOperatorLogBiz.class);

	@Autowired
	private PmsOperatorLogDao pmsOperatorLogDao;

	/**
	 * 根据ID获取操作员操作日志信息.
	 * 
	 * @param id
	 *            .
	 * @return PmsOperatorLog.
	 */
	public PmsOperatorLog getById(Long id) {
		return pmsOperatorLogDao.getById(id);
	}

	/**
	 * 分页查询操作员操作日志信息.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsOperatorLogDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 创建操作员操作记录.
	 * @param logTypeEnum 操作日志类型.
	 * @param logStatusEnum 操作日志状态.
	 * @param content 要记录的日志内容.
	 * @param loginedOperator 操作员信息.
	 * @param ipAddr IP地址.
	 */
	public void createOperatorLog(OperatorLogTypeEnum logTypeEnum, OperatorLogStatusEnum logStatusEnum, String content, PmsOperator operator, String ipAddr) {
		if (operator == null) {
			log.warn("==> operator is null");
			return;
		}
		
		PmsOperatorLog optLog = new PmsOperatorLog();
		optLog.setOperatorId(operator.getId());
		optLog.setOperatorName(operator.getLoginName());
		optLog.setOperateType(logTypeEnum.getValue());
		optLog.setStatus(logStatusEnum.getValue());
		optLog.setIp(ipAddr);
		optLog.setContent(content);

		pmsOperatorLogDao.insert(optLog);
	}

}
