package cn.auroralab.devtrack.form;

import lombok.Data;

@Data
public class SignUpForm {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String verificationCode;
}
