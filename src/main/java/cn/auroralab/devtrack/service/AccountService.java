package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户账号信息 服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
public interface AccountService extends IService<Account> {
    SignUpResultVO signUp(SignUpForm form);

    SignInResultVO login(SignInForm signInForm);

    EditProfileResultVO editProfile(EditProfileForm editProfileForm);

    AvatarResultVO updateAvatar(AvatarForm form);

    /**
     * 获取用户信息。
     *
     * @param username 用户名。
     * @return 包含状态码和用户信息的对象。
     * @author Guanyu Hu
     * @since 2022-11-03
     */
    UserInformationResultVO getUserInformation(String username);
}
