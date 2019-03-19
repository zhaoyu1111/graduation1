package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class UserDto {

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 学号
     */
    private Long studentId;
    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学院ID
     */
    private Long collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 性别0-男 1-女
     */
    private Integer gender;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 个人简介
     */
    private String introduce;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 当前城市
     */
    private String currentCity;

    /**
     * 专业ID
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 用户状态0-正常 1-冻结
     */
    private Integer status;
}
