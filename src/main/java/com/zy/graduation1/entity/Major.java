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
public class Major extends SuperEntity<Major> {

    private static final long serialVersionUID = 1L;

    /**
     * 专业ID
     */
    @TableId(value = "major_id", type = IdType.ID_WORKER)
    private Long majorId;
    /**
     * 专业名称
     */
    @TableField("major_name")
    private String majorName;

    @TableField("college_id")
    private Long collegeId;


    @Override
    protected Serializable pkVal() {
        return this.majorId;
    }

}
