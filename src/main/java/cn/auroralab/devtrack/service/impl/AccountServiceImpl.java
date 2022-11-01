package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import cn.auroralab.devtrack.form.AvatarForm;
import cn.auroralab.devtrack.form.EditProfileForm;
import cn.auroralab.devtrack.form.SignInForm;
import cn.auroralab.devtrack.form.SignUpForm;
import cn.auroralab.devtrack.mapper.AccountMapper;
import cn.auroralab.devtrack.mapper.VerificationCodeRecordMapper;
import cn.auroralab.devtrack.service.AccountService;
import cn.auroralab.devtrack.util.ConvertTool;
import cn.auroralab.devtrack.util.MD5Generator;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Blob;
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
        if (!verificationCodeRecord.isValid(LocalDateTime.now()))
            return new SignUpResultVO(StatusCodeEnum.VCODE_INVALID);

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
            else if (createUUIDCount == MAX_COUNT_OF_TRY_TO_CREATE_UUID)
                return new SignUpResultVO(StatusCodeEnum.UUID_CONFLICT);
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

    /**
     * 用户个人信息修改，验证码与密码均验证正确后则修改成功
     *
     * @param editProfileForm
     * @return
     */
    public EditProfileResultVO editProfile(EditProfileForm editProfileForm) {//Nickname，phone合法性在前端检查
        /*    密码校验     */
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", editProfileForm.getUsername());//查询到信息数据
        Account account = this.accountMapper.selectOne(queryWrapper);
        if (account == null) return new EditProfileResultVO(StatusCodeEnum.USER_NOT_EXISTS);
        if (!Arrays.equals(account.getPasswordDigest(), MD5Generator.getMD5(editProfileForm.getOld_password())))
            return new EditProfileResultVO(StatusCodeEnum.USER_PASSWORD_ERROR);//密码验证错误
        /*   验证码校验    */
        QueryWrapper<VerificationCodeRecord> verificationCodeRecordQueryWrapper = new QueryWrapper<>();
        verificationCodeRecordQueryWrapper.eq("task_uuid", ConvertTool.hexStringToBytes(editProfileForm.getVerificationCodeRecordUUID()));
        VerificationCodeRecord verificationCodeRecord = verificationCodeRecordMapper.selectOne(verificationCodeRecordQueryWrapper);
        if (verificationCodeRecord == null) return new EditProfileResultVO(StatusCodeEnum.VCODE_NO_RECORD);//获取验证码失败
        boolean sameEmail = verificationCodeRecord.getEmail().equals(editProfileForm.getEmail());//判断邮箱
        boolean sameVerificationCode = verificationCodeRecord.getVerificationCode().equals(editProfileForm.getVerificationCode());//判断验证码
        if (!sameEmail) return new EditProfileResultVO(StatusCodeEnum.VCODE_NO_RECORD);//验证码发送错误，发送至错误邮箱
        if (!sameVerificationCode) return new EditProfileResultVO(StatusCodeEnum.VCODE_ERROR);//验证码错误
        if (!verificationCodeRecord.isValid(LocalDateTime.now()))
            return new EditProfileResultVO(StatusCodeEnum.VCODE_INVALID);//验证码超时
        // 均成功
        Account target = new Account();
        target.setNickname(editProfileForm.getNickname());
        target.setEmail(editProfileForm.getEmail());
        target.setPasswordDigest(MD5Generator.getMD5(editProfileForm.getNew_password()));
        target.setPhone(editProfileForm.getPhone());
        accountMapper.update(target, queryWrapper);
        return new EditProfileResultVO(StatusCodeEnum.SUCCESS);
    }

    /**
     * 用户个人头像更改
     *
     * @param
     * @return
     */
    public AvatarResultVO avatar(AvatarForm form) {
        Blob blob = null;
        String type = form.getFile().getOriginalFilename().substring(form.getFile().getOriginalFilename().lastIndexOf("."));
        if (!(type.equals("png") || type.equals("jpeg") || type.equals("jpg")))
            return new AvatarResultVO(StatusCodeEnum.USER_AVATAR_FILETYPE_ERROR);
        try {
            blob = new SerialBlob(form.getFile().getBytes());
        } catch (Exception e) {
            return new AvatarResultVO(StatusCodeEnum.UNKNOWN_ERROR);//转换二进制错误，此处暂判为未知错误
        }
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", form.getUsername());//查询到信息数据
        Account account=accountMapper.selectOne(queryWrapper);
        if (account==null) return new AvatarResultVO(StatusCodeEnum.USER_NOT_EXISTS);
        accountMapper.update(new Account(blob), queryWrapper);//将头像更新至数据库
        return new AvatarResultVO(StatusCodeEnum.SUCCESS);
    }
}
