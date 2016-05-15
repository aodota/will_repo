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
}
