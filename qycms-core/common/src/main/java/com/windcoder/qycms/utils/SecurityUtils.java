package com.windcoder.qycms.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

import java.security.SecureRandom;

public class SecurityUtils {
    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * 生成指定为数的随机的Byte[]作为salt.
     * @param numBytes
     * @return
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }


    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }
}
