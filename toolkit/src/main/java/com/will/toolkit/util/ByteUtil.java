/*
 * $Header: ByteUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-2 下午04:35:46
 * $Owner: will
 */
package com.will.toolkit.util;

import java.nio.ByteOrder;

/**
 * ByteUtil
 * @author will
 * @version 1.0.0.0 2012-5-2 下午04:35:46
 */
public final class ByteUtil {

    /**
     * 获得指定长度的字节
     * @param l
     * @param length
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:38:23
     */
    public static byte[] intToBytes(int l, int length) {
        if (length % 8 != 0) {
            throw new IllegalArgumentException("illegal args [length:" + length + "]");
        }
        
        byte[] bytes = new byte[length >> 3];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (l >> (8 * (bytes.length - i - 1)));
        }
        return bytes;
    }
    
    /**
     * 字节转换为整型
     * @param bytes
     * @return
     * @version 1.0.0.0 2012-5-2 下午04:40:29
     */
    public static int bytesToInt(byte[] bytes, ByteOrder byteOrder) {
        if (byteOrder.equals(ByteOrder.BIG_ENDIAN)) {
            return    (bytes[0] & 0xff) << 24 
                    | (bytes[1] & 0xff) << 16 
                    | (bytes[2] & 0xff) << 8 
                    |  bytes[3] & 0xff;
        } else {
            return   bytes[0] & 0xff | 
                    (bytes[1] & 0xff) << 8 | 
                    (bytes[2] & 0xff) << 16 | 
                    (bytes[3] & 0xff) << 24;
        }
        
    }
}
