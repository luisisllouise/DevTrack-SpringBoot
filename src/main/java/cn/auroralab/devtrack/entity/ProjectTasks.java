package cn.auroralab.devtrack.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ProjectTasks implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务uuid
     */
    @TableId(value = "task_uuid", type = IdType.INPUT)
    private byte[] uuid;

    /**
     * 任务所属的项目uuid
     */
    private byte[] projectUuid;

    /**
     * 任务在项目中的id
     */
    private Integer taskId;

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 创建人uuid
     */
    private byte[] creatorUuid;

    /**
     * 负责人uuid
     */
    private byte[] principalUuid;

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
    private Boolean isFinished;

    /**
     * 是否已删除
     */
    private Boolean isDeleted;

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
