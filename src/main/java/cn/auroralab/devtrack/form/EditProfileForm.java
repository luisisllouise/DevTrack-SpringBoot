package cn.auroralab.devtrack.form;

import lombok.Data;

@Data
public class EditProfileForm {
    private String username;
    private String nickname;
    private String old_password;
    private String new_password;
    private String phone;
    private String email;
    private String verificationCode;
    private String verificationCodeRecordUUID;
}
