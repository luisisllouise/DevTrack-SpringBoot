package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Accounts;
import cn.auroralab.devtrack.form.RuleForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountsMapper;
import cn.auroralab.devtrack.service.AccountsService;
import cn.auroralab.devtrack.service.EmailService;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.ResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

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
    /**
     * 最大尝试创建uuid的次数。
     */
    private static final int MAX_COUNT_OF_TRY_TO_CREATE_UUID = 5;

    @Autowired
    private AccountsMapper accountsMapper;
    @Autowired
    private EmailService emailService;

    public SignUpResultVO signUp(SignUpForm form) {
        QueryWrapper<Accounts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());
        if (accountsMapper.selectOne(queryWrapper) != null) return SignUpResultVO.USER_EXISTS;

        int createUUIDCount = 0;

        Accounts account = new Accounts();
        while (createUUIDCount < MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            account.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            queryWrapper.eq("user_uuid", account.getUuid());
            if (accountsMapper.selectOne(queryWrapper) == null) break;
            else if (createUUIDCount == MAX_COUNT_OF_TRY_TO_CREATE_UUID) return SignUpResultVO.UNABLE_TO_CREATE_UUID;
        }
        account.setUsername(form.getUsername());
        account.setPasswordDigest(MD5Generator.getMD5(form.getPassword()));
        account.setEmail(form.getEmail());
        account.setPhone(form.getPhone());

        accountsMapper.insert(account);
        return SignUpResultVO.SUCCESS;
    }
    /**
     * 用户登录，并对数据库中的last_login_time进行更新
     * @param ruleForm
     * @return
     */
    @Override
    public ResultVO login(RuleForm ruleForm) {
        //判断用户名
        QueryWrapper<Accounts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        Accounts accounts = this.accountsMapper.selectOne(queryWrapper);
        ResultVO Judge_result = new ResultVO();
        if (accounts == null) {
            Judge_result.setCode(-1);//用户名对应的数据为空，没有该用户
        } else {//判断密码
            if (accounts.getPasswordDigest().equals(MD5Generator.getMD5(ruleForm.getPassword()))) {
                Judge_result.setCode(-2);//密码不正确
            } else {
                Judge_result.setCode(0);//用户名，密码均正确，可以登录
                Accounts accounts1=new Accounts();//动态更新last_login_time
                accounts1.setUsername(accounts.getUsername());
                accounts1.setLastLoginTime(LocalDateTime.now());
                accountsMapper.update(accounts1,queryWrapper);//更新至数据库
                Judge_result.setData(accounts);
            }
        }
        return Judge_result;
    }
}
