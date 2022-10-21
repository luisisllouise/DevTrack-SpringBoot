package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.service.EmailService;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
import cn.auroralab.devtrack.util.ConvertTool;
import cn.auroralab.devtrack.vo.SendVCodeEmailResultVO;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import cn.auroralab.devtrack.vo.VCodeResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email-task")
public class EmailTaskController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeRecordService verificationCodeRecordService;

    @GetMapping("/send-verification-code")
    public SendVCodeEmailResultVO sendVerificationCodeEmail(VerificationCodeForm form) {
        VCodeResultVO vCodeResultVO = verificationCodeRecordService.signUpVerificationCode(form);
        if (vCodeResultVO.getStatusCode() != StatusCodeEnum.SUCCESS.code)
            return new SendVCodeEmailResultVO(StatusCodeEnum.parse(vCodeResultVO.getStatusCode()));

        LocalDateTime invalidTime = vCodeResultVO.getResultData().getTaskTime().plusMinutes(vCodeResultVO.getResultData().getValidTime());
        String invalidTimeString = invalidTime.getYear() + "-" + invalidTime.getMonthValue() + "-" + invalidTime.getDayOfMonth() + " " +
                invalidTime.getHour() + ":" + invalidTime.getMinute() + ":" + invalidTime.getSecond();

        String subject = "AuroraLab Verification Code";
        String text = "<div>\n" +
                "    <table style=\"width: 100%; border-collapse: collapse; padding: 35px 7%\">\n" +
                "        <tr>\n" +
                "            <td style=\"font-size: 0\">&nbsp;</td>\n" +
                "            <td style=\"width: 600px\">\n" +
                "                <div style=\"font-size: 15px\">\n" +
                "                    <h1><img src=\"cid:logo\" style=\"max-width: 42px; max-height: 42px\"/>AuroraLab</h1>\n" +
                "                    <h2 style=\"font-size: 22px; color: #409eff\">Please verify your email address</h2>\n" +
                "                    <p>Your verification code is:</p>\n" +
                "                    <h2 style=\"font-size: 22px; text-align: center\">" + vCodeResultVO.getResultData().getVerificationCode() + "</h2>\n" +
                "                    <p>To sign up your AuroraLab ID account, please verify your email.</p>\n" +
                "                    <p>\n" +
                "                        The verification code is valid in 5 minutes and will expire on\n" +
                "                        <span style=\"font-weight: bold\">" + invalidTimeString + "</span>.\n" +
                "                    </p>" +
                "                </div>\n" +
                "                <hr/>\n" +
                "                <div style=\"font-size: 11px; color: #666666\">\n" +
                "                    <p>&copy; 2022 AuroraLab</p>\n" +
                "                    <p>This message was sent to " + form.getEmail() + " by AuroraLab.</p>\n" +
                "                    <p style=\"font-style: italic\">Note: This is an automated notification. Replies to this email address are not monitored.</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td style=\"font-size: 0\">&nbsp;</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</div>";
        emailService.sendEmail(form.getEmail(), subject, text, true);

        return new SendVCodeEmailResultVO(StatusCodeEnum.SUCCESS, ConvertTool.bytesToHexString(vCodeResultVO.getResultData().getUuid()));
    }
}
