package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.entity.TaskTypeEnum;
import cn.auroralab.devtrack.entity.VCodeRecord;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountMapper;
import cn.auroralab.devtrack.mapper.VCodeRecordMapper;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.util.ConvertTool;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.ResourceFileLoader;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.*;
import cn.auroralab.devtrack.vo.data.UserInformation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private VCodeRecordMapper vCodeRecordMapper;

    public SignUpResultVO signUp(SignUpForm form) {
        QueryWrapper<VCodeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("task_type", TaskTypeEnum.SIGN_UP.code)
                .eq("email", form.getEmail())
                .orderByDesc("task_time")
                .last("limit 1");
        VCodeRecord vCodeRecord = vCodeRecordMapper.selectOne(queryWrapper);

        if (vCodeRecord == null)
            return new SignUpResultVO(StatusCodeEnum.VCODE_NO_RECORD);
        if (!Objects.equals(vCodeRecord.getVCode(), form.getVCode()))
            return new SignUpResultVO(StatusCodeEnum.VCODE_ERROR);
        if (!vCodeRecord.isValid(LocalDateTime.now()))
            return new SignUpResultVO(StatusCodeEnum.VCODE_INVALID);

        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("username", form.getUsername());
        if (accountMapper.selectOne(accountQueryWrapper) != null)
            return new SignUpResultVO(StatusCodeEnum.USER_EXISTS);

        int createUUIDCount = 0;

        Account account = new Account();
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            account.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            accountQueryWrapper.eq("user_uuid", account.getUuid());
            if (accountMapper.selectOne(accountQueryWrapper) == null) break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID)
                return new SignUpResultVO(StatusCodeEnum.UUID_CONFLICT);
        }

        account.setUsername(form.getUsername());
        account.setPasswordDigest(ConvertTool.bytesToHexString(MD5Generator.getMD5(form.getPassword())));
        account.setAvatar(ResourceFileLoader.imageToBytes("default_avatar.png"));
        account.setNickname(form.getUsername());
        account.setEmail(form.getEmail());

        accountMapper.insert(account);
        return new SignUpResultVO(StatusCodeEnum.SUCCESS);
    }

    public SignInResultVO signIn(SignInForm form) {
        //判断用户名
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());
        Account account = accountMapper.selectOne(queryWrapper);

        if (account == null)
            return new SignInResultVO(StatusCodeEnum.USER_NOT_EXISTS);
        if (!account.getPasswordDigest().equals(ConvertTool.bytesToHexString(MD5Generator.getMD5(form.getPassword()))))
            return new SignInResultVO(StatusCodeEnum.USER_PASSWORD_ERROR);

        LocalDateTime signInTime = LocalDateTime.now();
        accountMapper.update(new Account(signInTime), queryWrapper);
        account.setLastSignInTime(signInTime);

        return new SignInResultVO(StatusCodeEnum.SUCCESS, new UserInformation(account));
    }

    public EditProfileResultVO editProfile(EditProfileForm form) {
        //nickname，phone合法性在前端检查
        /*    密码校验     */
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());                // 查询到信息数据
        Account account = this.accountMapper.selectOne(queryWrapper);

        if (account == null)
            return new EditProfileResultVO(StatusCodeEnum.USER_NOT_EXISTS);
        if (account.getPasswordDigest().equals(ConvertTool.bytesToHexString(MD5Generator.getMD5(form.getOld_password()))))
            return new EditProfileResultVO(StatusCodeEnum.USER_PASSWORD_ERROR); // 密码验证错误

        /*   验证码校验    */
        QueryWrapper<VCodeRecord> vCodeRecordQueryWrapper = new QueryWrapper<>();
        vCodeRecordQueryWrapper.eq("task_uuid", ConvertTool.hexStringToBytes(form.getVerificationCodeRecordUUID()));
        VCodeRecord vCodeRecord = vCodeRecordMapper.selectOne(vCodeRecordQueryWrapper);

        if (vCodeRecord == null)
            return new EditProfileResultVO(StatusCodeEnum.VCODE_NO_RECORD);                         // 获取验证码失败

        boolean sameEmail = vCodeRecord.getEmail().equals(form.getEmail());                         // 判断邮箱
        boolean sameVerificationCode = vCodeRecord.getVCode().equals(form.getVerificationCode());   // 判断验证码

        if (!sameEmail)
            return new EditProfileResultVO(StatusCodeEnum.VCODE_NO_RECORD);                         // 验证码发送错误，发送至错误邮箱
        if (!sameVerificationCode)
            return new EditProfileResultVO(StatusCodeEnum.VCODE_ERROR);                             // 验证码错误
        if (!vCodeRecord.isValid(LocalDateTime.now()))
            return new EditProfileResultVO(StatusCodeEnum.VCODE_INVALID);                           // 验证码超时

        // 均成功
        Account target = new Account();
        target.setNickname(form.getNickname());
        target.setEmail(form.getEmail());
        target.setPasswordDigest(ConvertTool.bytesToHexString(MD5Generator.getMD5(form.getNew_password())));
        target.setPhone(form.getPhone());
        accountMapper.update(target, queryWrapper);

        return new EditProfileResultVO(StatusCodeEnum.SUCCESS);
    }

    public AvatarResultVO updateAvatar(AvatarForm form) {
        String type = form.getFile().getOriginalFilename().substring(form.getFile().getOriginalFilename().lastIndexOf(".") + 1);

        if (!type.equals("png") & !type.equals("jpeg") & !type.equals("jpg"))
            return new AvatarResultVO(StatusCodeEnum.USER_AVATAR_FILETYPE_ERROR, type);

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());// 查询到信息数据
        Account account = accountMapper.selectOne(queryWrapper);

        if (account == null)
            return new AvatarResultVO(StatusCodeEnum.USER_NOT_EXISTS);

        try {
            accountMapper.update(new Account(form.getFile().getBytes()), queryWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将头像更新至数据库
        return new AvatarResultVO(StatusCodeEnum.SUCCESS, type);
    }

    public UserInformationResultVO getUserInformation(String username) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Account account = accountMapper.selectOne(queryWrapper);

        if (account == null)
            return new UserInformationResultVO(StatusCodeEnum.USER_NOT_EXISTS);

        return new UserInformationResultVO(StatusCodeEnum.SUCCESS, new UserInformation(account));
    }
}
