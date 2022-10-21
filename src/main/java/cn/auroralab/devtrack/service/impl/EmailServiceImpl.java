package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(String toEmail, String subject, String text) {
        sendEmail(toEmail, subject, text, false);
    }

    public void sendEmail(String toEmail, String subject, String text, boolean html) {
        sendEmail("AuroraLab", toEmail, subject, text, html);
    }

    public void sendEmail(String senderName, String toEmail, String subject, String text) {
        sendEmail(senderName, toEmail, subject, text, false);
    }

    public void sendEmail(String senderName, String toEmail, String subject, String text, boolean html) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(senderName + "<" + senderEmail + ">");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, html);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
