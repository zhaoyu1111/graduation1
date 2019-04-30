package com.zy.graduation1.controller;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.UserDto;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoManageService userInfoManageService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ClassService classService;

    @RequestMapping("/queryUser")
    public MyPage<UserDto> queryUser(Long majorId, Long classId,
                                     String nameOrId, @RequestParam(defaultValue = "1") Integer page) {
        return userInfoManageService.queryUser(RequestUser.getCurrentUser(), majorId, classId, nameOrId, page);
    }

    @RequestMapping("/getUser")
    public UserDto getUser(@NotNull(message = "请选择需要修改的学生信息") Long studentId) {
        return userInfoManageService.getUser(studentId);
    }

    @RequestMapping("/deletedUser")
    public void deletedUser(@NotNull(message = "请选择需要删除的用户") Long studentId) {
        userService.deleteUser(studentId);
    }

    @RequestMapping("/listMenu")
    public List<Menu> listMenu() {
        return userInfoManageService.listMenu(RequestUser.getCurrentUser());
    }

    @RequestMapping("/saveOrUpdateUser")
    public void saveOrUpdateUser(@NotNull(message = "请输入学号") Long studentId, @NotBlank(message = "请输入学生姓名") String userName,
                                 @NotBlank(message = "请输入所属学院") String collegeName, @NotBlank(message = "请输入所属的专业") String majorName,
                                 @NotBlank(message = "请输入所属班级") String className, @NotBlank(message = "请输入手机号") String mobile,
                                 @NotNull(message = "请选择性别") Integer gender, @NotBlank(message = "请输入出生日期") String birthday,
                                 @NotBlank(message = "请输入邮箱") String email, @RequestParam(defaultValue = "0") Integer status,
                                 @NotBlank(message = "请输入当前所在城市") String currentCity, @NotBlank(message = "请输入家庭地址") String homeAddress,
                                 String qq, String wechat, String introduce) {
        Long collegeId = collegeService.validCollege(collegeName);
        Long majorId = majorService.validMajor(majorName, collegeId);
        Long classId = classService.validClass(collegeId, majorId, className);

        userService.saveOrUpdateUser(studentId, userName, collegeId, majorId, classId, mobile, gender, birthday,
                email, status, currentCity, homeAddress, qq, wechat, introduce);
    }

    @RequestMapping("/getUserInfo")
    public User getUserInfo() {
        return userService.selectById(RequestUser.getCurrentUser());
    }
}
