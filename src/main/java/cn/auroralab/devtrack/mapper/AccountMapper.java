package cn.auroralab.devtrack.mapper;

import cn.auroralab.devtrack.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账号信息 Mapper 接口
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> { }
