package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.VerificationCodeList;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.mapper.VerificationCodeListMapper;
import cn.auroralab.devtrack.service.VerificationCodeListService;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.util.VerificationCodeGenerator;
import cn.auroralab.devtrack.vo.VerificationCodeResultVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
@Service
public class VerificationCodeListServiceImpl extends ServiceImpl<VerificationCodeListMapper, VerificationCodeList> implements VerificationCodeListService {

    @Autowired
    private VerificationCodeListMapper verificationCodeListMapper;

    public VerificationCodeResultVO signUpVerificationCode(VerificationCodeForm form) {
        QueryWrapper<VerificationCodeList> queryWrapper = new QueryWrapper<>();
        VerificationCodeList verificationCode = new VerificationCodeList();
        int createUUIDCount = 0;
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            verificationCode.setTaskUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            queryWrapper.eq("task_uuid", verificationCode.getTaskUuid());
            if (verificationCodeListMapper.selectOne(queryWrapper) == null) break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) return VerificationCodeResultVO.UNABLE_TO_CREATE_UUID;
        }
        verificationCode.setTaskType(form.getTaskType());
        verificationCode.setEmail(form.getEmail());

        VerificationCodeGenerator generator = new VerificationCodeGenerator();

        verificationCode.setVerificationCode(generator.getVerificationCode());
        verificationCode.setTaskTime(generator.getStartTime());

        verificationCodeListMapper.insert(verificationCode);

        VerificationCodeResultVO resultVO = VerificationCodeResultVO.SUCCESS;
        resultVO.setVerificationCodeRecord(verificationCode);
        return resultVO;
    }
}
