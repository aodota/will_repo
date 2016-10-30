/*
 * $Header: DateUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-2 下午04:11:23
 * $Owner: will
 */
package com.will.toolkit.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 * @author will
 * @version 1.0.0.0 2012-5-2 下午04:11:23
 */
public final class DateUtil {
    public static final String DATETIME_FULL_SLASH_S = "yyyy/MM/dd HH:mm:ss,S";
    public static final String DATETIME_FULL_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String DATETIME_HM_SLASH = "yyyy/MM/dd HH:mm";
    public static final String DATE_FULL_SLASH = "yyyy/MM/dd";
    public static final String DATE_YM_SLASH = "yyyy/MM";
    
    public static final String DATETIME_FULL_HYPHEN_S = "yyyy-MM-dd HH:mm:ss,S";
    public static final String DATETIME_FULL_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_HM_HYPHEN = "yyyy-MM-dd HH:mm";
    public static final String DATE_FULL_HYPHEN = "yyyy-MM-dd";
    public static final String DATE_YM_HYPHEN = "yyyy-MM";
    
    public static final String DATETIME_FULL_COMPACT_S = "yyyyMMddHHmmssS";
    public static final String DATETIME_FULL_COMPACT = "yyyyMMddHHmmss";
    public static final String DATETIME_HM_COMPACT = "yyyyMMddHHmm";
    public static final String DATE_FULL_COMPACT = "yyyyMMdd";
    public static final String DATE_YM_COMPACT = "yyyyMM";
    
    public static final String DATE_YY = "yy";
    public static final String DATE_HM = "HH:mm";
    public static final String DATE_MS = "mm:S";
    
    public static final String DATETIME_FULL_HYPHEN_CN = "yyyy年MM月dd日 HH点mm分";

    /** 1s对应的毫秒数 */
    public static final int SEC = 1000;
    /** 1分钟对应的毫秒数 */
    public static final int MINUTE_SEC = 60 * SEC;
    /** 1小时对应的毫秒数 */
    public static final int HOUR_SEC = 60 * MINUTE_SEC;
    /** 1天对应的毫秒数 */
    public static final int DAY_SEC = 24 * HOUR_SEC;
    
    /**
     * 格式化Date
     * @param date
     * @param pattern
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:12:45
     */
    public static String formatDate(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        
        if (StringUtils.isBlank(pattern)) {
            return null;
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }
    
    /**
     * 解析指定字符串为日期类型
     * @param dateStr
     * @param pattern
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:25:18
     * @throws ParseException 
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }
    
    /**
     * 判断两个日期是否是同一天
     * @param date1
     * @param date2
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:30:06
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cg1 = Calendar.getInstance();
        Calendar cg2 = Calendar.getInstance();
        cg1.setTime(date1);
        cg2.setTime(date2);
        
        return cg1.get(Calendar.YEAR) == cg2.get(Calendar.YEAR) && cg1.get(Calendar.DAY_OF_YEAR) == cg2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 计算2个日期之间间隔的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cg1 = Calendar.getInstance();
        Calendar cg2 = Calendar.getInstance();
        cg1.setTime(date1);
        cg2.setTime(date2);
        cg1 = getHourDate(cg1, 0);
        cg2 = getHourDate(cg2, 0);

        return (int) ((cg1.getTimeInMillis() - cg2.getTimeInMillis()) / DAY_SEC);
    }

    /**
     * 获取小时为指定时间的日期
     * @param hour
     * @return
     */
    public static Date getHourDate(int hour) {
        Calendar cg = Calendar.getInstance();
        cg.set(Calendar.HOUR_OF_DAY, hour);
        cg.set(Calendar.MINUTE, 0);
        cg.set(Calendar.SECOND, 0);
        cg.set(Calendar.MILLISECOND, 0);

        return cg.getTime();
    }

    /**
     * 获取小时为指定时间的日期
     * @param hour
     * @return
     */
    public static Date getHourDate(Date date, int hour) {
        Calendar cg = Calendar.getInstance();
        cg.setTime(date);
        cg.set(Calendar.HOUR_OF_DAY, hour);
        cg.set(Calendar.MINUTE, 0);
        cg.set(Calendar.SECOND, 0);
        cg.set(Calendar.MILLISECOND, 0);

        return cg.getTime();
    }

    /**
     * 获取小时为指定时间的日期
     * @return
     */
    public static Calendar getHourDate(Calendar cg, int hour) {
        cg.set(Calendar.HOUR_OF_DAY, 0);
        cg.set(Calendar.MINUTE, 0);
        cg.set(Calendar.SECOND, 0);
        cg.set(Calendar.MILLISECOND, 0);

        return cg;
    }

}
