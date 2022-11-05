package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户账号信息 服务类。
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
public interface AccountService extends IService<Account> {
    /**
     * 注册新用户。
     *
     * @param form 注册表单。
     * @return 注册结果对象。
     * @author Guanyu Hu
     * @since 2022-10-15
     */
    SignUpResultVO signUp(SignUpForm form);

    /**
     * 用户登录，并对数据库中的last_signin_time字段进行更新。
     *
     * @param form 登录表单。
     * @return 登录结果对象。
     * @author Guanyu Hu
     * @since 2022-10-17
     */
    SignInResultVO signIn(SignInForm form);

    /**
     * 用户个人信息修改，验证码与密码均验证正确后则修改成功。
     *
     * @param form 用户信息表单
     * @return 修改个人信息结果对象。
     * @author Xiaotong Wu
     * @since 2022-11-01
     */
    EditProfileResultVO editProfile(EditProfileForm form);

    /**
     * 用户个人头像修改。
     *
     * @param form 上传头像表单。
     * @return 上传头像结果对象。
     * @author Xiaotong Wu
     * @since 2022-11-01
     */
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
