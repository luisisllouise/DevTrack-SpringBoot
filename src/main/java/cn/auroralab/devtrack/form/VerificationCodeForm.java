package cn.auroralab.devtrack.form;

import lombok.Data;

@Data
public class VerificationCodeForm {
    private String email;
    private int taskType;
}
