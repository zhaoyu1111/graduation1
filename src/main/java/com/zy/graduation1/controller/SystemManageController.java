package com.zy.graduation1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.*;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.Role;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/web/system")
public class SystemManageController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserSessionManageService userSessionManageService;

    @Autowired
    private RoleMenuRelationService roleMenuRelationService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SystemManageService systemManageService;

    @Autowired
    private OperatorRoleRelationService operatorRoleRelationService;

    /**
     * 查询角色列表
     * @param roleName
     * @param status
     * @return
     */
    @Anonymous
    @RequestMapping("/listRole")
    public List<RoleDto> listRole(String roleName, Integer deleted) {
        List<Role> roles = roleService.listRole(roleName, deleted);
        List<RoleDto> roleDtos = Lists.newArrayList();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    /**
     * 后台登录
     * @param account
     * @param pwd
     * @return
     */
    @Anonymous
    @RequestMapping("/webLogin")
    public SessionDto webLogin(@NotNull(message = "账号不能为空") Long account,
                               @NotNull(message = "请输入密码") String pwd) {
        return userSessionManageService.webLogin(account, pwd);
    }

    /**
     * 新增或更新角色
     * @param
     */
    @Anonymous
    @RequestMapping("/saveOrUpdateRole")
    public void updateRole(Long roleId,
                           @NotNull(message = "请输入角色名称") String roleName,
                           @NotNull(message = "请选择角色状态") Integer deleted) {
        Role role = new Role();
        role.setRoleId(roleId).setRoleName(roleName).setDeleted(deleted);
        roleService.saveOrUpdateRole(role);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Anonymous
    @RequestMapping("/deleteRole")
    public void deleteRole(Long roleId) {
        roleService.deleteById(roleId);
    }

    /**
     * 查詢菜單信息
     * @param title
     * @param currentPage
     * @return
     */
    @Anonymous
    @RequestMapping("/queryMenu")
    public MyPage<MenuDto> queryMenu(String title,@RequestParam(defaultValue = "1") Integer currentPage) {
        IPage<Menu> menuPage = menuService.queryMenu(title, currentPage);
        List<Menu> menus = menuPage.getRecords();
        if(CollectionUtils.isEmpty(menus)) {
            return new MyPage<>();
        }
        List<MenuDto> menuDtos = Lists.newArrayList();
        for (Menu menu : menus) {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(menu, menuDto);
            menuDtos.add(menuDto);
        }
        return new MyPage<>(menuPage.getTotal(), menuDtos);
    }

    /**
     * 刪除菜單信息
     * @param menuId
     */
    @Anonymous
    @RequestMapping("/deleteMenu")
    public void deleteMenu(Long menuId) {
        menuService.deleteMenu(menuId);
    }

    /**
     * 新增或更新菜單信息
     * @param menuId
     * @param title
     * @param href
     * @param icon
     * @param parentId
     */
    @Anonymous
    @RequestMapping("/saveOrUpdateMenu")
    public void saveOrUpdateMenu(Long menuId,
                                 @NotNull(message = "请输入标题") String title,
                                 @NotNull(message = "请输入前端页面地址") String href,
                                 @NotNull(message = "请输入图标") String icon,
                                 @NotNull(message = "请选择目录类型") Long parentId) {
        menuService.saveOrUpdateMenu(menuId, title, href, icon, parentId);
    }

    /**
     * 分頁查詢角色菜單
     * @param roleId
     * @param menuId
     * @param currentPage
     * @return
     */
    @Anonymous
    @RequestMapping("/queryRoleMenu")
    public MyPage<RoleMenuRelationDto> queryRoleMenu(Long roleId, Long menuId,
                                                     @RequestParam(defaultValue = "1") Integer currentPage) {
        return systemManageService.queryRoleMenuRelation(roleId, menuId, currentPage);
    }

    /**
     * 新增或更新角色菜單
     * @param relationId
     * @param roleId
     * @param menuId
     */
    @Anonymous
    @RequestMapping("/saveOrUpdateRoleMenu")
    public void addRoleMenu(Long relationId,
                            @NotNull(message = "请选择角色") Long roleId,
                            @NotNull(message = "请选择菜单") Long menuId) {
        systemManageService.saveOrUpdateRoleMenu(relationId, roleId, menuId);
    }

    /**
     * 刪除角色菜單
     * @param relationId
     */
    @Anonymous
    @RequestMapping("/deleteRoleMenu")
    public void deleteRoleMenu(@NotNull(message = "请选择角色菜单联系记录") Long relationId) {
        roleMenuRelationService.deleteRoleMenu(relationId);
    }

    @Anonymous
    @RequestMapping("/deleteOperatorRole")
    public void deleteOperatorRole(@NotNull(message = "记录不能为空") Long relationId) {
        operatorRoleRelationService.deleteById(relationId);
    }

    @Anonymous
    @RequestMapping("/queryOperatorRoleRelation")
    public MyPage<OperatorRoleDto> queryOperatorRoleRelation(Long roleId, String operatorName,
                                                             @RequestParam(defaultValue = "1") Integer currentPage) {
        return systemManageService.queryOperatorRoleRelation(roleId, operatorName, currentPage);
    }

    @Anonymous
    @RequestMapping("/saveOrUpdateOperatorRole")
    public void saveOrUpdateOperatorRole(Long relationId, String operatorName, Long roleId) {
        systemManageService.saveOrUpdateOperatorRole(relationId, operatorName, roleId);
    }
}
