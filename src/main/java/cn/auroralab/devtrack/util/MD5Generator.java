package cn.auroralab.devtrack.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5生成器。
 *
 * @author Guanyu Hu
 * @since 2022-10-15
 */
public class MD5Generator {
    /**
     * 获取字符串的消息摘要。
     *
     * @param str 字符串。
     * @return 字符串的消息摘要。
     * @author Guanyu Hu
     * @since 2022-10-15
     */
    public static byte[] getMD5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) { }
        return new byte[16];
    }
}
