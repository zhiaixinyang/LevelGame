package com.mdove.levelgame.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author MDove on 2018/2/10.
 */
public class DateUtils {

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long FOUR_HOURS_IN_MILLIS = HOUR_IN_MILLIS * 4;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
    public static final long MONTH_IN_MILLIS = DAY_IN_MILLIS * 30;

    /**
     * This constant is actually the length of 364 days, not of a year!
     */
    public static final long YEAR_IN_MILLIS = WEEK_IN_MILLIS * 52;
    private static ThreadLocal<DateFormat> DEFAULT_DATE_FORMAT = new ThreadLocalDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ThreadLocalDateFormat extends ThreadLocal<DateFormat> {
        private String mDatePattern;

        public ThreadLocalDateFormat(String datePattern) {
            super();
            mDatePattern = datePattern;
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(mDatePattern, Locale.US);
        }
    }

    public static int getDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getYear(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.YEAR);
    }

    public static String formatMinsS(long timestamp) {
        if (getHour(timestamp) <= 0) {
            return String.format("%dmins%ds",
                    TimeUnit.MILLISECONDS.toMinutes(timestamp),
                    TimeUnit.MILLISECONDS.toSeconds(timestamp) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timestamp))
            );
        }
        return simpleFormat(TimeZone.getDefault().getDisplayName(), timestamp, "HH:mm:ss");
    }

    public static int getHour(long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static long getRemainTime() {
        Date currentDate = new Date();
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(midnight.DAY_OF_MONTH, 1);
        midnight.set(midnight.HOUR_OF_DAY, 0);
        midnight.set(midnight.MINUTE, 0);
        midnight.set(midnight.SECOND, 0);
        midnight.set(midnight.MILLISECOND, 0);
        return (midnight.getTime().getTime() - currentDate.getTime());
    }

    public static int getMonth(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static String getDateChineseYMD(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }

    public static String getDateChineseYMD(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }


    public static String formatDefault(long time) {
        return DEFAULT_DATE_FORMAT.get().format(new Date(time));
    }

    public static String simpleFormat(@Nullable String timeZone, long time, @NonNull String datePattern) {
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat(datePattern);
        if (timeZone != null) {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return format.format(date);
    }

    public static String getYearMonth(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(time);
        return format.format(date);
    }

    public static String simpleFormat(long time, @NonNull String datePattern) {
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat(datePattern);
        return format.format(date);
    }

    public static String simpleFormat(@NonNull String datePattern) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(datePattern);
        return format.format(date);
    }

    public static boolean isInDay(long time) {
        long current = System.currentTimeMillis();
        if (current - time <= DAY_IN_MILLIS && current - time > 0) {
            return true;
        }
        return false;
    }

    public static boolean isFourHours(long time) {
        long current = System.currentTimeMillis();
        if (current - time <= FOUR_HOURS_IN_MILLIS && current - time > 0) {
            return true;
        }
        return false;
    }

    public static boolean isSameDay(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time1));
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(new Date(time2));
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return day1 == day2;
    }

    public static boolean isToday(long time) {
        return isSameDay(time, System.currentTimeMillis());
    }

    public static int getHourOfDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取晚上9点半的时间戳
     *
     * @return
     */
    public static long getTimes(int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getTimes(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static int getMinute(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.MINUTE);
    }

    public static int getDayInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(String timeZone, long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getSimpleMonthE(boolean abbrev) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return parseMonthE(month, abbrev);
    }

    public static String getSimpleMonthC(boolean abbrev) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return parseMonthC(month, abbrev);
    }

    public static String getDayOfWeek(long time) {
        String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    //HH为24小时进制
    public static String getDateChinese(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }

    public static String getDateChineseNoH(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }

    public static String getDateChinese(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(time);
    }

    public static String getHourM(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    public static String getHourMChinese(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH小时mm分");
        return format.format(time);
    }

    public static String getDateChinese() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(new Date());
    }

    private static String parseMonthE(int month, boolean abbrev) {
        switch (month) {
            case Calendar.JANUARY:
                return abbrev ? "Ja." : "January";
            case Calendar.FEBRUARY:
                return abbrev ? "Feb." : "February";
            case Calendar.MARCH:
                return abbrev ? "Mar." : "March";
            case Calendar.APRIL:
                return abbrev ? "Apr." : "April";
            case Calendar.MAY:
                return abbrev ? "May." : "May";
            case Calendar.JUNE:
                return abbrev ? "Jun." : "June";
            case Calendar.JULY:
                return abbrev ? "Jul." : "July";
            case Calendar.AUGUST:
                return abbrev ? "Aug." : "August";
            case Calendar.SEPTEMBER:
                return abbrev ? "Sep." : "September";
            case Calendar.OCTOBER:
                return abbrev ? "Oct." : "October";
            case Calendar.NOVEMBER:
                return abbrev ? "Nov." : "November";
            case Calendar.DECEMBER:
                return abbrev ? "Dec." : "December";
            default:
                return "";
        }
    }

    private static String parseMonthC(int month, boolean allChinese) {
        switch (month) {
            case Calendar.JANUARY:
                return !allChinese ? "1月" : "一月";
            case Calendar.FEBRUARY:
                return !allChinese ? "2月" : "二月";
            case Calendar.MARCH:
                return !allChinese ? "3月" : "三月";
            case Calendar.APRIL:
                return !allChinese ? "4月" : "四月";
            case Calendar.MAY:
                return !allChinese ? "5月" : "五月";
            case Calendar.JUNE:
                return !allChinese ? "6月" : "六月";
            case Calendar.JULY:
                return !allChinese ? "7月" : "七月";
            case Calendar.AUGUST:
                return !allChinese ? "8月" : "八月";
            case Calendar.SEPTEMBER:
                return !allChinese ? "9月" : "九月";
            case Calendar.OCTOBER:
                return !allChinese ? "10月" : "十月";
            case Calendar.NOVEMBER:
                return !allChinese ? "11月" : "十一月";
            case Calendar.DECEMBER:
                return !allChinese ? "12月" : "十二月";
            default:
                return "";
        }
    }

    public static String getSimpleWeek(boolean abbrev) {
        Calendar calendar = Calendar.getInstance();
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        return parseWeek(weekday, abbrev);
    }

    private static String parseWeek(int weekday, boolean abbrev) {
        String dayInWeek;
        switch (weekday) {
            case Calendar.SUNDAY:
                dayInWeek = abbrev ? "周日" : "星期天";
                break;
            case Calendar.MONDAY:
                dayInWeek = abbrev ? "周一" : "星期一";
                break;
            case Calendar.TUESDAY:
                dayInWeek = abbrev ? "周二" : "星期二";
                break;
            case Calendar.WEDNESDAY:
                dayInWeek = abbrev ? "周三" : "星期三";
                break;
            case Calendar.THURSDAY:
                dayInWeek = abbrev ? "周四" : "星期四";
                break;
            case Calendar.FRIDAY:
                dayInWeek = abbrev ? "周五" : "星期五";
                break;
            case Calendar.SATURDAY:
                dayInWeek = abbrev ? "周六" : "星期六";
                break;
            default:
                dayInWeek = abbrev ? "周日" : "星期天";
                break;
        }
        return dayInWeek;
    }

    public static String getSimpleWeek(@Nullable String timeZone, long timestamp, boolean abbrev) {
        if (TextUtils.isEmpty(timeZone)) {
            timeZone = TimeZone.getDefault().getDisplayName();
        }
        int dayOfWeek = getDayOfWeek(timeZone, timestamp);
        return parseWeek(dayOfWeek, abbrev);
    }
}
