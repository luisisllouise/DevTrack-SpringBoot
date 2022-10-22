package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
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

    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public void initialize() {
        mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        setText(text, true);
    }

    public void setText(String text, boolean html) {
        try {
            mimeMessageHelper.setText(text, html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void addImage(String cid, String classPath) {
        try {
            mimeMessageHelper.addInline(cid, new DefaultResourceLoader().getResource("classpath:" + classPath));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void addImage(String cid, Resource resource) {
        try {
            mimeMessageHelper.addInline(cid, resource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String toEmail, String subject) {
        sendEmail("AuroraLab", toEmail, subject);
    }

    public void sendEmail(String senderName, String toEmail, String subject) {
        try {
            mimeMessageHelper.setFrom(senderName + "<" + senderEmail + ">");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
