package cn.auroralab.devtrack.vo;

import lombok.Data;

/**
 * 业务返回的数据视图
 *
 * @param <TResultData> 结果数据对象的类型
 * @author Guanyu Hu
 * @since 2022-10-17
 */
@Data
public class ResultVO<TResultData> {
    private final int statusCode;
    private final TResultData resultData;

    public ResultVO() { this(StatusCodeEnum.UNKNOWN_ERROR); }

    public ResultVO(StatusCodeEnum statusCodeEnum) { this(statusCodeEnum, null); }

    public ResultVO(StatusCodeEnum statusCodeEnum, TResultData resultData) {
        this.statusCode = statusCodeEnum.code;
        this.resultData = resultData;
    }
}
