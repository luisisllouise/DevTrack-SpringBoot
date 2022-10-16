package cn.auroralab.devtrack.service;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String text);

    void sendEmail(String toEmail, String subject, String text, boolean html);

    void sendEmail(String senderName, String toEmail, String subject, String text);

    void sendEmail(String senderName, String toEmail, String subject, String text, boolean html);
}
