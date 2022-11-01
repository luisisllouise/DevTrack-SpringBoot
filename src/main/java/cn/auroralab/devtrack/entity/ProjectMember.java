package cn.auroralab.devtrack.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目成员映射
 *
 * @author Guanyu Hu
 * @since 2022-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_members")
public class ProjectMember implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 映射记录uuid
     */
    @TableId(value = "record_uuid", type = IdType.INPUT)
    private String uuid;

    private String fromProject;

    /**
     * 用户uuid
     */
    @TableField(value = "user_uuid")
    private String user;

    /**
     * 用户在项目中的昵称
     */
    private String nickname;

    /**
     * 用户在项目中扮演的角色
     */
    private String role;
}
