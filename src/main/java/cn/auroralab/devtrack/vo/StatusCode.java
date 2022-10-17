package cn.auroralab.devtrack.vo;

import java.util.Arrays;
import java.util.Optional;

/**
 * 状态码枚举
 *
 * @author Guanyu Hu
 * @since 2022-10-17
 */
public enum StatusCode {
    UNKNOWN_ERROR(0),           // 未知错误

    SUCCESS(100),               // 成功

    // 用户业务
    USER_EXISTS(201),           // 用户已存在
    USER_NOT_EXISTS(202),       // 用户不存在
    USER_PASSWORD_ERROR(203),   // 密码错误

    //验证码业务
    VCODE_ERROR(301),           // 验证码错误
    VCODE_INVALID(302),         // 验证码失效（由于超时）
    VCODE_NO_RECORD(303),       // 没有验证码记录（无效的验证码业务uuid）

    // 后台业务
    UUID_CONFLICT(901),         // 生成uuid时发生碰撞
    ;
    public final int code;

    StatusCode(int code) { this.code = code; }

    public static StatusCode parse(int code) {
        Optional<StatusCode> any = Arrays.stream(StatusCode.class.getEnumConstants())
                .filter(e -> e.code == code).findAny();
        return any.orElse(UNKNOWN_ERROR);
    }
}
