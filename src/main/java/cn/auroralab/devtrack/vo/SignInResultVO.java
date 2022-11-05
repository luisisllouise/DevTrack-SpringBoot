package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.vo.data.UserInformation;

public class SignInResultVO extends ResultVO<UserInformation> {
    public SignInResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public SignInResultVO(StatusCodeEnum statusCodeEnum, UserInformation signInResultData) { super(statusCodeEnum, signInResultData); }
}
