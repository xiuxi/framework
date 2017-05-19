package com.framework.module.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期 工具类
 * 
 * @author qq
 * 
 */
public class DateUtil {
	/*****************************************************************************************************
	 * 字符串与日期转换
	 *****************************************************************************************************/
	/**
	 * 将<字符串>格式化为<日期>
	 * 
	 * @param dateStr
	 *            字符串日期
	 * 
	 * @param pattern
	 *            转换格式（默认yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return 日期
	 */
	public static Date parse(String dateStr, String pattern) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		Date result = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			result = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 将<日期>格式化为<字符串>
	 * 
	 * @param date
	 *            要转换的日期
	 * 
	 * @param pattern
	 *            转换格式（默认yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/*****************************************************************************************************
	 * 日期加减
	 *****************************************************************************************************/
	/**
	 * 
	 * 获取日期加减（年 && 月 && 日 && 时 && 分 && 秒）后的日期
	 * 
	 * 相减使用负数表示
	 * 
	 * @param date
	 *            要加减的日期
	 * 
	 * @param year
	 *            加减<年>数
	 * 
	 * @param month
	 *            加减<月>数
	 * 
	 * @param day
	 *            加减<日>数
	 * 
	 * @param hour
	 *            加减<时>数
	 * 
	 * @param minute
	 *            加减<分>数
	 * 
	 * @param second
	 *            加减<秒>数
	 * 
	 * @return 加减后的日期
	 */
	public static Date addTime(Date date, int year, int month, int day, int hour, int minute, int second) {
		if (date == null) {
			return null;
		}

		Calendar calender = toCalendar(date);
		calender.add(Calendar.YEAR, year);
		calender.add(Calendar.MONTH, month);
		calender.add(Calendar.DAY_OF_MONTH, day); // DAY_OF_MONTH=DATE
		calender.add(Calendar.HOUR_OF_DAY, hour); // HOUR_OF_DAY:24小时制;HOUR:12小时制
		calender.add(Calendar.MINUTE, minute);
		calender.add(Calendar.SECOND, second);
		return calender.getTime();
	}

	/**
	 * 获取日期加减（年 || 月 || 日 || 时 || 分 || 秒）后的日期
	 * 
	 * 相减使用负数表示
	 * 
	 * @param date
	 *            要加减的日期
	 * 
	 * @param num
	 *            加减数
	 * 
	 * @param unit
	 *            指定加减年还是月...
	 * 
	 * @return 加减后的日期
	 */
	public static Date addTime(Date date, int num, DateUnit unit) {
		if (date == null) {
			return null;
		}

		if (unit == null) {
			return date;
		}

		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;

		switch (unit) {
		case YEAR:
			year = num;
			break;
		case MONTH:
			month = num;
			break;
		case DAY:
			day = num;
			break;
		case HOUR:
			hour = num;
			break;
		case MINUTE:
			minute = num;
			break;
		case SECOND:
			second = num;
			break;
		}

		return addTime(date, year, month, day, hour, minute, second);
	}

	/*****************************************************************************************************
	 * 日期<时间差>
	 *****************************************************************************************************/
	/**
	 * 获取两个日期的<时间差>
	 * 
	 * start: 2015-12-09 18:15:32 end: 2015-12-10 20:18:36 >>>
	 * 相差天数：1;相差小时数：26;相差分钟数：1563;相差秒数：93784秒
	 * 
	 * @param start
	 *            起始日期
	 * 
	 * @param end
	 *            截止日期
	 * 
	 * @return （时间差数组）日[0]时[1]分[2]秒[3]
	 */
	public static long[] timeDifferencePer(Date start, Date end) {
		long[] difference = new long[4];
		long differentMillis = end.getTime() - start.getTime();

		long day = differentMillis / (1000 * 60 * 60 * 24);
		long hour = differentMillis / (1000 * 60 * 60);
		long minute = differentMillis / (1000 * 60);
		long second = differentMillis / 1000;

		difference[0] = day;
		difference[1] = hour;
		difference[2] = minute;
		difference[3] = second;

		return difference;
	}

	/**
	 * 获取两个日期的<时间差>
	 * 
	 * start: 2015-12-09 18:15:32 end: 2015-12-10 20:18:36 >>> 相差：1天2小时3分钟4秒
	 * 
	 * @param start
	 *            起始日期
	 * 
	 * @param end
	 *            截止日期
	 * 
	 * @return （时间差数组）日[0]时[1]分[2]秒[3]
	 */
	public static long[] timeDifference(Date start, Date end) {
		long[] difference = new long[4];
		long differentMillis = end.getTime() - start.getTime();

		long day = differentMillis / (1000 * 60 * 60 * 24);
		long hour = differentMillis / (1000 * 60 * 60);
		long minute = differentMillis / (1000 * 60);
		long second = differentMillis / 1000;

		difference[0] = day;
		difference[1] = hour % 24;
		difference[2] = minute % 60;
		difference[3] = second % 60;

		return difference;
	}

	/*****************************************************************************************************
	 * 日期<相差>、<相隔>天数
	 *****************************************************************************************************/
	/**
	 * 获取两个日期<相差>的天数
	 * 
	 * 不是同一天，相差天数也可以为0
	 * 
	 * start: 2015-12-09 18:15:32 end: 2015-1-29 00:00:00 >>> 相差：0天
	 * 
	 * @param start
	 *            起始日期
	 * 
	 * @param end
	 *            截止日期
	 * @return
	 */
	public static int daysDifference(Date start, Date end) {
		// 把时分秒算在内
		long differentMillis = end.getTime() - start.getTime();
		long day = differentMillis / (1000 * 60 * 60 * 24);
		return (int) day;
	}

	/**
	 * 获取两个日期<相隔>的天数
	 * 
	 * 只要是同一天，相隔就是0。不是同一天，相隔就不是0
	 * 
	 * start: 2015-12-09 18:15:32 end: 2015-1-29 00:00:00 >>> 相隔：1天
	 * 
	 * @param start
	 *            起始日期
	 * 
	 * @param end
	 *            截止日期
	 * @return
	 */
	public static int daysApart(Date start, Date end) {
		// 把时分秒忽略掉了
		Date s = parse(format(start, "yyyy-MM-dd"), "yyyy-MM-dd");
		Date e = parse(format(end, "yyyy-MM-dd"), "yyyy-MM-dd");

		long differentMillis = e.getTime() - s.getTime();

		long day = differentMillis / (1000 * 60 * 60 * 24);

		return (int) day;
	}

	/*****************************************************************************************************
	 * 日期转换
	 *****************************************************************************************************/
	/**
	 * Date => Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	/*****************************************************************************************************
	 * 日期修改
	 *****************************************************************************************************/
	/**
	 * 日期修改
	 * 
	 * @param date
	 * @param year
	 * @param month
	 *            1为1月
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date setTime(Date date, int year, int month, int day, int hour, int minute, int second) {
		if (date == null) {
			return null;
		}

		Calendar calendar = toCalendar(date);
		calendar.set(year, month - 1, day, hour, minute, second);

		return calendar.getTime();
	}

	/**
	 * 日期修改
	 * 
	 * @param date
	 * @param dateUnit
	 *            1为1月
	 * @param value
	 * @return
	 */
	public static Date setTime(Date date, DateUnit dateUnit, int value) {
		if (date == null) {
			return null;
		}

		if (dateUnit == null) {
			return date;
		}

		int calendarUnit = -1;
		switch (dateUnit) {
		case YEAR:
			calendarUnit = Calendar.YEAR;
			break;
		case MONTH:
			calendarUnit = Calendar.MONTH;
			--value; // 默认0为1月
			break;
		case DAY:
			calendarUnit = Calendar.DATE;
			break;
		case HOUR:
			calendarUnit = Calendar.HOUR_OF_DAY;
			break;
		case MINUTE:
			calendarUnit = Calendar.MINUTE;
			break;
		case SECOND:
			calendarUnit = Calendar.SECOND;
			break;
		}

		Calendar calendar = toCalendar(date);
		calendar.set(calendarUnit, value);

		return calendar.getTime();
	}

	/*****************************************************************************************************
	 * 枚举
	 *****************************************************************************************************/
	/**
	 * 日期单位
	 */
	public static enum DateUnit {
		YEAR, MONTH, DAY, HOUR, MINUTE, SECOND
	}
}
