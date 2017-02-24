package com.will.toolkit.codec;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by WILL on 2016/5/15.
 */
public class CodecTest {
    @Test
    public void testCoder() {
        Assert.assertEquals("900150983cd24fb0d6963f7d28e17f721",  CodecUtil.md5("abc"));
    }
}
