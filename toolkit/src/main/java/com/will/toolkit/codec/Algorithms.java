/*
 * $Header: MessageDigestUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-11-19 下午04:02:11
 * $Owner: will
 */
package com.will.toolkit.codec;

/**
 * Algorithms
 * @author will
 * @version 1.0.0.0 2012-11-19 下午04:02:11
 */
public final class Algorithms {
    private Algorithms() {
    }

    /**
     * The MD2 message digest algorithm defined in RFC 1319.
     */
    public static final String MD2 = "MD2";

    /**
     * The MD5 message digest algorithm defined in RFC 1321.
     */
    public static final String MD5 = "MD5";

    /**
     * The SHA-1 hash algorithm defined in the FIPS PUB 180-2.
     */
    public static final String SHA_1 = "SHA-1";

    /**
     * The SHA-256 hash algorithm defined in the FIPS PUB 180-2.
     */
    public static final String SHA_256 = "SHA-256";

    /**
     * The SHA-384 hash algorithm defined in the FIPS PUB 180-2.
     */
    public static final String SHA_384 = "SHA-384";

    /**
     * The SHA-512 hash algorithm defined in the FIPS PUB 180-2.
     */
    public static final String SHA_512 = "SHA-512";
    
    /**
     * The DES algorithm
     */
    public static final String DES = "DES";
    
    /**
     * The DES3 algorithm
     */
    public static final String DES3 = "DESede";
    
    /**
     * The AES algorithm
     */
    public static final String AES = "AES";
    
    /**
     * The RSA algorithm
     */
    public static final String RSA = "RSA";
}
