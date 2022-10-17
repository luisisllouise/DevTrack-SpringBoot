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

@RestController
@RequestMapping("/email-task")
public class EmailTaskController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeRecordService verificationCodeRecordService;

    @GetMapping("/send-verification-code")
    public SendVCodeEmailResultVO sendVerificationCodeEmail(VerificationCodeForm form) {
        VCodeResultVO verificationCodeResult = verificationCodeRecordService.signUpVerificationCode(form);
        if (verificationCodeResult.getStatusCode() != StatusCodeEnum.SUCCESS.code)
            return new SendVCodeEmailResultVO(StatusCodeEnum.parse(verificationCodeResult.getStatusCode()));

        String subject = "AuroraLab Verification Code";
//        String text = "<p>AuroraLab</p>" + "\n" +
//                "<span>Your verification code is: " + verificationCodeResult.getResultData().getVerificationCode() + ".</span><br/>" +
//                "<span>The verification code is valid in 5 minutes.</span>";
        String text = "<div>\n" +
                "    <table style=\"width: 100%; border-collapse: collapse; padding: 35px 7%\">\n" +
                "        <tr>\n" +
                "            <td style=\"font-size: 0\">&nbsp;</td>\n" +
                "            <td style=\"width: 600px\">\n" +
                "                <div style=\"font-size: 15px\">\n" +
                "                    <h1>AuroraLab</h1>\n" +
                "                    <h2 style=\"color: #409eff\">Please verify your email address</h2>\n" +
                "                    <p>Your verification code is:</p>\n" +
                "                    <h2 style=\"text-align: center\">" + verificationCodeResult.getResultData().getVerificationCode() + "</h2>\n" +
                "                    <p>To sign up your AuroraLab ID account, please verify your email.</p>\n" +
                "                    <p>The verification code is valid in 5 minutes.</p>\n" +
                "                </div>\n" +
                "                <hr/>\n" +
                "                <div style=\"font-size: 11px; color: #666666\">\n" +
                "                    <p>© 2022 AuroraLab</p>\n" +
                "                    <p>This message was sent to " + form.getEmail() + " by AuroraLab.</p>\n" +
                "                    <p style=\"font-style: italic\">Note: This is an automated notification. Replies to this email address are not monitored.</p>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td style=\"font-size: 0\">&nbsp;</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</div>\n" +
                "\n" +
                "<style>\n" +
                "    h2 {\n" +
                "        font-size: 22px;\n" +
                "    }\n" +
                "</style>";
        emailService.sendEmail(form.getEmail(), subject, text, true);

        return new SendVCodeEmailResultVO(StatusCodeEnum.SUCCESS, ConvertTool.bytesToHexString(verificationCodeResult.getResultData().getUuid()));
    }
}
