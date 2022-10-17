package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.VerificationCodeRecord;

public class VCodeResultVO extends ResultVO<VerificationCodeRecord> {
    public VCodeResultVO(StatusCode statusCode) { super(statusCode); }

    public VCodeResultVO(StatusCode statusCode, VerificationCodeRecord record) { super(statusCode, record); }
}
