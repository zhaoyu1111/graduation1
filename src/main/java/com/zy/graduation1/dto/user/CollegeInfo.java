package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class CollegeInfo {

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 联系人
     */
    private String operatorId;

    /**
     * 管理员姓名
     */
    private String operatorName;

    /**
     * 联系方式
     */
    private String mobile;
}
