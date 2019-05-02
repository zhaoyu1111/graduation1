package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zy.graduation1.entity.SuperEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Permission extends SuperEntity<Permission> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "permission_id", type = IdType.ID_WORKER)
    private Long permissionId;
    /**
     * 父ID，一级目录为0
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 名称
     */
    @TableField("permission_name")
    private String permissionName;
    /**
     * 图标
     */
    @TableField("permission_icon")
    private String permissionIcon;
    /**
     * 类型1,一级目录 2-二级目录 3-按钮
     */
    @TableField("permission_type")
    private Integer permissionType;
    /**
     * 目录和菜单对应前端路由，按钮对应后端接口
     */
    @TableField("permission_url")
    private String permissionUrl;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 是否删除0-否 1-是
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
    /**
     * 状态 0-无效 1-有效
     */
    @TableField("permission_status")
    private Integer permissionStatus;


    @Override
    protected Serializable pkVal() {
        return this.permissionId;
    }

}
