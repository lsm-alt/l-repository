package com.codedata.rbac.common.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理工具类
 */

public class DateUtil
{

	private static ThreadLocal<Object> format8 = new ThreadLocal<Object>() {
		protected synchronized Object initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};

	private static ThreadLocal<Object> format10 = new ThreadLocal<Object>() {
		protected synchronized Object initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 将8位的YYYYMMDD字符串转化为10位的YYYY-MM-DD字符串，主要用于日期格式转换
	 * 
	 * @param ch
	 * @return String
	 */
	public static String trans8To10(String ch) {
		return ch = ch.substring(0, 4) + "-" + ch.substring(4, 6) + "-"
					+ ch.substring(6, 8);
	}
	
	/**
	 * 
	 * 将10位的输入字符串去掉第5、8位转化为8位的字符串，主要用于日期格式转换
	 *
	 * @param ch
	 * @return String
	 */
	public static String trans10To8(String ch) {
		return ch = ch.substring(0, 4) + ch.substring(5, 7) + ch.substring(8, 10);
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @param dataFormat 日期格式
	 * @return String
	 */
	public static String dateToStr(Date date, String dataFormat) {
		if (dataFormat == null) return "";
		if (dataFormat.equals("yyyyMMdd"))
			return ((SimpleDateFormat)format8.get()).format(date);
		else if (dataFormat.equals("yyyy-MM-dd"))
//			return dateformat.format(date);
			return ((SimpleDateFormat)format10.get()).format(date);
		else
			return (new SimpleDateFormat(dataFormat)).format(date);
	}

	/**
	 * 将日期转化为yyyy-MM-dd格式字符串
	 * 
	 * @param Date
	 * @return String
	 */
	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期转化为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param Date
	 * @return String
	 */
	public static String dateTimeToStr(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期转化为yyyyMMddHHmmssSSS格式字符串
	 * 
	 * @param Date
	 * @return String
	 */
	public static String dateTimeToShortStr(Date date) {
		return dateToStr(date, "yyyyMMddHHmmssSSS");
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss:SSS格式及没有毫秒格式、没有时间格式的字符串转化为日期
	 * 
	 * @param String
	 * @return Date
	 * @throws ParseException 
	 */
	public static Date strToDateTime(String dateStr) throws ParseException {
		if (dateStr.matches("[0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+:[0-9]+")) return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).parse(dateStr);
		if (dateStr.matches("[0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+")) return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateStr);
		if (dateStr.matches("[0-9]+-[0-9]+-[0-9]+")) return java.sql.Date.valueOf(dateStr);
		return null;
	}

	/**
	 * 将yyyy-MM-dd格式字符串转化为日期
	 * 
	 * @param String
	 * @return Date
	 */
	public static java.sql.Date strToDate(String dateStr) {
		return java.sql.Date.valueOf(dateStr);
	}

	/**
	 * 格式化yyyy-MM-dd格式的日期，月、日左补0满2位，如2010-1-2=》2010-01-02
	 * 
	 * @param String
	 * @return Date
	 */
	public static String dateStrFormat(String dateStr) {
		return dateToStr(strToDate(dateStr));
	}

	/**
	 * Date型日期计算
	 * @param Date date
	 * @param String type 计算类型，可以为 year month week day
	 * @param int count 为正数则加，为负数则减
	 * @return Date
	 */
	private static Date calDate(Date date, String type, int count) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		if ("year".equals(type)) {
			cal.add(GregorianCalendar.YEAR, count);
		}
		else if ("month".equals(type)) {
			cal.add(GregorianCalendar.MONTH, count);
		}
		else if ("week".equals(type)) {
			cal.add(GregorianCalendar.WEEK_OF_YEAR, count);
		}
		else if ("day".equals(type)) {
			cal.add(GregorianCalendar.DAY_OF_YEAR, count);
		}
		else return null;
		return cal.getTime();
	}

	/**
	 * Date型天数计算
	 * @param Date date
	 * @param int days 为正数则加，为负数则减
	 * @return Date
	 */
	public static Date calDay(String date, int days) {
		return calDay(strToDate(date),days);
	}
	public static Date calDay(Date date, int days) {
		return calDate(date, "day", days);
	}
	public static String calDayToStr(String date, int days){
		return dateToStr(calDay(date, days));
	}

	/**
	 * 返回下一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date nextDay(String date) {
		return nextDay(strToDate(date));
	}
	public static Date nextDay(Date date) {
		return calDay(date, 1);
	}

	/**
	 * Date型月份计算
	 * @param Date date
	 * @param int month 为正数则加，为负数则减
	 * @return Date
	 */
	public static Date calMonth(String date, int month) {
		return calMonth(strToDate(date),month);
	}
	public static Date calMonth(Date date, int month) {
		return calDate(date, "month", month);
	}

	/**
	 * 返回下一月的同一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date nextMonth(Date date) {
		return calMonth(date, 1);
	}

	/**
	 * 返回当前月的第一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curMonthBegin(Date date) {
		return strToDate(dateToStr(date).substring(0, 8)+"01");
	}

	/**
	 * 返回当前月的最后一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curMonthEnd(Date date) {
		return calDay(curMonthBegin(nextMonth(date)), -1);
	}

	/**
	 * Date型季度计算
	 * @param Date date
	 * @param int quarter 为正数则加，为负数则减
	 * @return Date
	 */
	public static Date calQuarter(Date date, int quarter) {
		return calMonth(date, quarter*3);
	}

	/**
	 * 返回下一季度的同一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date nextQuarter(Date date) {
		return calQuarter(date, 1);
	}

	/**
	 * 返回当前季度的第一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curQuarterBegin(Date date) {
		String year = getYearStr(date);
		int month = getMonthInt(date);
		switch (month) {
		case 1:
		case 2:
		case 3:
			return strToDate(year+"-01-01");
		case 4:
		case 5:
		case 6:
			return strToDate(year+"-04-01");
		case 7:
		case 8:
		case 9:
			return strToDate(year+"-07-01");
		case 10:
		case 11:
		case 12:
			return strToDate(year+"-10-01");
		default:
			return null;
		}
	}

	/**
	 * 返回当前季度的最后一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curQuarterEnd(Date date) {
		String year = getYearStr(date);
		int month = getMonthInt(date);
		switch (month) {
		case 1:
		case 2:
		case 3:
			return strToDate(year+"-03-31");
		case 4:
		case 5:
		case 6:
			return strToDate(year+"-06-30");
		case 7:
		case 8:
		case 9:
			return strToDate(year+"-09-30");
		case 10:
		case 11:
		case 12:
			return strToDate(year+"-12-31");
		default:
			return null;
		}
	}

	/**
	 * Date型半年计算
	 * @param Date date
	 * @param int semiYear 为正数则加，为负数则减
	 * @return Date
	 */
	public static Date calSemiYear(Date date, int semiYear) {
		return calMonth(date, semiYear*6);
	}

	/**
	 * 返回下一半年的同一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date nextSemiYear(Date date) {
		return calSemiYear(date, 1);
	}

	/**
	 * 返回当前半年的第一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curSemiYearBegin(Date date) {
		String year = getYearStr(date);
		int month = getMonthInt(date);
		switch (month) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return strToDate(year+"-01-01");
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			return strToDate(year+"-07-01");
		default:
			return null;
		}
	}

	/**
	 * 返回当前半年的最后一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curSemiYearEnd(Date date) {
		String year = getYearStr(date);
		int month = getMonthInt(date);
		switch (month) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return strToDate(year+"-06-30");
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			return strToDate(year+"-12-31");
		default:
			return null;
		}
	}

	/**
	 * Date型年份计算
	 * @param Date date
	 * @param int year 为正数则加，为负数则减
	 * @return Date
	 */
	public static Date calYear(String date, int year) {
		return calYear(strToDate(date),year);
	}
	public static Date calYear(Date date, int year) {
		return calDate(date, "year", year);
	}

	/**
	 * 返回下一年的同一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date nextYear(Date date) {
		return calYear(date, 1);
	}

	/**
	 * 返回当前年的第一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curYearBegin(Date date) {
		return strToDate(dateToStr(date).substring(0, 4)+"-01-01");
	}

	/**
	 * 返回当前年的最后一天
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date curYearEnd(Date date) {
		return strToDate(dateToStr(date).substring(0, 4)+"-12-31");
	}

	/**
	 * 根据日期获取日
	 * @param Date
	 * @return String
	 */
	public static String getDayStr(Date date) {
		return (new SimpleDateFormat("dd")).format(date);
	}

	/**
	 * 根据日期获取日
	 * @param Date
	 * @return int
	 */
	public static int getDayInt(Date date) {
		return Integer.parseInt(getDayStr(date));
	}

	/**
	 * 根据日期获取月份
	 * @param Date
	 * @return String
	 */
	public static String getMonthStr(Date date) {
		return (new SimpleDateFormat("MM")).format(date);
	}

	/**
	 * 根据日期获取月份
	 * @param Date
	 * @return int
	 */
	public static int getMonthInt(Date date) {
		return Integer.parseInt(getMonthStr(date));
	}

	/**
	 * 根据日期判断所在季度
	 * 
	 * @param Date
	 * @return String 1 2 3 4
	 */
	public static String getQuarterStr(Date date) {
		int month = getMonthInt(date);
		switch (month) {
		case 1:
		case 2:
		case 3:
			return "1";
		case 4:
		case 5:
		case 6:
			return "2";
		case 7:
		case 8:
		case 9:
			return "3";
		case 10:
		case 11:
		case 12:
			return "4";
		default:
			return "";
		}
	}

	/**
	 * 根据日期判断所在季度
	 * 
	 * @param Date
	 * @return int 1 2 3 4
	 */
	public static int getQuarterInt(Date date) {
		return Integer.parseInt(getQuarterStr(date));
	}

	/**
	 * 根据日期获取年份
	 * @param Date
	 * @return String
	 */
	public static String getYearStr(Date date) {
		return (new SimpleDateFormat("yyyy")).format(date);
	}

	/**
	 * 根据日期获取年份
	 * @param Date
	 * @return int
	 */
	public static int getYearInt(Date date) {
		return Integer.parseInt(getYearStr(date));
	}

	/**
	 * 获得两个日期的间隔天数
	 * 
	 * @param startDay Date
	 * @param endDay Date
	 * @return
	 */
	public static int getDays(Date startDay, Date endDay) {
		return (int)((endDay.getTime() - startDay.getTime()) / 1000L / 3600L / 24L);
	}

	/**
	 * 对年对月对日判断两个日期的间隔月数
	 * 
	 * @param stdate Date
	 * @param enddate Date
	 * @param sdFlag boolean 是否包括开始日期的标志，通常为false
	 * @param edFlag boolean 是否包括结束日期的标志，通常为true
	 * @param abandonFlag boolean 不足整月的是否舍弃的标志，为true则不足整月都不算，否则按整月算
	 * @return
	 */
	public static int getMonths(Date sDate, Date eDate, boolean sdFlag, boolean edFlag, boolean abandonFlag) {
		if (sdFlag) sDate = calDay(sDate, -1);
		if (!edFlag) eDate = calDay(eDate, -1);
		int syear = getYearInt(sDate);
		int smonth = getMonthInt(sDate);
		int sday = getDayInt(sDate);
		int eyear = getYearInt(eDate);
		int emonth = getMonthInt(eDate);
		int eday = getDayInt(eDate);
		int month = (eyear - syear) * 12 + emonth - smonth;

		if (abandonFlag) {
			if (eday == getDayInt(curMonthEnd(eDate))) {
			} else if (sday == getDayInt(curMonthEnd(sDate))) {
				month --;
			} else if (eday - sday < 0) {
				month --;
			}
		} else {
			if (sday == getDayInt(curMonthEnd(sDate))) {
			} else if (eday == getDayInt(curMonthEnd(eDate))) {
				month ++;
			} else if (eday - sday > 0) {
				month ++;
			}
		}

		return month;
	}
	
	/**
	 * 获取当前时间，以yyyy-MM-dd HH:mm:ss格式输出
	 * @return
	 */
	public static String getCurTime() {
		return dateTimeToStr(new Date());
	}
	
	/**
	 * 获取当前日期，以yyyy-MM-dd格式输出
	 * @return
	 */
	public static String getcurDate(){
		return getCurTime().substring(0,10);
	}

	/**
	 * 得到当前年份
	 * @return
	 */
	public static String getCurYear() {
		String strTemp = getCurTime();
		return strTemp.substring(0, 4);
	}

	/**
	 * 得到当前月份
	 * @return
	 */
	public static String getNowMonth() {
		String strTemp = getCurTime();
		return strTemp.substring(5, 7);
	}

	/**
	 * 得到当前日期
	 * @return
	 */
	public static String getNowDay() {
		String strTemp = getCurTime();
		return strTemp.substring(8, 10);
	}

	/**
	 * 得到当前小时
	 * @return
	 */
	public static String getHours() {
		String strTemp = getCurTime();
		return strTemp.substring(11, 13);
	}

	public static String transDateByLong(long time) {
		Date d = new Date(time);
		Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleFormat.format(d);
	}
	
	public static boolean isDate(String str){
		String eL1= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(30)))|(02[\\-\\/\\s]?((0[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(30)))|(02[\\-\\/\\s]?((0[1-9])|(1[0-9])|(2[0-8]))))))";   
		String eL2= "^(((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(30)))|(02[\\-\\/\\s]?((0[1-9])|([1-2][0-9])))))\\s?((0[0-9])|(1[0-9])|(2[0-3]))[\\:\\s]?([0-5][0-9])[\\:\\s]?([0-5][0-9])[\\:\\s]?\\d+)|((\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?((0[1-9])|([1-2][0-9])|(30)))|(02[\\-\\/\\s]?((0[1-9])|(1[0-9])|(2[0-8])))))\\s?((0[0-9])|(1[0-9])|(2[0-3]))[\\:\\s]?([0-5][0-9])[\\:\\s]?([0-5][0-9])[\\:\\s]?\\d+))";   
        return str.matches(eL1)==true?true:str.matches(eL2);
	}
	
	/**
	 * 如果是yyyyMMdd格式的就转成yyyy-MM-dd格式，否则返回原字符串
	 * @param str
	 * @return
	 */
	public static String tranStrToDateStr10(String str){
		if(str==null) return null;
		if(str.matches("[0-9]+"))
			return trans8To10(str);
		return str;
	}
	
	/**
	 * 如果是yyyy-MM-dd或者yyyy-MM-dd HH:mm:ss格式的就转成yyyyMMddHHmmss格式，否则返回原字符串
	 * @param str
	 * @return
	 */
	public static String tranStrToDateStr8(String str){
		if(str==null) return null;
		if(str.matches("[0-9]+-[0-9]+-[0-9]+")){
			String curdate = getCurTime();
			String timestamp=curdate.substring(11, 13)+curdate.substring(14, 16)+curdate.substring(17, 19);
			return trans10To8(str)+timestamp;
		}else if(str.matches("[0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+")){
			return str.substring(0, 4) + str.substring(5, 7) + str.substring(8, 10)+str.substring(11, 13)+str.substring(14, 16)+str.substring(17, 19);
		}
		return str;
	}
	
	/**
	 * 将yyyyMMddHHmmss格式转换成yyyy-MM-dd HH:mm:ss格式
	 * @param str
	 * @return
	 */
	public static String tran14ToStr18(String str){
		return trans8To10(str)+" "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14);
	}
	
	/**
	 * 计算两个日期类型的差几天
	 * @param data_little 起始日期字串
	 * @param data_big    终止日期字串
	 * @return  差的天数
	 * @throws ParseException 
	 */
	public static int calDateDayNum(String data_little,String data_big) throws ParseException{
		return calDateDayNum(strToDateTime(data_little), strToDateTime(data_big));
	}
	
	public static int calDateDayNum(Date data_little,Date data_big){
		return (int) ((data_big.getTime()-data_little.getTime())/(1000*60*60*24));
	}
}
