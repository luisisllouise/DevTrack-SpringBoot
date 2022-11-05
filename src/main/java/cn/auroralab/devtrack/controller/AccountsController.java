package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.vo.*;
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

    @GetMapping("/signUp")
    public SignUpResultVO signUp(SignUpForm form) { return accountService.signUp(form); }

    @GetMapping("/signIn")
    public SignInResultVO signIn(SignInForm signInForm) { return accountService.signIn(signInForm); }

    @GetMapping("/editprofile")
    public EditProfileResultVO editProfile(EditProfileForm editProfileForm) { return accountService.editProfile(editProfileForm); }

    @GetMapping("/updateAvatar")
    public AvatarResultVO updateAvatar(AvatarForm form) { return accountService.updateAvatar(form); }

    @GetMapping("/getUserInformation")
    public UserInformationResultVO getUserInformation(String username) {
        if (username == null)
            return new UserInformationResultVO(StatusCodeEnum.REQUIRED_PARAM_IS_NULL);
        return accountService.getUserInformation(username);
    }
}
