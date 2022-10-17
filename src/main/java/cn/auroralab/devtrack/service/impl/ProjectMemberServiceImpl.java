package cn.auroralab.devtrack.service.impl;

import cn.auroralab.devtrack.entity.ProjectMember;
import cn.auroralab.devtrack.mapper.ProjectMemberMapper;
import cn.auroralab.devtrack.service.ProjectMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目成员映射 服务实现类
 * </p>
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Service
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMember> implements ProjectMemberService { }
