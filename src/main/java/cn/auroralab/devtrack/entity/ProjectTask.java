package cn.auroralab.devtrack.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目中的任务信息
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_tasks")
public class ProjectTask implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务uuid
     */
    @TableId(value = "task_uuid", type = IdType.INPUT)
    private String uuid;

    /**
     * 任务所属的项目uuid
     */
    private String fromProject;

    /**
     * 任务在项目中的id
     */
    @TableField(value = "task_id")
    private Integer id;

    /**
     * 任务类型
     */
    @TableField(value = "task_type")
    private Integer type;

    /**
     * 任务标题
     */
    @TableField(value = "task_title")
    private String title;

    /**
     * 创建人uuid
     */
    @TableField(value = "creator_uuid")
    private String creator;

    /**
     * 负责人uuid
     */
    @TableField(value = "principal_uuid")
    private String principal;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 需求来源
     */
    private Integer sourceOfDemand;

    /**
     * 参与人
     */
    private String participants;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 是否已完成
     */
    @TableField(value = "is_finished")
    private Boolean finished;

    /**
     * 是否已删除
     */
    @TableField(value = "is_deleted")
    private Boolean deleted;

    /**
     * 任务创建时间
     */
    private LocalDateTime creationTime;

    /**
     * 时间
     */
    private LocalDateTime startTime;

    /**
     * 任务截止时间
     */
    private LocalDateTime deadline;

    /**
     * 任务完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 任务删除时间
     */
    private LocalDateTime deleteTime;
}
