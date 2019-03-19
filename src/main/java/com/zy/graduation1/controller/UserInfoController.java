package com.zy.graduation1.controller;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.UserDto;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.service.UserInfoManageService;
import com.zy.graduation1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/queryUser")
    public MyPage<UserDto> queryUser(Long majorId, Long classId,
                                     String nameOrId, @RequestParam(defaultValue = "1") Integer page) {
        return userInfoManageService.queryUser(RequestUser.getCurrentUser(), majorId, classId, nameOrId, page);
    }

    @RequestMapping("/getUser")
    public User getUser(@NotNull(message = "请选择需要修改的学生信息") Long studentId) {
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
}
