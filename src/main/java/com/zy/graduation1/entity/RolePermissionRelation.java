package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("role_permission_relation")
public class RolePermissionRelation extends SuperEntity<RolePermissionRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "relation_id", type = IdType.ID_WORKER)
    private Long relationId;
    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 是否删除0-否 1-是
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.relationId;
    }

}
