package wusc.edu.pay.facade.fee.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.utils.DateUtils;


/**
 * 日期相关工具类
 * @Copyright: Copyright (c)2011
 * @company 易宝支付(YeePay)
 * @author runxin.li
 * @since 2012-3-30
 * @version 1.0
 */
public class FeeDateUtils {
	
	/**
	 * 字符串转换为日期
	 * @param date 日期字符串
	 * @param exp 表达式（默认为 yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static Date convertDate(String date, String exp){
		if(null == date || "".equals(date)){
			throw new IllegalArgumentException("Invalid string date!");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != exp && !"".equals(exp)){
			sdf = new SimpleDateFormat(exp);
		}
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期时间转字符串
	 * @param date 日期
	 * @param exp 表达式（默认：yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String convertDate(Date date, String exp){
		if(null == date){
			throw new IllegalArgumentException("Invalid date!");
		}
		if(null != exp && !"".equals(exp)){
			SimpleDateFormat sdf = new SimpleDateFormat(exp);
			return sdf.format(date);
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
	}
	
	/**
	 * 将日期时间段切割成日期集合
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> splitDays(Date start, Date end){
		if(null == start || end == null){
			throw new IllegalArgumentException("Invalid date.");
		}
		else if(start.after(end)){
			throw new IllegalArgumentException("Invalid date.");
		}
		else{
			List<Date> dates = new ArrayList<Date>();
			while(!start.after(end)){
				dates.add(start);
				start = DateUtils.addDay(start, 1);
			}
			return dates;
		}
	}
	
	/**
	 * 按天分割每天的小时数
	 * @param date
	 * @return
	 */
	public static List<Date> splitDayHours(Date date){
		if(null == date){
			return new ArrayList<Date>();
		}
		else{
			List<Date> dates = new ArrayList<Date>();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			while(hour < 24){
				dates.add(getHourStart(calendar.getTime()));
				hour ++;
				calendar.set(Calendar.HOUR_OF_DAY, hour);
			}
			return dates;
		}
	}
	
	/**
	 * 上季度开始第一天时间
	 * @param date
	 * @return
	 */
	public static Date getLastQuarterDateStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		//区分当前月份在第几季度
		if(currentMonth >= 1 && currentMonth <= 3){
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		else if(currentMonth >= 4 && currentMonth <= 6){
			calendar.set(Calendar.MONTH, Calendar.MARCH);
		}
		else if(currentMonth >= 7 && currentMonth <= 9){
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}
		else if(currentMonth >= 10 && currentMonth <= 12){
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取上个季度最后一天时间
	 * @param date
	 * @return
	 */
	public static Date getLastQuarterDateEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		//区分当前月份在第几季度
		if(currentMonth >= 1 && currentMonth <= 3){
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		else if(currentMonth >= 4 && currentMonth <= 6){
			calendar.set(Calendar.MONTH, Calendar.MARCH);
		}
		else if(currentMonth >= 7 && currentMonth <= 9){
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}
		else if(currentMonth >= 10 && currentMonth <= 12){
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	public static Date getLastMonthDateStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		
		return getMonthDayStart(calendar.getTime());
	}
	
	public static Date getLastMonthDateEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		
		return getMonthDayEnd(calendar.getTime());
	}
	
	/**
	 * 获取上周第一天时间
	 * @param date
	 * @return
	 */
	public static Date getLastWeekDateStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		
		return getWeekDayStart(calendar.getTime());
	}
	
	/**
	 * 获取上周最后一天时间
	 * @param date
	 * @return
	 */
	public static Date getLastWeekDateEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		
		return getWeekDayEnd(calendar.getTime());
	}
	
	/**
	 * 获取本年的最后一天时间
	 * @param date
	 * @return
	 */
	public static Date getYearDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取本年的开始时间
	 * @param date
	 * @return
	 */
	public static Date getYearDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取季度的结束时间
	 * @param date
	 * @return
	 */
	public static Date getQuarterDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		//区分当前月份在第几季度
		if(currentMonth >= 1 && currentMonth <= 3){
			calendar.set(Calendar.MONTH, Calendar.MARCH);
		}
		else if(currentMonth >= 4 && currentMonth <= 6){
			calendar.set(Calendar.MONTH, Calendar.JUNE);
		}
		else if(currentMonth >= 7 && currentMonth <= 9){
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		else if(currentMonth >= 10 && currentMonth <= 12){
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取季度开始时间
	 * @param date
	 * @return
	 */
	public static Date getQuarterDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		//区分当前月份在第几季度
		if(currentMonth >= 1 && currentMonth <= 3){
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
		}
		else if(currentMonth >= 4 && currentMonth <= 6){
			calendar.set(Calendar.MONTH, Calendar.APRIL);
		}
		else if(currentMonth >= 7 && currentMonth <= 9){
			calendar.set(Calendar.MONTH, Calendar.JULY);
		}
		else if(currentMonth >= 10 && currentMonth <= 12){
			calendar.set(Calendar.MONTH, Calendar.OCTOBER);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取指定时间月的最后一日的时间
	 * @param date
	 * @return
	 */
	public static Date getMonthDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取指定时间月份的第一日
	 * @param date
	 * @return
	 */
	public static Date getMonthDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendarTimeSet(calendar);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取每周的最后一日的最后时间
	 * @param date
	 * @return
	 */
	public static Date getWeekDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendarTimeSet(calendar);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取每周的第一日的开始时间
	 * @param date
	 * @return
	 */
	public static Date getWeekDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendarTimeSet(calendar);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当日结束时间
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date){
		if(date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarTimeSet(calendar);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当日开始时间
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarTimeSet(calendar);
//		calendar.add(Calendar.MILLISECOND, +1);
		return calendar.getTime();
	}
	
	/**
	 * 获取小时结束时间
	 * @param date
	 * @return
	 */
	public static Date getHourEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		timeSet(calendar);
		calendar.add(Calendar.HOUR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	
	/**获取小时开始时间
	 * @param date
	 * @return
	 */
	public static Date getHourStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		timeSet(calendar);
		return calendar.getTime();
	}
	
	/**
	 * Calendar 设置，小时、分钟、秒、毫秒设置
	 * @param calendar
	 */
	private static void calendarTimeSet(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		timeSet(calendar);
	}
	
	private static void timeSet(Calendar calendar){
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * 时间区间判断，当且仅当startDate所表示的时间点在endDate之前，才返回true,即合法
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static boolean timeIntervalLegal(Date startDate, Date endDate){
		if(null == startDate){
			return false;
		}
		else if(null == endDate){
			return true;
		}
		else if(startDate.after(endDate)){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * 时间是否在区间内检查
	 * 目标时间大于或等于区间左值、小于或等于区间右值时，返回true,否则返回false
	 * @param left 区间左值
	 * @param right 区间右值
	 * @param target 待比较目标时间值
	 * @return
	 */
	public static boolean isTimeInInterval(Date left, Date right, Date target){
		if(null == left || null == target){
			throw new IllegalArgumentException("The date of left, target can not be null!");
		}
		else if(null == right){
			if(!target.before(left)){
				return true;
			}
		}
		else{
			if(!target.before(left) && !target.after(right)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 计算实时生效的生效结束时间(amount 秒后生效)
	 * @param date 
	 * @param amount
	 * @return
	 */
	public static Date calculateEffectiveDate(Date date, int amount){
		if(null == date)
			throw new IllegalArgumentException("Illegal arguments for calculate next effective end date!");
		//定义日历
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//在修改时间的基础上向后推 amount 秒
		calendar.add(Calendar.SECOND, amount);
		return calendar.getTime();
	}
	
	public static void main(String[] args){
		Date date = new Date();
		System.out.println("The day start of week: " + FeeDateUtils.getWeekDayStart(date));
		System.out.println("The day end of week: " + FeeDateUtils.getWeekDayEnd(date));
		
		System.out.println("The day start of year: " + FeeDateUtils.getYearDayStart(date));
		System.out.println("The day end of year: " + FeeDateUtils.getYearDayEnd(date));
		
		System.out.println("The day start of quarter: " + FeeDateUtils.getQuarterDayStart(date));
		System.out.println("The day end of quarter: " + FeeDateUtils.getQuarterDayEnd(date));
		
		System.out.println("The day start of month: " + FeeDateUtils.getMonthDayStart(date));
		System.out.println("The day end of month: " + FeeDateUtils.getMonthDayEnd(date));
		
		System.out.println("The hour start of day: " + FeeDateUtils.getHourStart(date));
		System.out.println("The hour end of day: " + FeeDateUtils.getHourEnd(date));
		
		System.out.println("Hours: " + FeeDateUtils.splitDayHours(date));
		
		System.out.println("Split: " + FeeDateUtils.splitDays(DateUtils.addDay(date, -1), DateUtils.addDay(date, 1)));
	}
}
