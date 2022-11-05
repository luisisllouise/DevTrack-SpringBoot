package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.Account;
import cn.auroralab.devtrack.entity.Project;
import cn.auroralab.devtrack.environment.Environment;
import cn.auroralab.devtrack.form.createProjectFrom;
import cn.auroralab.devtrack.mapper.ProjectMapper;
import cn.auroralab.devtrack.service.ProjectService;
import cn.auroralab.devtrack.util.UUIDGenerator;
import cn.auroralab.devtrack.vo.CreateProjectVO;
import cn.auroralab.devtrack.vo.SignUpResultVO;
import cn.auroralab.devtrack.vo.StatusCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 项目列表 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    public CreateProjectVO createProject(createProjectFrom Form){
        Project project=new Project(Form.getCreater_uuid(),Form.getPrincipal_uuid(),Form.getTaskIdPrefix(),Form.isPublicProject());
        QueryWrapper<Project> projectQueryWrapper=new QueryWrapper<>();
        List<Project> list= projectMapper.selectList(projectQueryWrapper);
        project.setId(String.valueOf(list.size()));
        int createUUIDCount = 0;
        while (createUUIDCount < Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID) {
            project.setUuid(UUIDGenerator.getUUID());
            createUUIDCount++;
            projectQueryWrapper.eq("project_uuid", project.getUuid());
            if (projectMapper.selectOne(projectQueryWrapper) == null) break;
            else if (createUUIDCount == Environment.MAX_COUNT_OF_TRY_TO_CREATE_UUID)
                return new CreateProjectVO(StatusCodeEnum.UUID_CONFLICT);
        }
        project.setFinished(false);
        project.setDeleted(false);
        project.setCreationTime(LocalDateTime.now());
        projectMapper.insert(project);
        return new CreateProjectVO(StatusCodeEnum.SUCCESS);
    }
}
