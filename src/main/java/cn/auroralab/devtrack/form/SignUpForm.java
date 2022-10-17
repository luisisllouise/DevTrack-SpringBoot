package cn.auroralab.devtrack.form;

import lombok.Data;

/**
 * 前端注册表单
 *
 * @author Guanyu Hu
 */
@Data
public class SignUpForm {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String verificationCode;
    private String verificationCodeRecordUUID;
}
