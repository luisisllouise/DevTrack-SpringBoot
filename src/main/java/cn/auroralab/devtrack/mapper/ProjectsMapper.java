package cn.auroralab.devtrack.mapper;

import cn.auroralab.devtrack.entity.Projects;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目列表 Mapper 接口
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Mapper
public interface ProjectsMapper extends BaseMapper<Projects> { }
