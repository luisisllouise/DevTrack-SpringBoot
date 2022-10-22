package cn.auroralab.devtrack.service;

import org.springframework.core.io.Resource;

public interface EmailService {
    void initialize();

    void setText(String text);

    void setText(String text, boolean html);

    void addImage(String cid, String classPath);

    void addImage(String cid, Resource resource);

    void sendEmail(String toEmail, String subject);

    void sendEmail(String senderName, String toEmail, String subject);
}
