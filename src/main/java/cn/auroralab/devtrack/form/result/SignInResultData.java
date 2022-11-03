package cn.auroralab.devtrack.form.result;

import cn.auroralab.devtrack.entity.Account;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
public class SignInResultData {
    private String uuid;
    private String username;
    private byte[] avatar;
    private String nickname;
    private String email;
    private String phone;
    private LocalDateTime registrationTime;
    private LocalDateTime lastSignInTime;

    public SignInResultData(Account account) {
        uuid = account.getUuid();
        username = account.getUsername();
        avatar = account.getAvatar();
        nickname = account.getNickname();
        email = account.getEmail();
        phone = account.getPhone();
        registrationTime = account.getRegistrationTime();
        lastSignInTime = account.getLastSignInTime();
    }
}
