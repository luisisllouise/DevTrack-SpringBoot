package cn.auroralab.devtrack.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证码业务映射表
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("verification_code_records")
public class VerificationCodeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验证码业务uuid
     */
    private byte[] taskUuid;

    /**
     * 验证码业务类型
     */
    private Integer taskType;

    /**
     * 接收验证码的邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 业务时间
     */
    private LocalDateTime taskTime;
}
