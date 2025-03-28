package com.rural.platform.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 加密工具类 -- 提供了MD5加密算法
 */
public class DigestUtil {

    // 定义默认的字符编码
    private static String encodingCharset = "UTF-8";

    /**
     * 对参数数据进行MD5加密的算法
     * @param aValue 待加密的数据
     * @return 加密后的数据
     */
    public static String hmacSign(String aValue) {
        return hmacSign(aValue, "warehouse");
    }

    /**
     * 使用密钥对参数数据进行MD5加密的算法
     * @param aValue 待加密的数据
     * @param aKey 加密密钥
     * @return 加密后的数据
     */
    public static String hmacSign(String aValue, String aKey) {
        byte k_ipad[] = new byte[64];
        byte k_opad[] = new byte[64];
        byte keyb[];
        byte value[];
        try {
            keyb = aKey.getBytes(encodingCharset);
            value = aValue.getBytes(encodingCharset);
        } catch (UnsupportedEncodingException e) {
            keyb = aKey.getBytes();
            value = aValue.getBytes();
        }

        Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
        Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
        for (int i = 0; i < keyb.length; i++) {
            k_ipad[i] = (byte) (keyb[i] ^ 0x36);
            k_opad[i] = (byte) (keyb[i] ^ 0x5c);
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte dg[] = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param input 字节数组
     * @return 十六进制字符串
     */
    public static String toHex(byte input[]) {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }
}
