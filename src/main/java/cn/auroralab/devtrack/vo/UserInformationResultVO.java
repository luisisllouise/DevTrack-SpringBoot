package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.vo.data.UserInformation;

public class UserInformationResultVO extends ResultVO<UserInformation> {
    public UserInformationResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public UserInformationResultVO(StatusCodeEnum statusCodeEnum, UserInformation userInformation) { super(statusCodeEnum, userInformation); }
}
