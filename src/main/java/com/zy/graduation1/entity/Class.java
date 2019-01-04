package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Class extends SuperEntity<Class> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    @TableId(value = "class_id", type = IdType.ID_WORKER)
    private Long classId;
    /**
     * 学院ID
     */
    @TableField("college_id")
    private Long collegeId;
    /**
     * 专业ID
     */
    @TableField("major_id")
    private Long majorId;
    /**
     * 联系人ID
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 班主任
     */
    @TableField("head_master")
    private String headMaster;
    /**
     * 辅导员
     */
    @TableField("counselor")
    private String counselor;

    @TableField("class_name")
    private String className;


    @Override
    protected Serializable pkVal() {
        return this.classId;
    }

}
