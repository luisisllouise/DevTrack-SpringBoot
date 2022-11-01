package cn.auroralab.devtrack.util;

import lombok.Getter;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class VCodeGenerator {
    private static final int DEFAULT_VERIFICATION_CODE_LENGTH = 6;
    /**
     * 验证码的默认有效时间，单位：分钟。
     */
    public static final int DEFAULT_VALID_TIME = 5;

    @Getter
    private final LocalDateTime startTime;
    @Getter
    private final String verificationCode;
    @Getter
    private final int validTime;

    public VCodeGenerator() { this(DEFAULT_VALID_TIME); }

    public VCodeGenerator(int validTime) {
        Random random = new SecureRandom();
        this.startTime = LocalDateTime.now();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DEFAULT_VERIFICATION_CODE_LENGTH; i++)
            stringBuilder.append(random.nextInt(10));
        this.verificationCode = stringBuilder.toString();
        this.validTime = validTime;
    }

    public static boolean isValid(LocalDateTime startTime, LocalDateTime currentTime, int validTime) {
        return Duration.between(startTime, currentTime).toMinutes() < validTime;
    }
}
