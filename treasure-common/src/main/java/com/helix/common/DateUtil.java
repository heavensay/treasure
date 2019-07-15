package com.helix.common;

import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil extends PropertyEditorSupport {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";

    private String dateFormat = DATE_TIME_FORMAT;

    public static Date getCurrentDate() {
        return new Date();
    }

    // private static SimpleDateFormat sDateFormat = new
    // SimpleDateFormat("yyyy-MM-dd");

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        log.info("set Text");
        SimpleDateFormat frm = new SimpleDateFormat(dateFormat);
        try {
            Date date = frm.parse(text);
            this.setValue(date);
        } catch (Exception exp) {
            log.error("parse date error ", exp);
        }
    }

    public static Date parse(String text) throws ParseException {
        return parse(text, DATE_FORMAT);
    }

    public static Date parse(String text, String formatString) throws ParseException {
        SimpleDateFormat frm = new SimpleDateFormat(formatString);
        return frm.parse(text);
    }

    public static String parse(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public static String parse(Date date, String formatString) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static Calendar getDateZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal;
    }

    public static Date add(Date date, int zoom, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(zoom, amount);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date getExpireDay() {
        return getDate("2099-12-31 23:59:59");
    }

    public static Date getDate(String date) {
        return getDate(date, DATE_TIME_FORMAT);
    }

    public static Date getDate(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            log.error("parse date error", e);
        }

        return null;
    }

    /**
     * 计算两个日期相隔的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDayCount(Date date1, Date date2) {
        Assert.notNull(date1);
        Assert.notNull(date2);
        Calendar cal1 = getDateZeroCalendar(date1);
        Calendar cal2 = getDateZeroCalendar(date2);
        long between_days = Math.abs((cal1.getTime().getTime() - cal2.getTime().getTime()) / (1000 * 3600 * 24));

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getDate(Date date) {
        return getDate(date, DATE_TIME_FORMAT);
    }

    public static String getDate(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 星期三为3，星期天为7
     *
     * @param date
     * @return
     */
    public static int getDayNumber(Date date) {
        int week = getDay(date) - 1;
        return week == 0 ? 7 : week;
    }


    /**
     * 获取今天开始时间
     *
     * @return
     */
    public static Date getStartTimeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取今天结束时间
     *
     * @return
     */
    public static Date getEndTimeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取前几个月
     *
     * @param date
     * @return
     * @version 1.0
     * @author yangh
     * @created 2013年10月16日
     */
    public static Date getLastMonthStartDay(Date date, int lastMonthCount) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, month - lastMonthCount);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getDayStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static boolean isToday(Date date) {
        Date d = new Date();
        return DateUtil.getDate(date, YYYYMMDD).equals(DateUtil.getDate(d, YYYYMMDD));
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getMaxMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 得到某一天上一周的星期天，中国每周是以星期一开始。
     *
     * @return
     */
    public static Date getPreWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 如果当前日期不是周日则自动往后递增日期
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_WEEK, -1);
        }
        return cal.getTime();
    }

    /**
     * 转化字符串
     *
     * @param currentDate
     * @param dateTimeFormat
     * @return
     */
    public static String toString(Date currentDate, String dateTimeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
        return simpleDateFormat.format(currentDate);
    }

}
