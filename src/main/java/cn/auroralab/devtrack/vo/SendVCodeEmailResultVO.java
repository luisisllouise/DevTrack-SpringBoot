package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SendVCodeEmailResultVO extends ResultVO<String> {
    public SendVCodeEmailResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public SendVCodeEmailResultVO(StatusCodeEnum statusCodeEnum, String taskUUID) { super(statusCodeEnum, taskUUID); }
}
