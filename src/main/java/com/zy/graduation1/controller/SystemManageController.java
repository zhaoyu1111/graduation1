package com.zy.graduation1.controller;

import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.service.RoleService;
import com.zy.graduation1.service.UserSessionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/web/system")
public class SystemManageController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserSessionManageService userSessionManageService;

    /**
     * 添加角色
     * @param roleName
     */
    @RequestMapping("/addRole")
    public void addRole(@NotNull(message = "请输入管理员名称") String roleName,
                        @NotNull(message = "请输入管理员联系方式") String mobile,
                        @NotNull(message = "请输入管理员密码") String pwd) {
        roleService.addRole(roleName);
    }

    @Anonymous
    @RequestMapping("/webLogin")
    public SessionDto webLogin(@NotNull(message = "账号不能为空") Long account,
                               @NotNull(message = "请输入密码") String pwd) {
        return userSessionManageService.webLogin(account, pwd);
    }
}
