package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SendVCodeEmailResultVO extends ResultVO<byte[]> {
    public SendVCodeEmailResultVO(StatusCode statusCode) { super(statusCode); }

    public SendVCodeEmailResultVO(StatusCode statusCode, byte[] taskUUID) { super(statusCode, taskUUID); }
}
