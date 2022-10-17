package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.Account;
import lombok.Data;

@Data
public class SignInResultVO extends ResultVO<Account> {
    public SignInResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public SignInResultVO(StatusCodeEnum statusCodeEnum, Account account) { super(statusCodeEnum, account); }
}
