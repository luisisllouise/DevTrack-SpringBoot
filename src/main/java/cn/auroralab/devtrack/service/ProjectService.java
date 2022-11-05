package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.entity.Project;
import cn.auroralab.devtrack.form.createProjectFrom;
import cn.auroralab.devtrack.vo.CreateProjectVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 项目列表 服务类
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
public interface ProjectService extends IService<Project> {
    CreateProjectVO createProject(createProjectFrom form);
}
