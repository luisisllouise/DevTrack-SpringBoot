package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.TaskTypeEnum;
import cn.auroralab.devtrack.entity.VCodeRecord;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.VCodeForm;
import cn.auroralab.devtrack.mapper.VCodeRecordMapper;
import cn.auroralab.devtrack.service.VCodeRecordService;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.util.VCodeGenerator;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import cn.auroralab.devtrack.vo.VCodeResultVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
@Service
public class VCodeRecordServiceImpl extends ServiceImpl<VCodeRecordMapper, VCodeRecord> implements VCodeRecordService {
    @Autowired
    private VCodeRecordMapper vCodeRecordMapper;

    public VCodeResultVO signUpVerificationCode(VCodeForm form) {
        VCodeGenerator generator = new VCodeGenerator();

        QueryWrapper<VCodeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type", TaskTypeEnum.SIGN_UP.code)
                .eq("email", form.getEmail())
                .ge("task_time", LocalDateTime.now().minusMinutes(generator.getValidTime()))
                .orderByDesc("task_time")
                .last("limit 1");
        var oldRecord = vCodeRecordMapper.selectOne(queryWrapper);

        if (oldRecord != null)
            return new VCodeResultVO(StatusCodeEnum.VCODE_RESEND, oldRecord);

        VCodeRecord vCodeRecord = new VCodeRecord();

        int createUUIDCount = 0;
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            vCodeRecord.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            queryWrapper.eq("task_uuid", vCodeRecord.getUuid());
            if (vCodeRecordMapper.selectOne(queryWrapper) == null)
                break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID)
                return new VCodeResultVO(StatusCodeEnum.UUID_CONFLICT);
        }

        vCodeRecord.setTime(generator.getStartTime());
        vCodeRecord.setType(form.getTaskType());
        vCodeRecord.setEmail(form.getEmail());
        vCodeRecord.setVCode(generator.getVerificationCode());
        vCodeRecord.setValidTime(generator.getValidTime());

        vCodeRecordMapper.insert(vCodeRecord);

        return new VCodeResultVO(StatusCodeEnum.SUCCESS, vCodeRecord);
    }
}
