package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.mapper.VerificationCodeRecordMapper;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
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
public class VerificationCodeRecordServiceImpl extends ServiceImpl<VerificationCodeRecordMapper, VerificationCodeRecord> implements VerificationCodeRecordService {
    @Autowired
    private VerificationCodeRecordMapper verificationCodeRecordMapper;

    public VerificationCodeResultVO signUpVerificationCode(VerificationCodeForm form) {
        QueryWrapper<VerificationCodeRecord> queryWrapper = new QueryWrapper<>();
        VerificationCodeRecord verificationCodeRecord = new VerificationCodeRecord();
        int createUUIDCount = 0;
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            verificationCodeRecord.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            queryWrapper.eq("task_uuid", verificationCodeRecord.getUuid());
            if (verificationCodeRecordMapper.selectOne(queryWrapper) == null) break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) return VerificationCodeResultVO.UNABLE_TO_CREATE_UUID;
        }
        VerificationCodeGenerator generator = new VerificationCodeGenerator();

        verificationCodeRecord.setTaskTime(generator.getStartTime());
        verificationCodeRecord.setTaskType(form.getTaskType());
        verificationCodeRecord.setEmail(form.getEmail());
        verificationCodeRecord.setVerificationCode(generator.getVerificationCode());
        verificationCodeRecord.setValidTime(generator.getValidTime());

        verificationCodeRecordMapper.insert(verificationCodeRecord);

        VerificationCodeResultVO resultVO = VerificationCodeResultVO.SUCCESS;
        resultVO.setVerificationCodeRecord(verificationCodeRecord);
        return resultVO;
    }
}
