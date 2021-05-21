package com.liicon.utils.lang;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.text.format.Time;

/**
 * 日期处理工具类
 * @version  v0.1  king  2014-11-18  日期处理
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
    
    /**
     * 返回日期间隔天数
     * @param begin 起始日期，格式：yyyy-MM-dd
     * @param end 结束日期，格式：yyyy-MM-dd
     * @return
     */
    public static long getIntervalDays(String begin, String end) {
        // 开始日期
        String[] beginParts = begin.split("-");
        if (beginParts.length < 3) {
            return -1;
        }
        int intBeginYear = Integer.parseInt(beginParts[0]);
        int intBeginMonth = Integer.parseInt(beginParts[1]);
        int intBeginDay = Integer.parseInt(beginParts[2]);
        
        // 结束日期
        String[] endParts = begin.split("-");
        if (endParts.length < 2) {
            return -1;
        }
        int intEndYear = Integer.parseInt(endParts[0]);
        int intEndMonth = Integer.parseInt(endParts[1]);
        int intEndinDay = Integer.parseInt(endParts[2]);
        
        // 时间戳
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(intBeginYear, intBeginMonth, intBeginDay);
        long startMilliseconds = cal.getTimeInMillis();
        
        cal.set(intEndYear, intEndMonth, intEndinDay);
        long endMilliseconds = cal.getTimeInMillis();
        
        // 间隔
        return (startMilliseconds - endMilliseconds) / (3600 * 24 * 1000);
    }
    
    /**
     * 返回间隔月份
     * @param begin 起始日期，格式：yyyy-MM
     * @param end 结束日期，格式：yyyy-MM
     * @return
     */
    public static int getInterval(String begin, String end) {
        // 开始
        String[] beginParts = begin.split("-");
        if (beginParts.length < 2) {
            return -1;
        }
        int intBeginYear = Integer.parseInt(beginParts[0]);
        int intBeginMonth = Integer.parseInt(beginParts[1]);
        
        // 结束
        String[] endParts = begin.split("-");
        if (endParts.length < 2) {
            return -1;
        }
        int intEndYear = Integer.parseInt(endParts[0]);
        int intEndMonth = Integer.parseInt(endParts[1]);
        
        // 间隔
        return ((intEndYear - intBeginYear) * 12)
                + (intEndMonth - intBeginMonth) + 1;
    }
    
    

    /**
     * 文本解析为日期
     * @param date
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parse(String date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        return fmt.parse(date, pos);
    }
    
    
    
    /**
     * 格式化日期+时间（"yyyy-MM-dd HH:mm:ss"）
     * @return
     */
    public static String format(Date date) {
        return dateFormat.format(date);
    }
    /**
     * 格式化日期（"yyyy-MM-dd"）
     * @param date
     * @return
     */
    public static String formatSimple(Date date) {
        return dateFormatSimple.format(date);
    }
    /**
     * 格式化
     * @param date
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 格式化日期字符串
     * @param date 日期字符串：yyyy-MM-dd
     * @param format 格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String format(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(java.sql.Date.valueOf(date));
    }
    
    
    
    
    
    
    
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将字符串转位日期类型
     * @param date "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * @param defValue
     * @return
     */
    public static Date parse(String date, Date defValue) {
        Date value = defValue;
        if (!StringUtils.isEmpty(date)) {
            try {
                value = dateFormat.parse(date);
            } catch (ParseException e) {
                try {
                    value = dateFormatSimple.parse(date);
                } catch (ParseException e1) { }
            }
        }
        if (value == null) {
            value = defValue;
        }
        return value;
    }

    /**
     * 以友好的方式显示时间
     * @param date
     * @return
     */
    public static String toFriendlyTime(String date) {
        Date time = parse(date, (Date)null);
        if (time == null) {
            return "Unknown";
        }
        String ftime = dateFormatSimple.format(time);;
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormatSimple.format(cal.getTime());
        String paramDate = dateFormatSimple.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormatSimple.format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     * @param date "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * @return boolean
     */
    public static boolean isToday(String date) {
        boolean bool = false;
        Date time = parse(date, (Date)null);
        if (time != null) {
            Date today = new Date();
            String nowDate = dateFormatSimple.format(today);
            String timeDate = dateFormatSimple.format(time);
            if (nowDate.equals(timeDate)) {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * 判断给定时间戳是否为今日
     * @param when 时间戳
     * @return boolean
     */
    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }
    
    
}
