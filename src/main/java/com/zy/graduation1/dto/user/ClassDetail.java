package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class ClassDetail {

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 班主任
     */
    private String headMaster;

    /**
     * 辅导员
     */
    private String counselor;

    /**
     * 联系人
     */
    private String contractor;
}
