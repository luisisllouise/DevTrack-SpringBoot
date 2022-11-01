package cn.auroralab.devtrack.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import cn.auroralab.devtrack.util.VerificationCodeGenerator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("vcode_records")
public class VCodeRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验证码业务uuid
     */
    @TableId(value = "task_uuid", type = IdType.INPUT)
    private String uuid;
    /**
     * 业务时间
     */
    @TableField(value = "task_time")
    private LocalDateTime time;
    /**
     * 验证码业务类型
     */
    @TableField(value = "task_type")
    private int type;
    /**
     * 接收验证码的邮箱
     */
    private String email;
    /**
     * 验证码
     */
    @TableField(value = "vcode")
    private String vCode;
    /**
     * 验证码有效时间，单位：分钟
     */
    private int validTime;

    public boolean isValid(LocalDateTime currentTime) { return VerificationCodeGenerator.isValid(time, currentTime, validTime); }
}
