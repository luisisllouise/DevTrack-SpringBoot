package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Accounts;
import cn.auroralab.devtrack.form.RuleForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.vo.ResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
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
    SignUpResultVO signUp(SignUpForm form);
    ResultVO login(RuleForm ruleForm);
}
