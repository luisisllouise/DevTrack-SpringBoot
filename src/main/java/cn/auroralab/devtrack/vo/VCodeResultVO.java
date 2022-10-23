package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.VerificationCodeRecord;

public class VCodeResultVO extends ResultVO<VerificationCodeRecord> {
    public VCodeResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public VCodeResultVO(StatusCodeEnum statusCodeEnum, VerificationCodeRecord record) { super(statusCodeEnum, record); }
}
