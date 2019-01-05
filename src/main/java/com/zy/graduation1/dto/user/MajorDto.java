package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class MajorDto {

    /**
     * 专业ID
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 学院ID
     */
    private Long collegeId;

    /**
     * 专业管理员姓名
     */
    private String majorOperatorName;

    /**
     * 专业管理员联系方式
     */
    private String majorOperatorMobile;

    /**
     * 学院管理员姓名
     */
    private String collegeOperatorName;

    /**
     * 学院管理员联系方式
     */
    private String collegeOperatorMobile;
}
