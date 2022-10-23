package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.TaskTypeEnum;
import cn.auroralab.devtrack.entity.VerificationCodeRecord;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.mapper.VerificationCodeRecordMapper;
import cn.auroralab.devtrack.service.VerificationCodeRecordService;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.util.VerificationCodeGenerator;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import cn.auroralab.devtrack.vo.VCodeResultVO;
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

    public VCodeResultVO signUpVerificationCode(VerificationCodeForm form) {
        QueryWrapper<VerificationCodeRecord> queryWrapper = new QueryWrapper<>();
        VerificationCodeRecord vCodeRecord = new VerificationCodeRecord();
        int createUUIDCount = 0;
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            vCodeRecord.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            queryWrapper.eq("task_uuid", vCodeRecord.getUuid());
            if (verificationCodeRecordMapper.selectOne(queryWrapper) == null) break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) return new VCodeResultVO(StatusCodeEnum.UUID_CONFLICT);
        }
        VerificationCodeGenerator generator = new VerificationCodeGenerator();

        vCodeRecord.setTaskTime(generator.getStartTime());
        vCodeRecord.setTaskType(TaskTypeEnum.parse(form.getTaskType()));
        vCodeRecord.setEmail(form.getEmail());
        vCodeRecord.setVerificationCode(generator.getVerificationCode());
        vCodeRecord.setValidTime(generator.getValidTime());

        verificationCodeRecordMapper.insert(vCodeRecord);

        return new VCodeResultVO(StatusCodeEnum.SUCCESS, vCodeRecord);
    }
}
