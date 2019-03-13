package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class ActivityDto {

    /**
     * 主键ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动地点
     */
    private String activityAddr;
    /**
     * 活动开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 活动描述
     */
    private String activityDesc;
    /**
     * 活动人数
     */
    private Integer activityNumber;
    /**
     * 报名人数
     */
    private Integer signNumber;
    /**
     * 感兴趣数
     */
    private Integer interests;
    /**
     * 负责人姓名
     */
    private String leaderName;
    /**
     * 负责人电话
     */
    private String leaderMobile;
    /**
     * 状态1-申请 2-已结束
     */
    private Integer status;
    /**
     * 创建者ID
     */
    private Long creator;
}
