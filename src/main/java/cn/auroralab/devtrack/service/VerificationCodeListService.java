package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.VerificationCodeList;
import cn.auroralab.devtrack.form.VerificationCodeForm;
import cn.auroralab.devtrack.vo.VerificationCodeResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-16
 */
public interface VerificationCodeListService extends IService<VerificationCodeList> {
    VerificationCodeResultVO signUpVerificationCode(VerificationCodeForm form);
}
