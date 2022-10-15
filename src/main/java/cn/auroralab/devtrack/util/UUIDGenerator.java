package cn.auroralab.devtrack.util;

import java.util.UUID;

public class UUIDGenerator {
    public static byte[] getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        int len = uuid.length() / 2;
        char[] uuidChars = uuid.toCharArray();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (ConvertTool.charToByte(uuidChars[pos]) << 4 | ConvertTool.charToByte(uuidChars[pos + 1]));
        }
        return bytes;
    }
}
