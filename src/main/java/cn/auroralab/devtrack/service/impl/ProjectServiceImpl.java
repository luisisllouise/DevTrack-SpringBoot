package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Project;
import cn.auroralab.devtrack.mapper.ProjectMapper;
import cn.auroralab.devtrack.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目列表 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService { }
