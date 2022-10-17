package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import lombok.Data;

@Data
public class VerificationCodeResultVO {
    public static final VerificationCodeResultVO SUCCESS = new VerificationCodeResultVO(0);
    public static final VerificationCodeResultVO UNABLE_TO_CREATE_UUID = new VerificationCodeResultVO(1);

    private final int statusCode;
    private VerificationCodeRecord verificationCodeRecord;

    private VerificationCodeResultVO(int statusCode) { this.statusCode = statusCode; }
}
