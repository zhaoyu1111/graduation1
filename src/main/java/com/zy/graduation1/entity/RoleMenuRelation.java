package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2019-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_menu_relation")
public class RoleMenuRelation extends SuperEntity<RoleMenuRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 联系ID
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;
    /**
     * 菜单状态
     */
    @TableField("status")
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.relationId;
    }

}
