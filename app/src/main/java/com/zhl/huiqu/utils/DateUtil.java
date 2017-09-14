package com.zhl.huiqu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {

	private static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	private static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	private static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	private static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	private static final String FORMAT_YYYYMMDD_HH_MM_SS = "yyyyMMdd HH:mm:ss";
	private static String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

	private final static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String format(Date date) {
		return format1.format(date);
	}

	public static Date dateFormat(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		return sdf.parse(dateStr);
	}

	public static Date dateFormatCustom(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateStr);
	}

	/**
	 * 获取当前时间
	 *
	 * @return 当前的时间
	 */
	public static String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
		return sdf.format(DateUtil.getDate());
	}

	/**
	 * 当前的时间
	 *
	 * @return 当前的时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 当前的日期
	 *
	 * @return 当前的日期（2016-1-4）
	 */
	public static String getSimpleDate() {
		return formatSimpleDate(getDate());
	}

	/**
	 * 获取当前时间
	 *
	 * @return 当前的时间
	 */
	public static String getBeforDay(int n) {
		SimpleDateFormat sp = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date date = getDate();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 0 - n);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return sp.format(date);
	}

	/**
	 * 本年 或下一年  某月的起始区间
	 *
	 * @param month
	 * @return
	 */
	public static Map<String, Date> getMonthDiff(int month) throws ParseException {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH);
		if (month < currentMonth) {
			currentYear = currentYear + 1;
		}
		Map<String, Date> map = new HashMap<>();
		Date lastDate = getLastDayOfMonth(currentYear, month - 1);
		Date firstDate = getFirstDayOfMonth(currentYear, month - 1);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		map.put("lastDate", parseDate(sdf.format(lastDate)));
		map.put("firstDate", parseDate(sdf.format(firstDate)));
		return map;
	}

	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return cal.getTime();
	}


	/**
	 * 获取当前时间,时区为;"Asia/Shanghai"
	 *
	 * @return
	 */
	public static String timestamp() {
		String timestamp;
		Calendar cal = Calendar.getInstance();
		DateFormat dfm = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
		dfm.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		timestamp = dfm.format(cal.getTime());
		return timestamp;
	}

	/**
	 * 根据原来的时间（MONTH）获得相对偏移 N 月的时间
	 *
	 * @param protoDate                原来的时间（java.util.Date）
	 * @param monthOffset（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetMonthDate(Date protoDate, int monthOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.MONTH, monthOffset);
		return cal.getTime();
	}


	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
	 *
	 * @param protoDate              原来的时间（java.util.Date）
	 * @param dayOffset（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetDayDate(Date protoDate, int dayOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.DATE, dayOffset);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（HOUR）获得相对偏移 N 小时的时间
	 *
	 * @param protoDate          原来的时间（java.util.Date）
	 * @param hours（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetHour(Date protoDate, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（MINUTE）获得相对偏移 N 分钟的时间
	 *
	 * @param protoDate            原来的时间（java.util.Date）
	 * @param minutes（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetMinute(Date protoDate, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}


	/**
	 * 根据原来的时间（MINUTE）获得相对偏移 N 分钟的时间
	 *
	 * @param protoDateStr         原来的时间（String）
	 * @param minutes（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static String getOffsetMinute(String protoDateStr, int minutes) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
		try {
			Date protoDate = sdf.parse(protoDateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(protoDate);
			cal.add(Calendar.MINUTE, minutes);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 根据原来的时间（SECOND）获得相对偏移 N 秒的时间
	 *
	 * @param protoDate            原来的时间（java.util.Date）
	 * @param seconds（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetSecond(Date protoDate, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}


	/**
	 * 设置时间
	 *
	 * @param protoDate 原来的时间（java.util.Date）
	 * @param time
	 * @return 时间（java.util.Date）
	 */
	public static Date setTime(Date protoDate, String time) throws ParseException {
		String dateStr = formatSimpleDate(protoDate) + " " + time;
		return parseDate(dateStr);
	}

	/**
	 * 根据原来的时间（YEAR）获得相对偏移 N 年的时间
	 *
	 * @param protoDate               原来的时间（java.util.Date）
	 * @param yearOffset（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetYearDate(Date protoDate, int yearOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.YEAR, yearOffset);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date）
	 *
	 * @param protoDate                原来的时间（java.util.Date）
	 * @param yearOffset（向前移正数，向后移负数）
	 * @param monthOffset（向前移正数，向后移负数）
	 * @param dayOffset（向前移正数，向后移负数）
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetDate(Date protoDate, int yearOffset, int monthOffset, int dayOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.add(Calendar.YEAR, yearOffset);
		cal.add(Calendar.MONTH, monthOffset);
		cal.add(Calendar.DATE, dayOffset);
		return cal.getTime();
	}


	/**
	 * 将字符串转换为Date类型
	 *
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) throws ParseException {
		return new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).parse(str);
	}

	/**
	 * 格式化字符串，解决从MySql中查出来的数据后面多了0的问题
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).format(date);
	}

	/**
	 * 格式化字符串(yyyy-MM-dd)
	 *
	 * @param date
	 * @return
	 */
	public static String formatSimpleDate(Date date) {
		return new SimpleDateFormat(FORMAT_YYYY_MM_DD).format(date);
	}


	public static Date parseDate(String str) throws ParseException {
		return org.apache.commons.lang3.time.DateUtils.parseDate(str, new String[]{FORMAT_YYYY_MM_DD, FORMAT_YYYYMMDD, FORMAT_YYYY_MM_DD_HH_MM_SS, FORMAT_YYYYMMDDHHMMSS, FORMAT_YYYYMMDD_HH_MM_SS});
	}

	public static boolean isDate(String dateStr) {
		String eL = "^(((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/])(10|12|0?[13578])([-\\/])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/])(11|0?[469])([-\\/])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/])(0?2)([-\\/])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/])(0?2)([-\\/])(29)$)|(^([3579][26]00)([-\\/])(0?2)([-\\/])(29)$)|(^([1][89][0][48])([-\\/])(0?2)([-\\/])(29)$)|(^([2-9][0-9][0][48])([-\\/])(0?2)([-\\/])(29)$)|(^([1][89][2468][048])([-\\/])(0?2)([-\\/])(29)$)|(^([2-9][0-9][2468][048])([-\\/])(0?2)([-\\/])(29)$)|(^([1][89][13579][26])([-\\/])(0?2)([-\\/])(29)$)|(^([2-9][0-9][13579][26])([-\\/])(0?2)([-\\/])(29))|(((((0[13578])|([13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9])|(3[01])))|((([469])|(11))[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9])|(30)))|((02|2)[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9]))))[\\-\\/\\s]?\\d{4})(\\s(((0[1-9])|([1-9])|(1[0-2]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])\\s))([AM|PM|am|pm]{2,2})))";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}


	public static int compareDate(Date from, Date to) {
		return from.compareTo(to);
	}

	/**
	 * 偏移比较时间
	 *
	 * @param fromStr
	 * @param toStr
	 * @param hour
	 * @return
	 */
	public static int compareDate(String fromStr, String toStr, int hour) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM);
		try {
			Date fromDate = sdf.parse(fromStr);
			Date offsetDate = getOffsetHour(fromDate, hour);
			Date toDate = sdf.parse(toStr);
			return offsetDate.compareTo(toDate);
		} catch (ParseException e) {
		}
		return -1;
	}

	public static long getCurrentDateDiff(String dateStr) throws ParseException {
		Date date = parseDate(dateStr);
		long diff = getDate().getTime() - date.getTime();
		return diff / 1000;
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 根据出发日期，和产品详情中的"day ":(Integer)行程天数，算出每次行程的出发日期。
	 * 例如输入 2015-12-20，13 输出 2016-01-01
	 *
	 * @param departDateStr 出发日期
	 * @param day           行程天数
	 * @return
	 */
	public static String getStartDate(String departDateStr, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		try {
			Date departDate = sdf.parse(departDateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(departDate);
			cal.add(Calendar.DATE, day - 1);
			return new SimpleDateFormat(FORMAT_YYYY_MM_DD).format(cal.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	public static String getWeekFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekIndex < 0) {
			weekIndex = 0;
		}
		return weeks[weekIndex];
	}

	/**
	 * 将日期加指定天数
	 *
	 * @param departDateStr
	 * @param day
	 * @return
	 */
	public static String addDateByDays(String departDateStr, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		try {
			Date departDate = sdf.parse(departDateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(departDate);
			cal.add(Calendar.DATE, day);
			return new SimpleDateFormat(FORMAT_YYYY_MM_DD).format(cal.getTime());
		} catch (ParseException e) {
		}
		return null;
	}


	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		Date sm = sdf.parse(sdf.format(smdate));
		Date bd = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(sm);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bd);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static Integer daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

}
