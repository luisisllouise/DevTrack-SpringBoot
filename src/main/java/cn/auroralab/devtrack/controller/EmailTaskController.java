package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.service.EmailService;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
import cn.auroralab.devtrack.util.ConvertTool;
import cn.auroralab.devtrack.util.ResourceFileLoader;
import cn.auroralab.devtrack.vo.SendVCodeEmailResultVO;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import cn.auroralab.devtrack.vo.VCodeResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/send-verification-code")
    public SendVCodeEmailResultVO sendVerificationCodeEmail(VerificationCodeForm form) {
        VCodeResultVO vCodeResultVO = verificationCodeRecordService.signUpVerificationCode(form);
        if (vCodeResultVO.getStatusCode() != StatusCodeEnum.SUCCESS.code)
            return new SendVCodeEmailResultVO(StatusCodeEnum.parse(vCodeResultVO.getStatusCode()));

        LocalDateTime invalidTime = vCodeResultVO.getResultData().getTime().plusMinutes(vCodeResultVO.getResultData().getValidTime());
        String invalidTimeString = invalidTime.getYear() + "-" + invalidTime.getMonthValue() + "-" + invalidTime.getDayOfMonth() + " " +
                invalidTime.getHour() + ":" + invalidTime.getMinute() + ":" + invalidTime.getSecond();

        String subject = "AuroraLab Verification Code";
        String text = ResourceFileLoader.readFile("EmailTemplates/VCodeEmailTemplate.html")
                .replace("{{$vcode}}", vCodeResultVO.getResultData().getvCode())
                .replace("{{$time}}", invalidTimeString)
                .replace("{{$email}}", form.getEmail());

        emailService.initialize();
        emailService.setText(text);
        emailService.addImage("logo", "logo.png");
        emailService.sendEmail(form.getEmail(), subject);

        return new SendVCodeEmailResultVO(StatusCodeEnum.SUCCESS, ConvertTool.bytesToHexString(vCodeResultVO.getResultData().getUuid()));
    }
}
