package cn.auroralab.devtrack.mapper;

import cn.auroralab.devtrack.entity.Accounts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户账号信息 Mapper 接口
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Mapper
public interface AccountsMapper extends BaseMapper<Accounts> {

}
