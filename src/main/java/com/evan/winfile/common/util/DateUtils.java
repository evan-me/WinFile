/**
 * @Title: DateUtils.java
 * @Package com.gzshili.hlxj.util
 * @author Evan
 * @date Dec 6, 2016 4:00:23 PM
 * @version V1.0
 */
package com.evan.winfile.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @author Evan
 * @ClassName: DateUtils
 * @date Dec 6, 2016 4:00:23 PM
 */
public class DateUtils {

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE = "yyyy-MM-dd";

    public static final String TIME = "HH:mm:ss";

    public static final String YEAR = "yyyy";

    public static final String MONTH = "yyyy-MM";


    private static final Object OBJECT = new Object();

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) {
        synchronized (OBJECT) {
            return new SimpleDateFormat(pattern);
        }
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isTheDay(date, DateUtils.now());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now(long time) {
        return new Date(time);
    }

    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= DateUtils.dayBegin(day).getTime() && date.getTime() <= DateUtils.dayEnd(day).getTime();
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        synchronized (OBJECT) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 000);
            return c.getTime();
        }
    }

    /**
     * 获取指定时间当月第一天的 00:00:00.000 的时间
     *
     * @param
     * @return
     */
    public static Date getMonthBegin(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        gcLast.set(Calendar.HOUR_OF_DAY, 0);
        gcLast.set(Calendar.MINUTE, 0);
        gcLast.set(Calendar.SECOND, 0);
        gcLast.set(Calendar.MILLISECOND, 0);
        return gcLast.getTime();
    }

    /**
     * 获取指定时间当月最后一天的 00:00:00.000 的时间
     *
     * @param
     * @return
     */
    public static Date getMonthEnd(Date d) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @return Date
     */
    public static Date getYearBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, getYear(date));
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @return Date
     */
    public static Date getYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 转换到字符串
     *
     * @param date
     * @param fmt  日期格式化字符串
     * @return
     */
    public static String toString(final Date date, String fmt) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat sdf = getDateFormat(fmt);
        return sdf.format(date);
    }

    /**
     * 从字符串解析为日期型
     *
     * @param s
     * @param fmt
     * @return
     */
    public static Date parse(final String s, final String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取年份 @Title: getYear @Description: @param date @return @throws
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份 @Title: getMonth @Description: @param date @return @throws
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日 @Title: getDay @Description: @param date @return @throws
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获取小时 @Title: getHours @Description: @param date @return @throws
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟 @Title: getMinute @Description: @param date @return @throws
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒 @Title: getSecond @Description: @param date @return @throws
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 计算两个时间的时和分 @Title: getDiffersTime @Description: @param @param
     * beginDate @param @param endDate @param @return @return String
     * "HH小时MM分钟" @throws
     */
    public static String getDiffersTime(Date beginDate, Date endDate) {
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        long differ = endTime - beginTime;
        // long a = differ/1000/60/60;
        int hour = Math.abs((int) (differ / 1000 / 60 / 60));
        long minuteTime = Math.abs(differ - hour * 60 * 60 * 1000L);
        int minute = Math.abs((int) (minuteTime / 1000 / 60));
        return hour + "小时" + minute + "分钟";
    }

    /**
     * 计算两个时间的时和分 @Title: getDiffersTime @Description: @param @param
     * beginDate @param @param endDate @param @return @return String
     * "HH小时MM分钟" @throws
     */
    public static String getDiffersTimes(Date beginDate, Date endDate) {
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        long differ = endTime - beginTime;
        int hour = Math.abs((int) (differ / 1000 / 60 / 60));
        long minuteTime = Math.abs(differ - hour * 60 * 60 * 1000L);
        int minute = Math.abs((int) (minuteTime / 1000 / 60));
        return hour + ":" + minute + ":" + "00";
    }

    /**
     * 一个时间的时和分 @Title: getDiffersTime @Description: @param @param
     * beginDate @param @param endDate @param @return @return String
     * "HH小时MM分钟" @throws
     */
    public static String getDiffersTime(long totalTime) {
        if (totalTime == 0L) {
            return "0小时 0分钟";
        }
        // long a = differ/1000/60/60;
        int hour = Math.abs((int) (totalTime / 1000 / 60 / 60));
        long minuteTime = Math.abs(totalTime - hour * 60 * 60 * 1000L);
        int minute = Math.abs((int) (minuteTime / 1000 / 60));
        return hour + "小时 " + minute + "分钟";
    }

    public static synchronized int compareDate(final Date a, final Date b) {
        // 上一个记录的当天23:59分
        long oldDateStr = DateUtils.dayEnd(a).getTime();
        // 新时间
        long newDateStr = b.getTime();
        if (newDateStr > oldDateStr) {
            // 另一天的数据
            return 0;
        } else if (a.getTime() > b.getTime()) {
            // 历史轨迹
            return 1;
        } else {
            // 正常轨迹数据
            return 2;
        }
    }

    public static int getTotalDaysOfMonth(int mon) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, mon - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startTime 开始日期
     * @param endTime   结束日趋
     * @return
     */
    public static Long minuteDiff(Date startTime, Date endTime) {
        try {
            Long diff = (endTime.getTime() - startTime.getTime()) / 1000 / 60;
            // 返回相差的天数
            return diff;
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startTime 开始日期
     * @param endTime   结束日趋
     * @return
     */
    public static Integer dateDiff(Date startTime, Date endTime) {
        try {
            // 一天的毫秒数
            Float nd = new Float(1000 * 24 * 60 * 60);
            // 计算相差多少毫秒
            Float diff = new Float(endTime.getTime() - startTime.getTime());
            // 计算差多少天
            Float day = diff / nd;
            // 返回相差的天数
            return day.intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @Title: isTheFirstDay @Description: @param countDate @return @throws
     */
    public static boolean isTheFirstDay(Date countDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(countDate);
        return c.get(Calendar.DAY_OF_MONTH) == 1;
    }

    /**
     * @return thisDate > anotherDate 返回1；
     * thisDate = anotherDate返回0；
     * thisDate < anotherDate返回-1；
     * @Title: compare2Date
     * @Description: 比较相个日期的大小，
     * thisDate > anotherDate 返回1；
     * thisDate = anotherDate返回0；
     * thisDate < anotherDate返回-1；
     * @param: thisDate 日期
     * @param: anotherDate 要对比的日期
     */
    public static int compare2Date(Date thisDate, Date anotherDate) {
        return thisDate.compareTo(anotherDate);
    }

    /**
     * 获取下月一号
     *
     * @param date
     * @return
     */
    public static Date firstDayOfNextMonth(Date date) {
        //获得入参的日期
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        //获取下个月第一天：
        cd.add(Calendar.MONTH, 1);
        //设置为1号,当前日期既为次月第一天
        cd.set(Calendar.DAY_OF_MONTH, 1);

        return cd.getTime();
    }

    /**
     * 获取下月第一天的日期字符串
     *
     * @param year
     * @param month
     * @return
     */
    public static String nextMonthOneDayDate(String year, String month) {
        // 下个月第一天的日期
        String nextMonthOneDayDate = "";
        if ("12".equals(month)) {
            nextMonthOneDayDate = (Integer.parseInt(year) + 1) + "-01-01";
        } else {
            int nextMonth = Integer.parseInt(month) + 1;
            nextMonthOneDayDate = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth) + "-01";
        }
        return nextMonthOneDayDate;
    }

    /**
     * 获取当月第一天的日期字符串
     *
     * @param year
     * @param month
     * @return
     */
    public static String nowMonthOneDayDate(String year, String month) {
        // 当月第一条的日期
        String nowMonthOneDayDate = year + "-" + (month.length() == 1 ? "0" + month : month) + "-01";
        return nowMonthOneDayDate;
    }

    /**
     * 获取明年第一天日期
     *
     * @param year
     * @return
     */
    public static String nextYearOneDayDate(String year) {
        // 下年第一天的日期
        return (Integer.parseInt(year) + 1) + "-01-01";
    }

    /**
     * 获取今年第一天日期
     *
     * @param year
     * @return
     */
    public static String nowYearOneDayDate(String year) {
        // 今年第一天的日期
        return year + "-01-01";
    }

    /**
     * 获取当月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int numberOfDayToMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 把日期设置为当月第一天
        cal.set(Calendar.DATE, 1);
        // 日期回滚一天，也就是最后一天
        cal.roll(Calendar.DATE, -1);
        return cal.get(Calendar.DATE);
    }

    /**
     * 通过传入的日期获取第二天的日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String tomorrow(String year, String month, String day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(day) + 1);
        Date date = cal.getTime();
        return toString(date, DATE);
    }

    /**
     * 获取当前的日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String today(String year, String month, String day) {
        // 当月第一条的日期
        return year + "-" + (month.length() == 1 ? "0" + month : month) + "-" + (day.length() == 1 ? "0" + day : day);
    }

    /**
     * 获取一个设定的大时间
     *
     * @return
     * @throws ParseException
     */
    public static Date outTime() {
        SimpleDateFormat dataFormat = getDateFormat(DATE);
        Date outTime = null;
        try {
            outTime = dataFormat.parse("2099-01-01");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outTime;
    }

}
