package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.VCodeRecord;
import cn.auroralab.devtrack.form.VCodeForm;
import cn.auroralab.devtrack.vo.VCodeResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 验证码服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
public interface VCodeRecordService extends IService<VCodeRecord> {
    /**
     * 生成注册验证码。
     * @param form 验证码表单。
     * @return 结果对象。
     */
    VCodeResultVO signUpVerificationCode(VCodeForm form);
}
