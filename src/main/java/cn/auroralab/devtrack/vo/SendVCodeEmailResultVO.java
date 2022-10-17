package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SendVCodeEmailResultVO extends ResultVO<byte[]> {
    public SendVCodeEmailResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public SendVCodeEmailResultVO(StatusCodeEnum statusCodeEnum, byte[] taskUUID) { super(statusCodeEnum, taskUUID); }
}
