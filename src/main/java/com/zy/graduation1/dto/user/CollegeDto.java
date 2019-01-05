package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class CollegeDto {

    /**
     * 学院Id
     */
    private Long collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 管理员ID
     */
    private Long operatorId;

    /**
     * 管理员姓名
     */
    private String operatorName;

    /**
     * 联系方式
     */
    private String mobile;
}
