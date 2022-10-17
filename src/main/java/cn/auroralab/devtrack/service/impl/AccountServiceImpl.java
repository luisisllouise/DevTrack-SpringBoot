package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import cn.auroralab.devtrack.form.RuleForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountMapper;
import cn.auroralab.devtrack.mapper.VerificationCodeRecordMapper;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.SignInResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户账号信息 服务实现类
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    /**
     * 最大尝试创建uuid的次数。
     */
    private static final int MAX_COUNT_OF_TRY_TO_CREATE_UUID = 5;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private VerificationCodeRecordMapper verificationCodeRecordMapper;

    public SignUpResultVO signUp(SignUpForm form) {
        QueryWrapper<VerificationCodeRecord> verificationCodeRecordQueryWrapper = new QueryWrapper<>();
        verificationCodeRecordQueryWrapper.eq("task_uuid", form.getVerificationCodeRecordUUID());
        VerificationCodeRecord verificationCodeRecord = verificationCodeRecordMapper.selectOne(verificationCodeRecordQueryWrapper);
        if (verificationCodeRecord == null) return SignUpResultVO.NOT_FIND_VERIFICATION_CODE_RECORD;

        boolean sameEmail = verificationCodeRecord.getEmail().equals(form.getEmail());
        boolean sameVerificationCode = verificationCodeRecord.getVerificationCode().equals(form.getVerificationCode());
        if (!sameEmail) return SignUpResultVO.VERIFICATION_CODE_INVALID;
        if (!sameVerificationCode) return SignUpResultVO.VERIFICATION_CODE_ERROR;
        if (!verificationCodeRecord.isValid(LocalDateTime.now())) return SignUpResultVO.VERIFICATION_CODE_INVALID;

        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("username", form.getUsername());
        if (accountMapper.selectOne(accountQueryWrapper) != null) return SignUpResultVO.USER_EXISTS;

        int createUUIDCount = 0;

        Account account = new Account();
        while (createUUIDCount < MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            account.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            accountQueryWrapper.eq("user_uuid", account.getUuid());
            if (accountMapper.selectOne(accountQueryWrapper) == null) break;
            else if (createUUIDCount == MAX_COUNT_OF_TRY_TO_CREATE_UUID) return SignUpResultVO.UNABLE_TO_CREATE_UUID;
        }
        account.setUsername(form.getUsername());
        account.setPasswordDigest(MD5Generator.getMD5(form.getPassword()));
        account.setEmail(form.getEmail());
        account.setPhone(form.getPhone());

        accountMapper.insert(account);
        return SignUpResultVO.SUCCESS;
    }

    /**
     * 用户登录，并对数据库中的last_login_time进行更新
     * @param ruleForm
     * @return
     */
    @Override
    public SignInResultVO login(RuleForm ruleForm) {
        //判断用户名
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        Account account = this.accountMapper.selectOne(queryWrapper);
        SignInResultVO Judge_result = new SignInResultVO();
        if (account == null) {
            Judge_result.setCode(-1);//用户名对应的数据为空，没有该用户
        } else {//判断密码
            if (account.getPasswordDigest().equals(MD5Generator.getMD5(ruleForm.getPassword()))) {
                Judge_result.setCode(-2);//密码不正确
            } else {
                Judge_result.setCode(0);//用户名，密码均正确，可以登录
                Judge_result.setData(account);
            }
        }
        return Judge_result;
    }
}
