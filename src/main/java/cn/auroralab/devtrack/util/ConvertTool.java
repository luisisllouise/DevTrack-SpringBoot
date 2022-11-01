package cn.auroralab.devtrack.util;

import java.math.BigInteger;

/**
 * 转换工具类。用于转换进制字符串。
 */
public class ConvertTool {
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    /**
     * 将十六进制字符串转换为二进制数组，每一个元素代表0-0xff。
     *
     * @param s 十六进制字符串。
     * @return 二进制数组。
     */
    public static byte[] hexStringToBytes(String s) {
        if (s.length() % 2 != 0) s = '0' + s;
        int len = s.length() / 2;
        char[] chars = s.toLowerCase().toCharArray();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (charToByte(chars[pos]) << 4 | charToByte(chars[pos + 1]));
        }
        return bytes;
    }

    /**
     * 将二进制数组转换为十六进制字符串。
     *
     * @param bytes 二进制数组。
     * @return 十六进制字符串。
     */
    public static String bytesToHexString(byte[] bytes) {
        String str = new BigInteger(1, bytes).toString(16);
        return "0".repeat(bytes.length * 2 - str.length()) + str;
    }
}
