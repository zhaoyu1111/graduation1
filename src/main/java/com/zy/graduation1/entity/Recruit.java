package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Recruit extends SuperEntity<Recruit> {

    private static final long serialVersionUID = 1L;

    /**
     * 招聘ID
     */
    @TableId(value = "recurit_id", type = IdType.AUTO)
    private Long recuritId;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 工资
     */
    @TableField("salary")
    private String salary;
    /**
     * 招聘人数
     */
    @TableField("members")
    private Integer members;
    /**
     * 简历投递数
     */
    @TableField("resumes")
    private Integer resumes;
    /**
     * 结束时间
     */
    @TableField("endTime")
    private Long endTime;
    /**
     * 联系人姓名
     */
    @TableField("contractor")
    private String contractor;
    /**
     * 电话
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 职位名称
     */
    @TableField("pos_name")
    private String posName;
    /**
     * 职位描述
     */
    @TableField("pos_descript")
    private String posDescript;
    /**
     * 福利
     */
    @TableField("welfare")
    private String welfare;
    /**
     * 工作地点
     */
    @TableField("work_place")
    private String workPlace;
    /**
     * 职位所属单位
     */
    @TableField("unit_id")
    private Long unitId;
    /**
     * 申请人Id
     */
    @TableField("email")
    private String email;
    /**
     * 职位状态1-审核 2-正在招聘 3-已结束
     */
    @TableField("status")
    private Integer status;
    /**
     * 是否删除0-是 1-否
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.recuritId;
    }

}
