package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SignUpResultVO {
    public static SignUpResultVO SUCCESS = new SignUpResultVO(0);
    public static SignUpResultVO USER_EXISTS = new SignUpResultVO(1);
    public static SignUpResultVO UNABLE_TO_CREATE_UUID = new SignUpResultVO(2);
    public static SignUpResultVO VERIFICATION_CODE_ERROR = new SignUpResultVO(3);
    public static SignUpResultVO VERIFICATION_CODE_INVALID = new SignUpResultVO(4);
    public static SignUpResultVO NOT_FIND_VERIFICATION_CODE_RECORD = new SignUpResultVO(5);
    public static SignUpResultVO UNKNOWN_ERROR = new SignUpResultVO(6);

    private final int statusCode;

    private SignUpResultVO(int statusCode) { this.statusCode = statusCode; }
}
