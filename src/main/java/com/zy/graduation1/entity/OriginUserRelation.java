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

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2019-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("origin_user_relation")
public class OriginUserRelation extends SuperEntity<OriginUserRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 联系ID
     */
    @TableId(value = "relation_id", type = IdType.ID_WORKER)
    private Long relationId;
    /**
     * 用户ID
     */
    @TableField("student_id")
    private Long studentId;
    /**
     * 组织ID
     */
    @TableField("origin_id")
    private Long originId;
    /**
     * 状态0-正常 1-冻结
     */
    @TableField("status")
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.relationId;
    }

}
