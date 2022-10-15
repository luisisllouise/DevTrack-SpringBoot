package cn.auroralab.devtrack.mapper;

import cn.auroralab.devtrack.entity.ProjectMembers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目成员映射 Mapper 接口
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Mapper
public interface ProjectMembersMapper extends BaseMapper<ProjectMembers> {

}
