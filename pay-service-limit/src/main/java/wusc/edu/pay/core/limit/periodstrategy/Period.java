package wusc.edu.pay.core.limit.periodstrategy;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 累计周期
 * 
 * @author：zh
 */
public class Period {

	/**
	 * 开始时间
	 */
	private Date beginDate;

	/**
	 * 结束时间
	 */
	private Date endDate;

	public Period() {
		super();
	}

	public Period(Date beginDate, Date endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
