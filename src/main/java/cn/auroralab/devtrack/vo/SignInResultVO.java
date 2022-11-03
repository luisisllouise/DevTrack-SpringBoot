package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.form.result.SignInResultData;
import lombok.Data;

@Data
public class SignInResultVO extends ResultVO<SignInResultData> {
    public SignInResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public SignInResultVO(StatusCodeEnum statusCodeEnum, SignInResultData signInResultData) { super(statusCodeEnum, signInResultData); }
}
