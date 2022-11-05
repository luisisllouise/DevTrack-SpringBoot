package cn.auroralab.devtrack.form;

import lombok.Data;

@Data
public class createProjectFrom {
    String creater_uuid;
    String principal_uuid;
    String taskIdPrefix;
    boolean publicProject;
}
