package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.service.EmailService;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
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
        String text = "<p>AuroraLab</p>" + "\n" +
                "<span>Your verification code is: " + verificationCodeResult.getResultData().getVerificationCode() + ".</span><br/>" +
                "<span>The verification code is valid in 5 minutes.</span>";
        emailService.sendEmail(form.getEmail(), subject, text, true);

        return new SendVCodeEmailResultVO(StatusCodeEnum.SUCCESS, verificationCodeResult.getResultData().getUuid());
    }
}
