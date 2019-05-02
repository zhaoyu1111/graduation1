package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("operator_role_relation")
public class OperatorRoleRelation extends SuperEntity<OperatorRoleRelation> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系ID
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Long relationId;
    /**
     * 管理员ID
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 菜单ID
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 是否删除0-是 1-否
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.relationId;
    }

}
