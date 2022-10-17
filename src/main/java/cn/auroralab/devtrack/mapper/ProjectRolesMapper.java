package cn.auroralab.devtrack.mapper;

import cn.auroralab.devtrack.entity.ProjectRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目中的角色信息 Mapper 接口
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Mapper
public interface ProjectRolesMapper extends BaseMapper<ProjectRoles> { }
