package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.form.createProjectFrom;
import cn.auroralab.devtrack.service.ProjectService;
import cn.auroralab.devtrack.vo.CreateProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目列表 前端控制器
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@RestController
@RequestMapping("//projects")
public class ProjectsController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("/createProject")
    public CreateProjectVO createProject(createProjectFrom Form){
        return projectService.createProject(Form);
    }
}
