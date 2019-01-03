package com.zy.graduation1.controller;

import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.enums.SystemTypeStatue;
import com.zy.graduation1.service.RoleService;
import com.zy.graduation1.service.UserSessionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void addRole(@RequestParam(defaultValue = "管理员") String roleName) {
        roleService.addRole(roleName);
    }

    @Anonymous
    @RequestMapping("/webLogin")
    public SessionDto login(Long account, String pwd) {
        return userSessionManageService.login(account, pwd, SystemTypeStatue.WEB);
    }
}
