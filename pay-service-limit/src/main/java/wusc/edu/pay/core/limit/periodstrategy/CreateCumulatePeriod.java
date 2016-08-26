package wusc.edu.pay.core.limit.periodstrategy;
import java.util.Date;

/**
 * 累计周期生成器
 * 
 * @author：zh
 */
public interface CreateCumulatePeriod {

	/**
	 * 生成累计周期
	 * 
	 * @param date
	 *            根据此时间和类型生成累计周期
	 * @return
	 */
	public Period createPeriod(Date date);

}
