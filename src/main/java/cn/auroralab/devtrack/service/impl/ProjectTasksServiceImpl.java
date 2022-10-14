package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.ProjectTasks;
import cn.auroralab.devtrack.mapper.ProjectTasksMapper;
import cn.auroralab.devtrack.service.ProjectTasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目中的任务信息 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class ProjectTasksServiceImpl extends ServiceImpl<ProjectTasksMapper, ProjectTasks> implements ProjectTasksService {

}
