package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Projects;
import cn.auroralab.devtrack.mapper.ProjectsMapper;
import cn.auroralab.devtrack.service.ProjectsService;
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
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects> implements ProjectsService {

}
