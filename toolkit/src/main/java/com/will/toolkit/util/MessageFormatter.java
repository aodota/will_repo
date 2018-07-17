/*
 * $Header: MessageFormatter.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-9-28 下午03:52:09
 * $Owner: will
 */
package com.will.toolkit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息格式化器，解决MessageFormate中的bug
 * @author will
 * @version 1.0.0.0 2011-9-28 下午03:52:09
 */
public class MessageFormatter {
    /** 文本Pattern */
    private List<TextPattern> patternList = new ArrayList<TextPattern>();
    
    /** pattern字符串 */
    private String pattern;
    
    /** 位置偏移列表 */
    private List<Integer> offsetList = new ArrayList<Integer>(10);
    
    /** 缓存Map，用于提高性能 */
    private static Map<String, MessageFormatter> cacheMap = new ConcurrentHashMap<String, MessageFormatter>();
    
    /** 锁 */
    private static Object lock = new Object();
    
    /**
     * 构造函数
     * @param pattern
     */
    public MessageFormatter(String pattern) {
        parsePattern(pattern);
    }

   /**
    * 获取格式化字符串
    * @param pattern 字符串pattern
    * @param params 参数列表
    * @return
    * @version 1.0.0.0 2011-9-28 下午04:20:56
    */
    public static String format(String pattern, Object... params) {
        MessageFormatter formatter = cacheMap.get(pattern);
        if (null == formatter) {
            synchronized (lock) {
                formatter = cacheMap.get(pattern);
                if (null == formatter) {
                    formatter = new MessageFormatter(pattern);
                    cacheMap.put(pattern, formatter);
                }
            }
        }
        
        return formatter.format(params);
    }
    
    /**
     * 格式化字符串
     * @param params 填充参数
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:05:50
     */
    private String format(Object... params) {
        int len = pattern.length();
        StringBuilder builder = new StringBuilder(len);
        int lastOffset = 0;
        int i = 0;        
        for (int offset : offsetList) {
            builder.append(pattern.substring(lastOffset, offset));
            int index = patternList.get(i).index;
            if (index < params.length) {
                builder.append(params[index]);
            } else {
                builder.append("{").append(index).append("}");
            }
            
            lastOffset = offset;
            i++;
        }
        
        builder.append(pattern.substring(lastOffset, len));
        return builder.toString();
    }
    
    /**
     * 解析pattern
     * @param pattern Pattern
     * @version 1.0.0.0 2011-9-28 下午04:10:58
     */
    private void parsePattern(String pattern) {
        int braceStack = 0;
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            if (ch == '{' && braceStack == 0) {
                // 第一个花挎号
                braceStack++;
            } else if (braceStack != 0) {
                switch (ch) {
                case '{':
                    braceStack++;
                    builder.append(ch);
                    break;
                case '}':
                    if (braceStack == 1) {
                        offsetList.add(builder.length());
                        patternList.add(new TextPattern(Integer.parseInt(builder1.toString())));
                        builder1.setLength(0);
                        braceStack--;
                        break;
                    } else {
                        braceStack--;
                        builder.append(ch);
                        break;
                    }
                default:
                    builder1.append(ch);
                    break;
                }
            } else {
                builder.append(ch);
            }
        }
        this.pattern = builder.toString();
    }

    private class TextPattern {
        public TextPattern(int index) {
            this.index = index;
        }
        /** 序号 */
        public int index;
    }
}
