package com.lc.utils.other;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Yang Peng<yangpeng@credit.com>
 * @date 2016年4月15日
 */

public class DateUtil {

    // Grace style
    public static final String PATTERN_GRACE = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_GRACE_SIMPLE = "yyyy/MM/dd";

    // Classical style
    public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static final String PATTERN_CHINESE = "yyyy年MM月dd日";

    public static Date getCurrentDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date;
    }

    /**
     * 根据默认格式将指定字符串解析成日期
     *
     * @param str 指定字符串
     * @return 返回解析后的日期
     */
    public static Date parse(String str) {
        return parse(str, PATTERN_CLASSICAL);
    }

    /**
     * 根据指定格式将指定字符串解析成日期
     *
     * @param str     指定日期
     * @param pattern 指定格式
     * @return 返回解析后的日期
     */
    public static Date parse(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 根据默认格式将日期转格式化成字符串
     *
     * @param date 指定日期
     * @return 返回格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, PATTERN_CLASSICAL);
    }

    /**
     * 根据指定格式将指定日期格式化成字符串
     *
     * @param date    指定日期
     * @param pattern 指定格式
     * @return 返回格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    /**
     * 根据年，月，日返回日期
     *
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static Date getDate(int year, int month, int date) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(year, month, date);
        return currentDate.getTime();
    }


    /**
     * 获取重置指定日期的时分秒后的时间
     *
     * @param date   指定日期
     * @param hour   指定小时
     * @param minute 指定分钟
     * @param second 指定秒
     * @return 返回重置时分秒后的时间
     */
    public static Date getResetTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, minute);
        cal.set(Calendar.MINUTE, second);
        return cal.getTime();
    }

    /**
     * 返回指定日期的起始时间
     *
     * @param date 指定日期（例如2014-08-01）
     * @return 返回起始时间（例如2014-08-01 00:00:00）
     */
    public static Date getIntegralStartTime(Date date) {
        return getResetTime(date, 0, 0, 0);
    }

    /**
     * 返回指定日期的结束时间
     *
     * @param date 指定日期（例如2014-08-01）
     * @return 返回结束时间（例如2014-08-01 23:59:59）
     */
    public static Date getIntegralEndTime(Date date) {
        return getResetTime(date, 23, 59, 59);
    }


    /**
     * 获取前一个工作日
     *
     * @return 返回前一个工作日
     */
    public static Date getPrevWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取下一个工作日
     *
     * @return 返回下个工作日
     */
    public static Date getNextWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取当周的第一个工作日
     *
     * @return 返回第一个工作日
     */
    public static Date getFirstWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取当周的最后一个工作日
     *
     * @return 返回最后一个工作日
     */
    public static Date getLastWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 判断指定日期是否是工作日
     *
     * @param date 指定日期
     * @return 如果是工作日返回true，否则返回false
     */
    public static boolean isWorkday(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return !(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * 获取指定日期是星期几
     *
     * @param date 指定日期
     * @return 返回星期几的描述
     */
    public static String getWeekdayDesc(Date date) {
        final String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return weeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取指定日期累加年月日后的时间
     *
     * @param date  指定日期
     * @param year  指定年数
     * @param month 指定月数
     * @param day   指定天数
     * @return 返回累加年月日后的时间
     */
    public static Date addDate(Date date, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 获取指定日期累加时分秒后的时间
     *
     * @param date   指定日期
     * @param hour   指定小时
     * @param minute 指定分钟
     * @param second 指定秒数
     * @return 返回累加年月日后的时间
     */
    public static Date addTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR, hour);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 指定时间加上秒
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        return addTime(date, 0, 0, second);
    }

    /**
     * 指定时间加上分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        return addTime(date, 0, minute, 0);
    }

    /**
     * 指定时间加上小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        return addTime(date, 0, 0, hour);
    }


    /**
     * 指定日期加上年数
     *
     * @param date
     * @param year
     * @return 计算后日期
     */
    public static Date addYear(Date date, int year) {
        return addDate(date, year, 0, 0);
    }

    /**
     * 指定日期加上月数
     *
     * @param date
     * @param month
     * @return 计算后日期
     */
    public static Date addMonth(Date date, int month) {
        return addDate(date, 0, month, 0);
    }

    /**
     * @param date
     * @param day
     * @return 计算后日期
     */
    public static Date addDay(Date date, int day) {
        return addDate(date, 0, 0, day);
    }


    /**
     * 获取时间date1与date2相差的毫秒数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的秒数
     */
    public static long getOffsetMilliseconds(Date date1, Date date2) {
        long millisecond = date2.getTime() - date1.getTime();
        return millisecond;
    }

    /**
     * 获取时间date1与date2相差的秒数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的秒数
     */
    public static int getOffsetSeconds(Date date1, Date date2) {
        return (int) (getOffsetMilliseconds(date1, date2) / 1000);
    }

    /**
     * 获取时间date1与date2相差的分钟数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的分钟数
     */
    public static int getOffsetMinutes(Date date1, Date date2) {
        return getOffsetSeconds(date1, date2) / 60;
    }

    /**
     * 获取时间date1与date2相差的小时数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的小时数
     */
    public static int getOffsetHours(Date date1, Date date2) {
        return getOffsetMinutes(date1, date2) / 60;
    }

    /**
     * 获取时间date1与date2相差的天数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的天数
     */
    public static int getOffsetDays(Date date1, Date date2) {
        return getOffsetHours(date1, date2) / 24;
    }

    /**
     * 获取时间date1与date2相差的周数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的周数
     */
    public static int getOffsetWeeks(Date date1, Date date2) {
        return getOffsetDays(date1, date2) / 7;
    }


    /**
     * 获取指定日期距离当前时间的时间差描述（如3小时前、1天前）
     *
     * @param date 指定日期
     * @return 返回时间差的描述
     */
    public static String getTimeOffsetDesc(Date date) {
        int seconds = getOffsetSeconds(date, getCurrentDate());
        if (Math.abs(seconds) < 60) {
            return Math.abs(seconds) + "秒" + (seconds > 0 ? "前" : "后");
        }
        int minutes = seconds / 60;
        if (Math.abs(minutes) < 60) {
            return Math.abs(minutes) + "分钟" + (minutes > 0 ? "前" : "后");
        }
        int hours = minutes / 60;
        if (Math.abs(hours) < 60) {
            return Math.abs(hours) + "小时" + (hours > 0 ? "前" : "后");
        }
        int days = hours / 24;
        if (Math.abs(days) < 7) {
            return Math.abs(days) + "天" + (days > 0 ? "前" : "后");
        }
        int weeks = days / 7;
        if (Math.abs(weeks) < 5) {
            return Math.abs(weeks) + "周" + (weeks > 0 ? "前" : "后");
        }
        int monthes = days / 30;
        if (Math.abs(monthes) < 12) {
            return Math.abs(monthes) + "个月" + (monthes > 0 ? "前" : "后");
        }
        int years = monthes / 12;
        return Math.abs(years) + "年" + (years > 0 ? "前" : "后");
    }

    public static Date getStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Integer getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static Integer getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static String GMT(String s){
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date=sdf1.parse(s);
            SimpleDateFormat sdf=new SimpleDateFormat(PATTERN_CLASSICAL);
            s=sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) {
        String a="Fri Mar 24 18:11:34 GMT+08:00 2017";
        try {
            String d=DateUtil.GMT(a);
            System.out.println(d);
        }catch (Exception e){
e.printStackTrace();
        }

    }

}
