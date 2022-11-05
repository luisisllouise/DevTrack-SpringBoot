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
    /**
     * 创建项目
     *
     * @param form 项目创建表单
     * @return 项目创建结果对象。
     * @author Xiaotong Wu
     * @since 2022-11-05
     */
    CreateProjectVO createProject(createProjectFrom form);
}
