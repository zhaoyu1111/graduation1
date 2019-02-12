package com.zy.graduation1.controller;

import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.UserInfoDto;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.service.UserInfoManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoManageService userInfoManageService;

    @RequestMapping("/queryUserInfo")
    public MyPage<UserInfoDto> queryUserInfo(@RequestParam(defaultValue = "1") Integer currentPage, Long classId, Long majorId, Long collegeId) {
        return userInfoManageService.queryUserInfo(RequestUser.getCurrentUser(), currentPage, classId, majorId, collegeId);
    }

    @RequestMapping("/listMenu")
    public List<Menu> listMenu() {
        return userInfoManageService.listMenu(RequestUser.getCurrentUser());
    }
}
