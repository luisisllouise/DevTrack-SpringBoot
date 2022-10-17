package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.RuleForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.vo.SignInResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账号信息 前端控制器
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/signup")
    public SignUpResultVO signUp(SignUpForm form) { return accountService.signUp(form); }

    @GetMapping("/login")
    public SignInResultVO login(RuleForm ruleForm) {
        SignInResultVO login_result = accountService.login(ruleForm);
        return login_result;
    }
}
