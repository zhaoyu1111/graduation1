package com.zy.graduation1.entity;

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
 * @since 2018-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends SuperEntity<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 学号
     */
    @TableId(value = "student_id", type = IdType.INPUT)
    private Long studentId;
    /**
     * 班级ID
     */
    @TableField("class_id")
    private Long classId;
    /**
     * 学院ID
     */
    @TableField("college_id")
    private Long collegeId;
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 性别0-男 1-女
     */
    @TableField("gender")
    private Integer gender;
    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 个人简介
     */
    @TableField("introduce")
    private String introduce;
    /**
     * 家庭住址
     */
    @TableField("home_address")
    private String homeAddress;
    /**
     * 当前城市
     */
    @TableField("current_city")
    private String currentCity;

    /**
     * 专业ID
     */
    @TableField("major_id")
    private Long majorId;

    /**
     * 用户状态0-正常 1-冻结
     */
    @TableField("status")
    private Integer status;

    /**
     * 用户密码
     */
    @TableField("pwd")
    private String pwd;

    @TableField("qq")
    private String qq;

    @TableField("wechat")
    private String wechat;


    @Override
    protected Serializable pkVal() {
        return this.studentId;
    }

}
