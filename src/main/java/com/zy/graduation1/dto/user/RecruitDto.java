package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class RecruitDto {

    /**
     * 招聘ID
     */
    private Long recuritId;
    /**
     * 标题
     */
    private String title;
    /**
     * 工资
     */
    private String salary;
    /**
     * 招聘人数
     */
    private Integer members;
    /**
     * 简历投递数
     */
    private Integer resumes;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 联系人姓名
     */
    private String contractor;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 职位名称
     */
    private String posName;
    /**
     * 职位描述
     */
    private String posDescript;
    /**
     * 福利
     */
    private String welfare;
    /**
     * 工作地点
     */
    private String workPlace;
    /**
     * 职位所属单位
     */
    private Long unitId;

    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 申请人Id
     */
    private Long applyId;
    /**
     * 职位状态1-审核 2-正在招聘 3-已结束
     */
    private Integer status;
    /**
     * 是否删除0-是 1-否
     */
    private Integer deleted;
}
