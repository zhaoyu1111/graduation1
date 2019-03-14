package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class MajorDetail {

    /**
     * 专业编号
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
     * 学院编号
     */
    private Long collegeId;
}
