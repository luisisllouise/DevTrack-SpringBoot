package cn.auroralab.devtrack.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * 业务类型枚举
 *
 * @author Guanyu Hu
 * @since 2022-10-23
 */
public enum TaskTypeEnum {
    UNKNOWN_ERROR(0),       // 未知业务

    SIGN_UP(100),           // 注册

    // 认证业务
    SIGN_IN(201),           // 登录认证
    CHANGE_PASSWORD(202),   // 修改密码认证
    CHANGE_EMAIL(203),      // 修改邮箱认证
    NEW_EMAIL(204),         // 新邮箱认证
    REAL_NAME(205),         // 实名认证
    ;

    public final int code;

    TaskTypeEnum(int code) { this.code = code; }

    public static TaskTypeEnum parse(int code) {
        Optional<TaskTypeEnum> any = Arrays.stream(TaskTypeEnum.class.getEnumConstants())
                .filter(e -> e.code == code).findAny();
        return any.orElse(UNKNOWN_ERROR);
    }
}
