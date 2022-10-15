package cn.auroralab.devtrack.controller;


import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.service.AccountsService;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 用户账号信息 前端控制器
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    @GetMapping("/signup")
    @ResponseBody
    public SignUpResultVO signUp(SignUpForm form) { return accountsService.signUp(form); }
}
