package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Accounts;
import cn.auroralab.devtrack.form.SignUpForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户账号信息 服务类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
public interface AccountsService extends IService<Accounts> {
    boolean signUp(SignUpForm form);
}
