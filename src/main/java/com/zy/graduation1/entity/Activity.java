package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Activity extends SuperEntity<Activity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Long activityId;
    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;
    /**
     * 活动地点
     */
    @TableField("activity_addr")
    private String activityAddr;
    /**
     * 活动开始时间
     */
    @TableField("start_time")
    private Long startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Long endTime;
    /**
     * 活动描述
     */
    @TableField("activity_desc")
    private String activityDesc;
    /**
     * 活动人数
     */
    @TableField("activity_number")
    private Integer activityNumber;
    /**
     * 报名人数
     */
    @TableField("sign_number")
    private Integer signNumber;
    /**
     * 感兴趣数
     */
    @TableField("interests")
    private Integer interests;
    /**
     * 负责人姓名
     */
    @TableField("leader_name")
    private String leaderName;
    /**
     * 负责人电话
     */
    @TableField("leader_mobile")
    private String leaderMobile;
    /**
     * 状态1-申请 2-已结束
     */
    @TableField("status")
    private Integer status;
    /**
     * 创建者ID
     */
    @TableField("creator")
    private Long creator;


    @Override
    protected Serializable pkVal() {
        return this.activityId;
    }

}
