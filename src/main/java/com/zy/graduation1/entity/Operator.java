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
public class Operator extends SuperEntity<Operator> {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
    @TableId(value = "operator_id", type = IdType.ID_WORKER)
    private Long operatorId;
    /**
     * 管理员名称
     */
    @TableField("operator_name")
    private String operatorName;
    /**
     * 管理员状态0-冻结 1-正常
     */
    @TableField("operator_status")
    private Integer operatorStatus;
    /**
     * 是否删除0-否 1-是
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @TableField("origin_type")
    private Integer originType;

    @TableField("mobile")
    private String mobile;

    @TableField("pwd")
    private String pwd;


    @Override
    protected Serializable pkVal() {
        return this.operatorId;
    }

}
