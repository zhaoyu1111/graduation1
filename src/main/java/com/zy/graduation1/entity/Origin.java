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
 * @since 2019-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Origin extends SuperEntity<Origin> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    @TableId(value = "origin_id", type = IdType.ID_WORKER)
    private Long originId;
    /**
     * 组织名称
     */
    @TableField("origin_name")
    private String originName;
    /**
     * 组织类型1-班级 2-学院 3-学校 4-校友会
     */
    @TableField("origin_type")
    private Integer originType;
    /**
     * 组织描述
     */
    @TableField("origin_descript")
    private String originDescript;
    /**
     * 成员数量
     */
    @TableField("members")
    private Integer members;
    /**
     * 状态0-正常 1-关闭
     */
    @TableField("status")
    private Integer status;
    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 管理员ID
     */
    @TableField("operator_id")
    private Long operatorId;


    @Override
    protected Serializable pkVal() {
        return this.originId;
    }

}
