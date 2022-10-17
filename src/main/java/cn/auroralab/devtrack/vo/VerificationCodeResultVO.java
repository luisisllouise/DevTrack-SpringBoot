package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.VerificationCodeRecord;

public class VerificationCodeResultVO extends ResultVO<VerificationCodeRecord> {
    public VerificationCodeResultVO(StatusCode statusCode) { super(statusCode); }

    public VerificationCodeResultVO(StatusCode statusCode, VerificationCodeRecord record) { super(statusCode, record); }
}
