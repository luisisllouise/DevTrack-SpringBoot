package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Accounts;
import cn.auroralab.devtrack.mapper.AccountsMapper;
import cn.auroralab.devtrack.service.AccountsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账号信息 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AccountsService {

}
