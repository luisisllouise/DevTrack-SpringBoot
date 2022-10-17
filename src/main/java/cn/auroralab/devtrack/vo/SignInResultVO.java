package cn.auroralab.devtrack.vo;

import lombok.Data;

@Data
public class SignInResultVO<T> {
    private Integer code;
    private T data;
}
