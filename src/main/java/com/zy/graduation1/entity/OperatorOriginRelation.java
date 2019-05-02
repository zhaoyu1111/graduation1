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
 * @since 2019-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("operator_origin_relation")
public class OperatorOriginRelation extends SuperEntity<OperatorOriginRelation> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系ID
     */
    @TableId(value = "relation_id", type = IdType.ID_WORKER)
    private Long relationId;
    /**
     * 管理员ID
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 组织ID
     */
    @TableField("origin_id")
    private Long originId;
    /**
     * 组织类型 1-班级 2-专业 3-学院 4-学校 5-校友会
     */
    @TableField("origin_type")
    private Integer originType;
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
