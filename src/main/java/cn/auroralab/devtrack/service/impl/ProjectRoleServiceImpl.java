package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.ProjectRole;
import cn.auroralab.devtrack.mapper.ProjectRoleMapper;
import cn.auroralab.devtrack.service.ProjectRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目中的角色信息 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class ProjectRoleServiceImpl extends ServiceImpl<ProjectRoleMapper, ProjectRole> implements ProjectRoleService { }
