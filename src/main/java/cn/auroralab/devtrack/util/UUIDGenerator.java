package cn.auroralab.devtrack.util;

import java.util.UUID;

/**
 * UUID生成器。
 *
 * @author Guanyu Hu
 * @since 2022-10-15
 */
public class UUIDGenerator {
    /**
     * 根据时间戳获取版本4的uuid。
     *
     * @return 32位uuid字符串。
     * @author Guanyu Hu
     * @since 2022-10-15
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
}
