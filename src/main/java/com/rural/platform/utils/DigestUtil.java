package com.rural.platform.utils;

import java.security.MessageDigest;

/**
 * MD5 加密工具类
 * 提供标准的 MD5 哈希计算，结果返回 32 位小写十六进制字符串。
 */
public class DigestUtil {

    private static final String MD5_ALGORITHM = "MD5";
    private static final String CHARSET = "UTF-8";

    // 私有构造方法，禁止实例化
    private DigestUtil() {}

    /**
     * 计算字符串的 MD5 哈希值
     * @param input 待加密的字符串
     * @return 32 位小写 MD5 值，如 "e10adc3949ba59abbe56e057f20f883e"
     * @throws RuntimeException 如果加密失败
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] digest = md.digest(input.getBytes(CHARSET));
            return bytesToHex(digest);
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    // 兼容旧版方法（可选）
    @Deprecated
    public static String hmacSign(String input) {
        return md5(input);
    }

    @Deprecated
    public static String hmacSign(String input, String key) {
        return md5(input); // 忽略 key
    }
}