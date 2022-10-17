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

    public ResultVO() { this(StatusCode.UNKNOWN_ERROR); }

    public ResultVO(StatusCode statusCode) { this(statusCode, null); }

    public ResultVO(StatusCode statusCode, TResultData resultData) {
        this.statusCode = statusCode.code;
        this.resultData = resultData;
    }
}
