package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.service.EmailService;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
import cn.auroralab.devtrack.vo.SendVerificationCodeEmailResultVO;
import cn.auroralab.devtrack.vo.VerificationCodeResultVO;
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
    public SendVerificationCodeEmailResultVO sendVerificationCodeEmail(VerificationCodeForm form) {
        SendVerificationCodeEmailResultVO resultVO = new SendVerificationCodeEmailResultVO();
        var verificationCodeResult = verificationCodeRecordService.signUpVerificationCode(form);

        if (!verificationCodeResult.equals(VerificationCodeResultVO.SUCCESS)) {
            resultVO.setSuccess(false);
            return resultVO;
        }

        String subject = "AuroraLab Verification Code";
        String text = "<p>AuroraLab</p>" + "\n" +
                "<span>Your verification code is: " + verificationCodeResult.getVerificationCodeRecord().getVerificationCode() + ".</span><br/>" +
                "<span>The verification code is valid in 5 minutes.</span>";
        emailService.sendEmail(form.getEmail(), subject, text, true);

        resultVO.setSuccess(true);
        resultVO.setTaskUUID(verificationCodeResult.getVerificationCodeRecord().getUuid());
        return resultVO;
    }
}
