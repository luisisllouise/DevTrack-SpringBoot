package cn.auroralab.devtrack.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarForm {
    private String username;
    private MultipartFile file;
}
