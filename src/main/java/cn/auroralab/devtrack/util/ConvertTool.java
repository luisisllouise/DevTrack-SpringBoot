package cn.auroralab.devtrack.util;

public class ConvertTool {
    public static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }
}
