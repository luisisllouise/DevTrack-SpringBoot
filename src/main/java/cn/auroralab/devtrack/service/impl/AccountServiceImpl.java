package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountMapper;
import cn.auroralab.devtrack.mapper.VerificationCodeRecordMapper;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.util.ConvertTool;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.SignInResultVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 用户账号信息 服务实现类
 * </p>
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
        verificationCodeRecordQueryWrapper.eq("task_uuid", ConvertTool.hexStringToBytes(form.getVerificationCodeRecordUUID()));
        VerificationCodeRecord verificationCodeRecord = verificationCodeRecordMapper.selectOne(verificationCodeRecordQueryWrapper);
        if (verificationCodeRecord == null) return new SignUpResultVO(StatusCodeEnum.VCODE_NO_RECORD);

        boolean sameEmail = verificationCodeRecord.getEmail().equals(form.getEmail());
        boolean sameVerificationCode = verificationCodeRecord.getVerificationCode().equals(form.getVerificationCode());
        if (!sameEmail) return new SignUpResultVO(StatusCodeEnum.VCODE_NO_RECORD);
        if (!sameVerificationCode) return new SignUpResultVO(StatusCodeEnum.VCODE_ERROR);
        if (!verificationCodeRecord.isValid(LocalDateTime.now())) return new SignUpResultVO(StatusCodeEnum.VCODE_INVALID);

        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("username", form.getUsername());
        if (accountMapper.selectOne(accountQueryWrapper) != null) return new SignUpResultVO(StatusCodeEnum.USER_EXISTS);

        int createUUIDCount = 0;

        Account account = new Account();
        while (createUUIDCount < MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            account.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            accountQueryWrapper.eq("user_uuid", account.getUuid());
            if (accountMapper.selectOne(accountQueryWrapper) == null) break;
            else if (createUUIDCount == MAX_COUNT_OF_TRY_TO_CREATE_UUID) return new SignUpResultVO(StatusCodeEnum.UUID_CONFLICT);
        }
        account.setUsername(form.getUsername());
        account.setPasswordDigest(MD5Generator.getMD5(form.getPassword()));
        account.setEmail(form.getEmail());
        account.setPhone(form.getPhone());

        accountMapper.insert(account);
        return new SignUpResultVO(StatusCodeEnum.SUCCESS);
    }

    /**
     * 用户登录，并对数据库中的last_login_time进行更新
     *
     * @param form
     * @return
     */
    public SignInResultVO login(SignInForm form) {
        //判断用户名
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());
        Account account = this.accountMapper.selectOne(queryWrapper);

        if (account == null)
            return new SignInResultVO(StatusCodeEnum.USER_NOT_EXISTS);
        if (!Arrays.equals(account.getPasswordDigest(), MD5Generator.getMD5(form.getPassword())))
            return new SignInResultVO(StatusCodeEnum.USER_PASSWORD_ERROR);

        LocalDateTime signInTime = LocalDateTime.now();
        accountMapper.update(new Account(signInTime), queryWrapper);
        account.setLastLoginTime(signInTime);

        return new SignInResultVO(StatusCodeEnum.SUCCESS, account);
    }
}
