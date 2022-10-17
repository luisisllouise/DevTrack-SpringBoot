package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SendVerificationCodeEmailResultVO {
    private boolean success;
    private byte[] taskUUID;
}
