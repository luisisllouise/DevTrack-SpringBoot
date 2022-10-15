package cn.auroralab.devtrack.util;

import java.util.UUID;

public class UUIDGenerator {
    public static byte[] getUUID() {
        return ConvertTool.hexStringToBytes(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
