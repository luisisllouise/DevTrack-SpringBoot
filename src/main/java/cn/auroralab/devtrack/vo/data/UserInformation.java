package cn.auroralab.devtrack.vo.data;

import cn.auroralab.devtrack.entity.Account;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInformation {
    private final String uuid;
    private final String username;
    private final byte[] avatar;
    private final String nickname;
    private final String email;
    private final String phone;
    private final LocalDateTime signUpTime;
    private final LocalDateTime lastSignInTime;

    public UserInformation(Account account) {
        uuid = account.getUuid();
        username = account.getUsername();
        avatar = account.getAvatar();
        nickname = account.getNickname();
        email = account.getEmail();
        phone = account.getPhone();
        signUpTime = account.getSignUpTime();
        lastSignInTime = account.getLastSignInTime();
    }
}
