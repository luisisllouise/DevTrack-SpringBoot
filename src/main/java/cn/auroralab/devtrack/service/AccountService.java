package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.vo.EditProfileResultVO;
import cn.auroralab.devtrack.vo.ResultVO;
import cn.auroralab.devtrack.vo.SignInResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户账号信息 服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
public interface AccountService extends IService<Account> {
    SignUpResultVO signUp(SignUpForm form);
    SignInResultVO login(SignInForm signInForm);
    EditProfileResultVO editprofile(EditProfileForm editProfileForm);
    ResultVO avatar(AvatarForm form);
}
