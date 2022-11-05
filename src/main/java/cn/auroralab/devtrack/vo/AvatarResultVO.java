package cn.auroralab.devtrack.vo;

public class AvatarResultVO extends ResultVO {
    public AvatarResultVO(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum);
    }

    public AvatarResultVO(StatusCodeEnum statusCodeEnum, String filename) {
        super(statusCodeEnum, filename);
    }
}
