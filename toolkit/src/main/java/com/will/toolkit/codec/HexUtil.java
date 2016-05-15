/*
 * $Header: HexUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-11-19 下午03:47:40
 * $Owner: will
 */
package com.will.toolkit.codec;


/**
 * HexUtil
 * @author will
 * @version 1.0.0.0 2012-11-19 下午03:47:40
 */
public final class HexUtil {
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    /**
     * Hex decode
     * @param data
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:50:10
     */
    public static byte[] decodeHex(char[] data) {
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * Hex encode
     * @param data
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:51:58
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * Hex encode
     * @param data
     * @param toLowerCase
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:52:26
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * Hex encode
     * @param data
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:52:57
     */
    public static String encodeHexString(byte[] data) {
        return encodeHexString(data, true);
    }
    
    /**
     * Hex encode
     * @param data
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:52:57
     */
    public static String encodeHexString(byte[] data, boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    /**
     * Hex encode
     * @param data
     * @param toDigits
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:52:35
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
    
    /**
     * char转换为数字
     * @param ch
     * @param index
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:53:05
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }
}
