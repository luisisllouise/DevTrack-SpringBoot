package cn.auroralab.devtrack.service;

import org.springframework.core.io.Resource;

/**
 * 邮件服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
public interface EmailService {
    /**
     * 邮件服务初始化。
     *
     * @author Guanyu Hu
     * @since 2022-10-22
     */
    void initialize();

    /**
     * 以HTML模式设置邮件正文。
     *
     * @param text 正文文本。
     * @author Guanyu Hu
     * @since 2022-10-22
     */
    default void setText(String text) { setText(text, true); }

    /**
     * 设置邮件正文。
     *
     * @param text 正文文本。
     * @param html 是否开启HTML模式。
     * @author Guanyu Hu
     * @since 2022-10-22
     */
    void setText(String text, boolean html);

    void addImage(String cid, String classPath);

    void addImage(String cid, Resource resource);

    default void sendEmail(String toEmail, String subject) { sendEmail("AuroraLab", toEmail, subject); }

    void sendEmail(String senderName, String toEmail, String subject);
}
