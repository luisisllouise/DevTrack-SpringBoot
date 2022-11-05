package cn.auroralab.devtrack.service;

import org.springframework.core.io.Resource;

/**
 * 邮件服务类。
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

    /**
     * 向邮件正文中添加图片。
     *
     * @param cid       资源cid号。
     * @param classPath 图片在resources/下的相对路径。
     * @author Guanyu Hu
     * @since 2022-11-03
     */
    void addImage(String cid, String classPath);

    /**
     * 向邮件正文中添加图片。
     *
     * @param cid      资源cid号。
     * @param resource 图片资源对象。
     * @author Guanyu Hu
     * @since 2022-10-22
     */
    void addImage(String cid, Resource resource);

    /**
     * 向指定邮箱发送邮件。
     *
     * @param toEmail 收件邮箱。
     * @param subject 邮件主题。
     * @author Guanyu Hu
     * @since 2022-10-22
     */
    default void sendEmail(String toEmail, String subject) { sendEmail("AuroraLab", toEmail, subject); }

    /**
     * 向指定邮箱发送邮件。
     *
     * @param senderName 发件人名字。
     * @param toEmail    收件邮箱。
     * @param subject    邮件主题。
     * @author Guanyu Hu
     * @since 2022-10-16
     */
    void sendEmail(String senderName, String toEmail, String subject);
}
