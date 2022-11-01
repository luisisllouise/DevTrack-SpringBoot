package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.VCodeRecord;

public class VCodeResultVO extends ResultVO<VCodeRecord> {
    public VCodeResultVO(StatusCodeEnum statusCodeEnum) { super(statusCodeEnum); }

    public VCodeResultVO(StatusCodeEnum statusCodeEnum, VCodeRecord record) { super(statusCodeEnum, record); }
}
