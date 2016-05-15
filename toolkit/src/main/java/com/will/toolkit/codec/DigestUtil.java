/*
 * $Header: MessageDigestUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-11-19 下午04:15:47
 * $Owner: will
 */
package com.will.toolkit.codec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DigestUtil
 * @author will
 * @version 1.0.0.0 2012-11-19 下午04:15:47
 */
class DigestUtil {
    /**
     * 摘要签名
     * @param input 输入串
     * @param algorithm 摘要算法
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    static byte[] digest(String input, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] srcBytes = input.getBytes("utf-8");
        
        // 使用srcBytes更新摘要
        digest.update(srcBytes);
        // 完成哈希计算，得到result
        byte[] resultBytes = digest.digest();
        
        return resultBytes;
    }
}
