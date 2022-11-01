package cn.auroralab.devtrack.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目列表
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("projects")
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 项目uuid
     */
    @TableId(value = "project_uuid", type = IdType.INPUT)
    private String uuid;

    /**
     * 项目的数字id
     */
    private String projectId;

    /**
     * 创建人uuid
     */
    private String creatorUuid;

    /**
     * 负责人uuid
     */
    private String principalUuid;

    /**
     * 任务编号前缀
     */
    private String taskIdPrefix;

    /**
     * 项目是否公开
     */
    private Boolean isPublic;

    /**
     * 项目是否已完成
     */
    private Boolean isFinished;

    /**
     * 项目是否已删除
     */
    private Boolean isDeleted;

    /**
     * 项目创建时间
     */
    private LocalDateTime creationTime;

    /**
     * 项目开始时间
     */
    private LocalDateTime startTime;

    /**
     * 项目完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 项目删除时间
     */
    private LocalDateTime deleteTime;
}
