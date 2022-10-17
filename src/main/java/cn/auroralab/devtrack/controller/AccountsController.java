package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.RuleForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.service.AccountsService;
import cn.auroralab.devtrack.vo.ResultVO;
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
    private AccountsService accountsService;

    @GetMapping("/signup")
    public SignUpResultVO signUp(SignUpForm form) { return accountsService.signUp(form); }

    @GetMapping("/login")
    public ResultVO login(RuleForm ruleForm) {
        ResultVO login_result = accountsService.login(ruleForm);
        return login_result;
    }
}
