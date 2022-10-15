package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Accounts;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountsMapper;
import cn.auroralab.devtrack.service.AccountsService;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.UUIDGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账号信息 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AccountsService {
    @Autowired
    private AccountsMapper accountsMapper;

    @Override
    public boolean signUp(SignUpForm form) {
        QueryWrapper<Accounts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());
        if (accountsMapper.selectOne(queryWrapper) != null) return false;

        Accounts account = new Accounts();
        account.setUuid(UUIDGenerator.getUUID());
        account.setUsername(form.getUsername());
        account.setPasswordDigest(MD5Generator.getMD5(form.getPassword()));
        account.setEmail(form.getEmail());
        account.setPhone(form.getPhone());
        accountsMapper.insert(account);
        return true;
    }
}
