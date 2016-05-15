/*
 * $Header: FileUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-2 下午04:56:02
 * $Owner: will
 */
package com.will.toolkit.file;



/**
 * FileUtil
 * @author will
 * @version 1.0.0.0 2012-5-2 下午04:56:02
 */
public final class FileUtil {
    /** 长度占的位数  */
    public static final int LENGTH = 32;
    
    /** 长度的字节数 */
    public static final int BIT_LENGTH = LENGTH >> 3;

    /** 存放路径 */
    private static String path;

    /**
     * 获取文件路径
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:56:07
     */
    public static String getPath() {
        return path;
    }

    /**
     * 设置文件存放路径
     * @param path
     * @return
     */
    public synchronized static void setPath(String path) {
        if (null == FileUtil.path) {
            FileUtil.path = path;
        }
    }
    
    /**
     * 存储到文件
     * @param bytes
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:59:29
     */
    public static String saveToFile(byte[] bytes) {
        return FileWriter.getInstance().writeFile(bytes);
    }
    
    /**
     * 从文件中读取
     * @param id
     * @return
     * @version 1.0.0.0 2012-5-2 下午05:00:12
     */
    public static byte[] getContent(String id) {
        return FileReader.getInstance().readFile(id);
    }

}
