package cn.auroralab.devtrack.util;

import lombok.Getter;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class VerificationCodeGenerator {
    /**
     * 验证码的默认有效时间，单位：分钟。
     */
    public static final int DEFAULT_VALID_TIME = 5;

    @Getter
    private final String verificationCode;
    @Getter
    private final LocalDateTime startTime;
    private final int validTime;

    public VerificationCodeGenerator() { this(DEFAULT_VALID_TIME); }

    public VerificationCodeGenerator(int validTime) {
        Random random = new SecureRandom();
        this.verificationCode = String.valueOf(random.nextInt(1000000));
        this.startTime = LocalDateTime.now();
        this.validTime = validTime;
    }

    public boolean isValid(LocalDateTime currentTime) {
        return Duration.between(startTime, currentTime).toMinutes() < validTime;
    }
}
