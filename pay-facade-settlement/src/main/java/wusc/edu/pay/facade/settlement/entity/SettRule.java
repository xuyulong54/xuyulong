package wusc.edu.pay.facade.settlement.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.settlement.enums.SettRuleCycleTypeEnum;


/**
 * 
 * @描述: 结算规则实体.
 * @作者: WuShuicheng.
 * @创建: 2014-7-29,下午1:44:09
 * @版本: V1.0
 * 
 */
public class SettRule extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2902834792317466268L;

	/** 账户编号 **/
	private String accountNo;

	/** 用户编号 **/
	private String userNo;

	/** 用户名称 **/
	private String userName;

	/** 结算类型(参考 SettTypeEnum) **/
	private Integer settType;

	/** 结算周期类型(参考 SettRuleCycleTypeEnum) **/
	private Integer settCycleType;

	/** 结算周期 **/
	private String settCycleData;

	/** 风险预存期天数 **/
	private Integer riskDay;

	/** 是否可结算(参与枚举:PublicStatusEnum) **/
	private Integer isSett;

	/** 当前结算状态(参考SettRuleStatusEnum) **/
	private Integer currentSettStatus;

	/** 上次汇总日期 **/
	private Date lastSumDate;

	/** 上次结算截止日期 **/
	private Date lastSettDate;

	/** 上次结算处理日期 **/
	private Date lastProcessDate;

	/** 下次结算处理日期 **/
	private Date nextProcessDate;

	/** 上次结算批次号 **/
	private String lastBatchNo;

	/** 最后修改时间 **/
	private Date lastModifyTime;

	/** 操作员登录名 **/
	private String operatorLoginname;

	/** 操作员姓名 **/
	private String operatorRealname;

	/** 描述 **/
	private String remark;

	/***************************************************************/
	/**
	 * 设置下次结算处理日及上次结算处理日
	 * 
	 * @param date
	 *            结算处理日或者新建规则日
	 */
	public Date calculateNextSettleDay(Date date) {

		// 定义对参数date及所得结果时间进行格式化的时间格式，以满足判断
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt = sdf.parse(sdf.format(this.calculateNextSettleDay()));
			// 如果得到的下次结算处理日期在参数date日期之前，则再次使用计算规则进行计算
			// while (!dt.after(sdf.parse(sdf.format(date))) || !dt.after(new
			// Date())) {
			while (!dt.after(sdf.parse(sdf.format(date)))) {
				dt = sdf.parse(sdf.format(this.calculateNextSettleDay()));
				this.nextProcessDate = dt;
			}

			return dt;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算下次结算处理日期
	 * 
	 */
	private Date calculateNextSettleDay() {
		// 定义日历对象
		Calendar calendar = Calendar.getInstance();
		// 定义日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Integer type = this.settCycleType; // 得到结算的周期（月或周）
		String[] data = this.settCycleData.split(","); // 得到结算周期日期的数组

		// 按月结算
		if (type == SettRuleCycleTypeEnum.MONTH.getValue()) {
			// 下次结算处理日期不为空，计算下次结算处理日期
			if (null != this.nextProcessDate) {
				// 获取下次结算日期的day
				String temp = sdf.format(this.nextProcessDate);
				String day = temp.substring(temp.lastIndexOf("-") + 1);

				// 验证日期 是否在结算规则的结算周期中
				if (this.validatorIsIn(this.settCycleData, day)) {
					calendar.setTime(this.nextProcessDate);
					// 按月结算：计算下次结算处理时间
					date = this.caculateSettleDayByMonth(data, calendar, sdf);
				} else {
					date = initNextDay(calendar, sdf, date, data);
				}
			}
			// 首次新增结算规则，初始化下次结算处理日期数据
			else {
				date = initNextDay(calendar, sdf, date, data);
			}
		}
		// 按周结算
		else if (type == SettRuleCycleTypeEnum.WEEK.getValue()) {
			// 已进行过结算，计算下次结算处理日期
			if (null != this.nextProcessDate) {
				calendar.setTime(this.nextProcessDate);
				int temp = calendar.get(Calendar.DAY_OF_WEEK);
				String day = String.valueOf(temp);

				if (this.validatorIsIn(this.settCycleData, day)) {
					calendar.setTime(this.nextProcessDate);
					// 按周结算：计算下次结算处理时间
					date = this.caculateSettleDayByWeek(data, calendar, sdf);
				} else {
					calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(data[0]));
					this.setNextProcessDate(calendar.getTime());

					date = calendar.getTime();
				}
			}
			// 首次新增结算规则，初始化下次结算处理日期数据
			else {
				calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(data[0]));
				this.setNextProcessDate(calendar.getTime());

				date = calendar.getTime();
			}
		}

		return date;
	}

	/**
	 * 按月结算：计算下次结算处理时间
	 * 
	 * @param date
	 *            结算周期数据内每个结算日
	 * @param calendar
	 *            日历
	 * @param sdf
	 *            日期格式
	 * @return
	 */
	private Date caculateSettleDayByMonth(String[] date, Calendar calendar, SimpleDateFormat sdf) {
		String lastDay = sdf.format(this.nextProcessDate);
		// 获取当前lastDay的最后两位字符
		String day = lastDay.substring(lastDay.lastIndexOf("-") + 1);
		// 按月结算
		for (int i = 0; i < date.length; i++) {
			// 如果是当前设定的每月的最后一个结算日，则需要转到下一个月
			if (this.validatorIsIn(date[i], day) && i == date.length - 1) {
				int value = Integer.parseInt(date[0]);
				calendar.set(Calendar.DATE, value);
				calendar.add(Calendar.MONTH, 1);
				break;
			}
			// 如果不是当前设定的每月最后一个结算日，则取该月的下一个结算日
			else if (this.validatorIsIn(date[i], day)) {
				int value = Integer.parseInt(date[i + 1]);
				if (value > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					value = Integer.parseInt(date[0]);
					calendar.set(Calendar.DATE, value);
					calendar.add(Calendar.MONTH, 1);
					break;
				}
				calendar.set(Calendar.DATE, value);
				break;
			}

		}
		return calendar.getTime();
	}
	
	/**
	 * 初始化下一个结算日期
	 * @描述: .
	 * @作者: Along.shen .
	 * @创建时间:2014-12-10,上午9:27:45.
	 * @版本:
	 */
	private Date initNextDay(Calendar calendar, SimpleDateFormat sdf, Date date, String[] data) {
		String todayTime = sdf.format(new Date());
		String todayDay = todayTime.substring(todayTime.lastIndexOf("-") + 1);
		// 如果今天比周期中最大值还要大
		if (Integer.parseInt(todayDay) > Integer.parseInt(data[data.length - 1])) {
			calendar.setTime(new Date());
			calendar.set(Calendar.DATE, Integer.parseInt(data[0]));
			calendar.add(Calendar.MONTH, 1);
			date = calendar.getTime();
		} else {
			//
			for (int i = 0; i < data.length; i++) {
				if(Integer.parseInt(todayDay)<=Integer.parseInt(data[i])){
					if(i==0){
						calendar.setTime(new Date());
						calendar.set(Calendar.DATE, Integer.parseInt(data[0]));
						date = calendar.getTime();
						break;
					}else if(Integer.parseInt(todayDay)>Integer.parseInt(data[i-1])){
						calendar.setTime(new Date());
						calendar.set(Calendar.DATE, Integer.parseInt(data[i]));
						date = calendar.getTime();
						break;
					}
				}
			}
		}
		return date;
	}

	/**
	 * 按周结算：计算下次结算处理时间
	 * 
	 * @param date
	 *            结算周期数据内每个结算日
	 * @param calendar
	 *            日历
	 * @return
	 */
	private Date caculateSettleDayByWeek(String[] date, Calendar calendar, SimpleDateFormat sdf) {
		String lastDay = sdf.format(this.nextProcessDate);
		// 定义局部Calendar对象
		Calendar weekCalendar = Calendar.getInstance();
		weekCalendar.setTime(this.nextProcessDate);

		for (int i = 0; i < date.length; i++) {
			weekCalendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(date[i]));
			// 如果是当前设定的每周的最后一个结算日，则需要转到下一个周
			if (lastDay.equals(sdf.format(weekCalendar.getTime())) && i == date.length - 1) {
				calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(date[0]));
				calendar.add(Calendar.WEEK_OF_YEAR, 1);
				break;
			}

			// 如果不是当前设定的每周的最后一个结算日，则取该周的下一个结算日
			else if (lastDay.equals(sdf.format(weekCalendar.getTime()))) {
				calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(date[i + 1]));
				break;
			}

		}
		return calendar.getTime();
	}

	/**
	 * 验证某个日期是否在结算规则日中
	 * 
	 * @param cycleData
	 *            结算规则日
	 * @param day
	 *            某个日期
	 * @return
	 */
	private boolean validatorIsIn(String cycleData, String day) {
		String[] cycleDays = cycleData.split(",");
		if (cycleDays.length == 1 && StringUtil.isBlank(cycleDays[0])) {
			return false;
		}
		for (int i = 0; i < cycleDays.length; i++) {
			if (Integer.parseInt(day) == Integer.parseInt(cycleDays[i])) {
				return true;
			}
		}
		return false;
	}

	/** 账户编号 **/
	public String getAccountNo() {
		return accountNo;
	}

	/** 账户编号 **/
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	/** 用户编号 **/
	public String getUserNo() {
		return userNo;
	}

	/** 用户编号 **/
	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	/** 用户名称 **/
	public String getUserName() {
		return userName;
	}

	/** 用户名称 **/
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/** 结算类型(参考 SettTypeEnum) **/
	public Integer getSettType() {
		return settType;
	}

	/** 结算类型(参考SettleTypeEnum) **/
	public void setSettType(Integer settType) {
		this.settType = settType;
	}

	/** 结算周期类型(参考SettlementRuleCycleTypeEnum) **/
	public Integer getSettCycleType() {
		return settCycleType;
	}

	/** 结算周期类型(参考SettlementRuleCycleTypeEnum) **/
	public void setSettCycleType(Integer settCycleType) {
		this.settCycleType = settCycleType;
	}

	/** 结算周期 **/
	public String getSettCycleData() {
		return settCycleData;
	}

	/** 结算周期 **/
	public void setSettCycleData(String settCycleData) {
		this.settCycleData = settCycleData == null ? null : settCycleData.trim();
	}

	/** 风险预存期天数 **/
	public Integer getRiskDay() {
		return riskDay;
	}

	/** 风险预存期天数 **/
	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

	/** 是否可结算(参与枚举:PublicStatusEnum) **/
	public Integer getIsSett() {
		return isSett;
	}

	/** 是否可结算(参与枚举:PublicStatusEnum) **/
	public void setIsSett(Integer isSett) {
		this.isSett = isSett;
	}

	/** 当前结算状态(参考SettlementRuleStatusEnum) **/
	public Integer getCurrentSettStatus() {
		return currentSettStatus;
	}

	/** 当前结算状态(参考SettlementRuleStatusEnum) **/
	public void setCurrentSettStatus(Integer currentSettStatus) {
		this.currentSettStatus = currentSettStatus;
	}

	/** 上次汇总日期 **/
	public Date getLastSumDate() {
		return lastSumDate;
	}

	/** 上次汇总日期 **/
	public void setLastSumDate(Date lastSumDate) {
		this.lastSumDate = lastSumDate;
	}

	/** 上次结算截止日期 **/
	public Date getLastSettDate() {
		return lastSettDate;
	}

	/** 上次结算截止日期 **/
	public void setLastSettDate(Date lastSettDate) {
		this.lastSettDate = lastSettDate;
	}

	/** 上次结算处理日期 **/
	public Date getLastProcessDate() {
		return lastProcessDate;
	}

	/** 上次结算处理日期 **/
	public void setLastProcessDate(Date lastProcessDate) {
		this.lastProcessDate = lastProcessDate;
	}

	/** 下次结算处理日期 **/
	public Date getNextProcessDate() {
		return nextProcessDate;
	}

	/** 下次结算处理日期 **/
	public void setNextProcessDate(Date nextProcessDate) {
		this.nextProcessDate = nextProcessDate;
	}

	/** 上次结算批次号 **/
	public String getLastBatchNo() {
		return lastBatchNo;
	}

	/** 上次结算批次号 **/
	public void setLastBatchNo(String lastBatchNo) {
		this.lastBatchNo = lastBatchNo == null ? null : lastBatchNo.trim();
	}

	/** 最后修改时间 **/
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	/** 最后修改时间 **/
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/** 操作员登录名 **/
	public String getOperatorLoginname() {
		return operatorLoginname;
	}

	/** 操作员登录名 **/
	public void setOperatorLoginname(String operatorLoginname) {
		this.operatorLoginname = operatorLoginname == null ? null : operatorLoginname.trim();
	}

	/** 操作员姓名 **/
	public String getOperatorRealname() {
		return operatorRealname;
	}

	/** 操作员姓名 **/
	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname == null ? null : operatorRealname.trim();
	}

	/** 描述 **/
	public String getRemark() {
		return remark;
	}

	/** 描述 **/
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}