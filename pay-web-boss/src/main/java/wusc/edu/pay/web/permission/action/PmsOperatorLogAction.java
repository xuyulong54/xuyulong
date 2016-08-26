/**
 * 
 */
package wusc.edu.pay.web.permission.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.web.permission.base.PermissionBaseAction;
import wusc.edu.pay.web.permission.biz.PmsOperatorBiz;
import wusc.edu.pay.web.permission.biz.PmsOperatorLogBiz;
import wusc.edu.pay.web.permission.entity.PmsOperator;
import wusc.edu.pay.web.permission.entity.PmsOperatorLog;
import wusc.edu.pay.web.permission.enums.OperatorLogStatusEnum;
import wusc.edu.pay.web.permission.enums.OperatorLogTypeEnum;


/**
 * @描述: 操作员操作日志管理.
 * @作者: WuShuicheng.
 * @创建时间: 2013-12-31,下午11:01:59.
 * @版本号: V1.0 .
 * 
 */
@Scope("prototype")
public class PmsOperatorLogAction extends PermissionBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(PmsOperatorLogAction.class);

	@Autowired
	private PmsOperatorBiz pmsOperatorBiz;
	@Autowired
	private PmsOperatorLogBiz pmsOperatorLogBiz;

	/**
	 * 进入操作员操作日志信息查询页面.
	 * 
	 * @return
	 */
	@Permission("pms:operatorlog:view")
	public String listPmsOperatorLogUI() {
		// 日志状态
		super.putData("OperatorLogStatusEnum", OperatorLogStatusEnum.toMap());
		super.putData("OperatorLogStatusEnumList", OperatorLogStatusEnum.values());
		// 日志类型
		super.putData("OperatorLogTypeEnum", OperatorLogTypeEnum.toMap());
		super.putData("OperatorLogTypeEnumList", OperatorLogTypeEnum.values());
		return "PmsOperatorLogList";
	}

	/**
	 * 分页查询列出操作员操作日志信息.
	 * 
	 * @return
	 */
	@Permission("pms:operatorlog:view")
	public String listPmsOperatorLog() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			String operatorName = getString("operatorName"); // 操作员登录名
			if (StringUtils.isNotBlank(operatorName)) {
				paramMap.put("operatorName", operatorName);
			}

			Integer operateType = getInteger("operateType");
			if (operateType != null) {
				paramMap.put("operateType", operateType);
			}

			Integer status = getInteger("status");
			if (status != null) {
				paramMap.put("status", status);
			}

			String ip = getString("ip");
			if (StringUtils.isNotBlank(ip)) {
				paramMap.put("ip", ip);
			}
			String beginDate = getString("beginDate");
			if (StringUtils.isNotBlank(beginDate)) {
				paramMap.put("beginDate", beginDate);
			}
			String endDate = getString("endDate");
			if (StringUtils.isNotBlank(endDate)) {
				paramMap.put("endDate", endDate);
			}
			// 对时间进行校验
			if (beginDate != null && endDate != null) {
				// 取得两个日期之间的日数
				long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

				if (a > 0 && a > 5) {
					return this.operateError("开始时间不能大于结束时间而且前后区间不能大于6天");
				}
			} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
				return this.operateError("不能只输入一个时间查询");
			}

			if (paramMap.isEmpty()) {
				log.info("== query param is empty");
				return this.operateError("查询参数不能为空");
			}

			super.pageBean = pmsOperatorLogBiz.listPage(getPageParam(), paramMap);

			super.pushData(pageBean);
			super.pushData(paramMap); // 回显查询条件值
			
			// 日志状态
			super.putData("OperatorLogStatusEnum", OperatorLogStatusEnum.toMap());
			super.putData("OperatorLogStatusEnumList", OperatorLogStatusEnum.values());
			// 日志类型
			super.putData("OperatorLogTypeEnum", OperatorLogTypeEnum.toMap());
			super.putData("OperatorLogTypeEnumList", OperatorLogTypeEnum.values());
			
			return "PmsOperatorLogList";
		} catch (Exception e) {
			log.error("==>listPmsOperatorLog exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 根据操作员操作日志ID查看操作日志详情.
	 * 
	 * @return
	 */
	@Permission("pms:operatorlog:view")
	public String viewById() {
		PmsOperatorLog operatorLog = pmsOperatorLogBiz.getById(getLong("id"));
		PmsOperator operator = pmsOperatorBiz.getById(operatorLog.getOperatorId());
		super.putData("operator", operator);
		super.putData("operatorLog", operatorLog);
		// 日志状态
		super.putData("OperatorLogStatusEnum", OperatorLogStatusEnum.toMap());
		super.putData("OperatorLogStatusEnumList", OperatorLogStatusEnum.values());
		// 日志类型
		super.putData("OperatorLogTypeEnum", OperatorLogTypeEnum.toMap());
		super.putData("OperatorLogTypeEnumList", OperatorLogTypeEnum.values());
		return "PmsOperatorLogView";
	}

}
