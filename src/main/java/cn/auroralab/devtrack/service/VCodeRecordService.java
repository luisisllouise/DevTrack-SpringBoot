package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.VCodeRecord;
import cn.auroralab.devtrack.form.VCodeForm;
import cn.auroralab.devtrack.vo.VCodeResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
public interface VCodeRecordService extends IService<VCodeRecord> {
    VCodeResultVO signUpVerificationCode(VCodeForm form);
}
