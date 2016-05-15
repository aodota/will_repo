/*
 * $Header: CodecUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-11-19 下午03:20:23
 * $Owner: will
 */
package com.will.toolkit.codec;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * CodecUtil 
 * 集成了常用的加密，解密，签名方法
 * @author will
 * @version 1.0.0.0 2012-11-19 下午03:20:23
 */
public final class CodecUtil {
    /**
     * MD2签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String md2(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.MD2);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("md5 encrypt error", e);
        }
    }
    
    /**
     * MD5签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String md5(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.MD5);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("md5 encrypt error", e);
        }
    }
    
    /**
     * SHA-1签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String sha1(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.SHA_1);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("sha encrypt error", e);
        }
    }
    
    /**
     * SHA-256签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String sha256(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.SHA_256);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("sha encrypt error", e);
        }
    }
    
    /**
     * SHA-384签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String sha384(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.SHA_384);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("sha encrypt error", e);
        }
    }
    
    /**
     * SHA-512签名
     * @param str 签名字符串
     * @return
     * @version 1.0.0.0 2012-11-19 下午03:21:18
     */
    public static String sha512(String str) {
        byte[] bytes;
        try {
            bytes = DigestUtil.digest(str, Algorithms.SHA_512);
            
            return HexUtil.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("sha encrypt error", e);
        }
    }
    
    /**
     * DES加密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] desEncrypt(String desKey, String data) {
        return encrypt(desKey, 8, Algorithms.DES, data);
    }
    
    /**
     * DES加密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static String desEncryptHex(String desKey, String data) {
        return HexUtil.encodeHexString(encrypt(desKey, 8, Algorithms.DES, data));
    }
    
    /**
     * DES解密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] desDecrypt(String desKey, byte[] data) {
        return decrypt(desKey, 8, Algorithms.DES, data);
    }
    
    /**
     * DES解密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] desDecryptHex(String desKey, String data) {
        return decrypt(desKey, 8, Algorithms.DES, HexUtil.decodeHex(data.toCharArray()));
    }
    
    
    /**
     * DES3加密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] des3Encrypt(String desKey, String data) {
        return encrypt(desKey, 24, Algorithms.DES3, data);
    }
    
    /**
     * DES3加密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static String des3EncryptHex(String desKey, String data) {
        return HexUtil.encodeHexString(encrypt(desKey, 24, Algorithms.DES3, data));
    }
    
    /**
     * DES3解密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] des3Decrypt(String desKey, byte[] data) {
        return decrypt(desKey, 24, Algorithms.DES3, data);
    }
    
    /**
     * DES3解密
     * @param desKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] des3DecryptHex(String desKey, String data) {
        return decrypt(desKey, 24, Algorithms.DES3, HexUtil.decodeHex(data.toCharArray()));
    }
    
    /**
     * AES加密
     * @param aesKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] aesEncrypt(String aesKey, String data) {
        return encrypt(aesKey, 16, Algorithms.AES, data);
    }
    
    /**
     * AES加密
     * @param aesKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static String aesEncryptHex(String aesKey, String data) {
        return HexUtil.encodeHexString(encrypt(aesKey, 16, Algorithms.AES, data));
    }
    
    /**
     * AES解密
     * @param aesKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] aesDecrypt(String aesKey, byte[] data) {
        return decrypt(aesKey, 16, Algorithms.AES, data);
    }
    
    /**
     * AES解密
     * @param aesKey
     * @param data
     * @version 1.0.0.0 2012-11-19 下午04:24:14
     * @throws Exception 
     */
    public static byte[] aesDecryptHex(String aesKey, String data) {
        return decrypt(aesKey, 16, Algorithms.AES, HexUtil.decodeHex(data.toCharArray()));
    }
    
    /**
     * 获取RSA私钥
     * @param key
     * @return
     * @throws Exception
     * @version 1.0.0.0 2012-11-15 下午8:37:18
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        try {
            byte[] keyBytes = Base64.decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException("get privateKey error", e);
        }
    }
    
    /**
     * 获取公钥
     * @param key
     * @return
     * @throws Exception
     * @version 1.0.0.0 2012-11-15 下午8:37:18
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        try {
            byte[] keyBytes = Base64.decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            
            return publicKey;
        } catch (Exception e) {
            throw new RuntimeException("get publicKey error", e);
        }
    }
    
    /**
     * 公钥加密
     * @param publicKey
     * @param data
     * @return 加密结果
     */
    public static byte[] rsaEncryptByPublicKey(RSAPublicKey publicKey, String data) {
        if (publicKey != null) {
            try {
                // Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance(Algorithms.RSA);
                // 根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] resultBytes = cipher.doFinal(data.getBytes("utf-8"));
                
                return resultBytes;
            } catch (Exception e) {
                throw new RuntimeException("rsa encrypt error", e);
            }
        }
        throw new RuntimeException("key can't be null");
    }
    
    /**
     * 公钥加密
     * @param publicKey
     * @param data
     * @return 加密结果
     */
    public static String rsaEncryptByPublicKeyHex(RSAPublicKey publicKey, String data) {
        return HexUtil.encodeHexString(rsaEncryptByPublicKey(publicKey, data));
    }
    
    /**
     * 私钥加密
     * @param privateKey
     * @param data
     * @return 加密结果
     */
    public static byte[] rsaEncryptByPrivateKey(RSAPrivateKey privateKey, String data) {
        if (privateKey != null) {
            try {
                // Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance(Algorithms.RSA);
                // 根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                byte[] resultBytes = cipher.doFinal(data.getBytes("utf-8"));
                
                return resultBytes;
            } catch (Exception e) {
                throw new RuntimeException("rsa encrypt error", e);
            }
        }
        throw new RuntimeException("key can't be null");
    }
    
    /**
     * 私钥加密
     * @param privateKey
     * @param data
     * @return 加密结果
     */
    public static String rsaEncryptByPrivateKeyHex(RSAPrivateKey privateKey, String data) {
        return HexUtil.encodeHexString(rsaEncryptByPrivateKey(privateKey, data));
    }
    
    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return 解密结果
     * @version 1.0.0.0 2012-11-17 上午1:31:30
     */
    public static byte[] rsaDecryptByPublicKey(PublicKey publicKey, byte[] data) {
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(Algorithms.RSA);
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
                byte[] resultBytes = cipher.doFinal(data);
                
                return resultBytes;
            } catch (Exception e) {
                throw new RuntimeException("rsa decrypt error", e);
            }
        }
        
        throw new RuntimeException("key can't be null");
    }
    
    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return 解密结果
     * @version 1.0.0.0 2012-11-17 上午1:31:30
     */
    public static byte[] rsaDecryptByPublicKeyHex(PublicKey publicKey, String data) {
        return rsaDecryptByPublicKey(publicKey, HexUtil.decodeHex(data.toCharArray()));
    }
    
    /**
     * 私钥解密 
     * @param privateKey
     * @param data
     * @return 解密结果
     */
    public static byte[] rsaDecryptByPrivateKey(RSAPrivateKey privateKey, byte[] data) {
        if (privateKey != null) {
            try {
                // Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance(Algorithms.RSA);
                // 根据公钥，对Cipher对象进行初始化
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] resultBytes = cipher.doFinal(data);
                
                return resultBytes;
            } catch (Exception e) {
                throw new RuntimeException("rsa decrypt error", e);
            }
        }
        
        throw new RuntimeException("key can't be null");
    }
    
    /**
     * 私钥解密 
     * @param privateKey
     * @param data
     * @return 解密结果
     */
    public static byte[] rsaDecryptByPrivateKeyHex(RSAPrivateKey privateKey, String data) {
        return rsaDecryptByPrivateKey(privateKey, HexUtil.decodeHex(data.toCharArray()));
    }
    
    /**
     * 加密
     * @param keyStr 加密所用的Key
     * @param keyLen 加密Key所限制的长度
     * @param algorithm 加密算法
     * @param data 加密数据
     * @return
     * @version 1.0.0.0 2012-11-19 下午05:03:38
     */
    protected static byte[] encrypt(String keyStr, int keyLen, String algorithm, String data) {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            Key key = getKey(keyStr.getBytes(), algorithm, keyLen);

            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            return encryptCipher.doFinal(data.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException("encrypt error", e);
        }
    }
    
    /**
     * 解密
     * @param keyStr 解密所用的Key
     * @param keyLen 解密Key所限制的长度
     * @param algorithm 解密算法
     * @param data 解密数据
     * @return
     * @version 1.0.0.0 2012-11-19 下午05:03:38
     */
    protected static byte[] decrypt(String keyStr, int keyLen, String algorithm, byte[] data) {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            Key key = getKey(keyStr.getBytes(), algorithm, keyLen);

            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.DECRYPT_MODE, key);
            return encryptCipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("encrypt error", e);
        }
    }

    /**
     * DES  密钥所需长度为8位
     * DES3 密钥所需长度为24位
     * 从指定字符串生成密钥，取密钥所需的字节数组长度不足时后面补0，超出时截取指定位
     * 
     * @param keysData 构成Key的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    protected static Key getKey(byte[] keysData, String algorithm, int len) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] keys = new byte[len];

        // 将原始字节数组转换为8位
        for (int i = 0; i < keysData.length && i < keys.length; i++) {
            keys[i] = keysData[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(keys, algorithm);
        return key;
    }
    
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String msg = "郭德纲-精品相声技术12zsd宣传发改委";
        String result = new String(des3EncryptHex("abcdef", msg));
        String result1 = new String(des3DecryptHex("abcdef", result));
        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + result + ", " + result.length());
        System.out.println("密文是：" + result1 + ", " + result1.length());
        
        PublicKey publicKey = getPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs/nB3qN/mddivHPO1A2GWCMTtxgb9fXwiopbfzEOPgh2mIqqBThSgcPAL8cpF4457hmNpkiE7+aWzErip/6idbzZOeVVqD+O6ix/WLiSiAABOkKtmp539qqq0tqirKJhzSp3x4G6j47ERzOMjrssUj9rgV5U4nfowebAMo9M6itWPowYRe0OYDS6577FVUJH/f0n/EdHpRlVlx5jy29dN2rp+7eKEFg8dXPLH/6STKqRJDuMnDgtZTEtTW5D2il99WSHuD0mN+wZ0paDRJVmf7bPu9L+c3FyQtWIFA9RaBfv+7WZHN+Lm+OUsDchtvlOYBcuvYiRay1660KQBIdt9wIDAQAB");
        String data = "lpi5Uloh9oXSZ1M9xAk+3+DVHX+xrvNR4h7szCxQX4WTlXnr/1oR3F813ptsxqecnbXToJuw8YIUtBOh+lMDfLT+lXGFBqFf7hWyllCz0tSLZBWwri2CQCDy/qamEujZ7B1Yw1CdybyASqPC907sBS9bSlydbnQmTW0MMcXaauzDoxVoyKItKfgrtKtSNizAYMv58LJZNoJfdjM3S5g+rZo1NkXx/7/eqkBL9ZWIBvV5QWlT3Kg5AYa1DnHpY+jct1hLxxMmW68L0ijXQpfonckEcx4TPwCdBmcUfwusRrcKLJ+mLRWmw/ICzCwTpDVeIqGcpronx7dh0hkrRsb6pQ==";
        byte[] bytes = rsaDecryptByPublicKey(publicKey, Base64.decode(data.getBytes()));
        System.out.println(new String(bytes));
    }
}
