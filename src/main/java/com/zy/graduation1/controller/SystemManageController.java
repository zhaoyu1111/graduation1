package com.zy.graduation1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.*;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.Role;
import com.zy.graduation1.entity.RoleMenuRelation;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
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

    @Autowired
    private OperatorService operatorService;

    /**
     * 查询角色列表
     * @param roleName
     * @param deleted
     * @return
     */
    @RequestMapping("/listRole")
    public MyPage<RoleDto> listRole(String roleName, Integer deleted) {
        List<Role> roles = roleService.listRole(roleName, deleted);
        List<RoleDto> roleDtos = Lists.newArrayList();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        return new MyPage<>((long)roleDtos.size(), roleDtos);
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
    @RequestMapping("/deleteRole")
    public void deleteRole(Long roleId) {
        roleService.deleteById(roleId);
    }

    /**
     * 查詢菜單信息
     * @param title
     * @param page
     * @return
     */
    @RequestMapping("/queryMenu")
    public MyPage<MenuDto> queryMenu(String title, Long parentId,
                                     @RequestParam(defaultValue = "1") Integer page) {
        IPage<Menu> menuPage = menuService.queryMenu(title, parentId, page);
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

    @RequestMapping("getAllMenu")
    public List<MenuTree> getAllMenu(@NotNull(message = "请选择角色") Long roleId) {
        List<Menu> menus = menuService.getAllMenu();
        if(CollectionUtils.isEmpty(menus)) {
            return null;
        }
        List<MenuTree> menuTrees = Lists.newArrayList();
        for (Menu menu : menus) {

            Boolean isBreak = true;
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu, menuTree);
            menuTree.setValue(menu.getMenuId());
            if(menu.getParentId() == 0) {
                menuTrees.add(menuTree);
                continue;
            }
            for (MenuTree tree : menuTrees) {
                if(menu.getParentId().equals(tree.getValue())) {
                    tree.getData().add(menuTree);
                    isBreak = false;
                    break;
                }
            }

            for (Menu menu1 : menus) {
                if(menu.getParentId().equals(menu1.getMenuId()) && isBreak) {
                    BeanUtils.copyProperties(menu1, menuTree);
                    menuTree.setValue(menu1.getMenuId());
                    MenuTree menuTree1 = new MenuTree();
                    BeanUtils.copyProperties(menu, menuTree1);
                    menuTree1.setValue(menu.getMenuId());
                    menuTree.getData().add(menuTree1);
                    menuTrees.add(menuTree);
                    break;
                }
            }
        }
        return menuTrees;
    }

    @RequestMapping("getResourceByRoleId")
    public List<RoleMenuRelation> getResourceByRoleId(Long roleId) {
        return roleMenuRelationService.listRoleMenu(roleId);
    }

    @PostMapping("editResource")
    public void editResource(@NotNull(message = "请选择角色") Long roleId,
                             @RequestParam(value="resources[]") Long[] resources) {
        roleMenuRelationService.editResources(roleId, Arrays.asList(resources));
    }

    @RequestMapping("/getParentMenu")
    public List<Menu> getParentMenu() {
        return menuService.getParentMenu();
    }

    /**
     * 刪除菜單信息
     * @param menuId
     */
    @RequestMapping("/deleteMenu")
    public void deleteMenu(@NotNull(message = "请选择菜单") Long menuId) {
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
    @RequestMapping("/saveOrUpdateMenu")
    public void saveOrUpdateMenu(Long menuId,
                                 @NotNull(message = "请输入标题") String title,
                                 @NotNull(message = "请输入前端页面地址") String href,
                                 @NotNull(message = "请输入图标") String icon,
                                 @NotNull(message = "请选择目录类型") Long parentId,
                                 @NotNull(message = "请选择菜单状态") Integer deleted) {
        menuService.saveOrUpdateMenu(menuId, title, href, icon, parentId, deleted);
    }

    /**
     * 分頁查詢角色菜單
     * @param roleId
     * @param menuId
     * @param currentPage
     * @return
     */
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
    @RequestMapping("/deleteRoleMenu")
    public void deleteRoleMenu(@NotNull(message = "请选择角色菜单联系记录") Long relationId) {
        roleMenuRelationService.deleteRoleMenu(relationId);
    }

    @RequestMapping("/deleteOperatorRole")
    public void deleteOperatorRole(@NotNull(message = "记录不能为空") Long relationId) {
        operatorRoleRelationService.deleteById(relationId);
    }

    @RequestMapping("/queryOperatorRoleRelation")
    public MyPage<OperatorRoleDto> queryOperatorRoleRelation(Long roleId, String operatorName,
                                                             @RequestParam(defaultValue = "1") Integer currentPage) {
        return systemManageService.queryOperatorRoleRelation(roleId, operatorName, currentPage);
    }

    @RequestMapping("/saveOrUpdateOperatorRole")
    public void saveOrUpdateOperatorRole(Long relationId, String operatorName, Long roleId) {
        systemManageService.saveOrUpdateOperatorRole(relationId, operatorName, roleId);
    }

    @RequestMapping("/queryOperator")
    public MyPage<OperatorDto> queryOperator(String operatorName, Long roleId, Long operatorId,
                                             @RequestParam(defaultValue = "1") Integer currentPage) {
        return systemManageService.queryOperator(operatorName, roleId, operatorId, currentPage);
    }

    @RequestMapping("/saveOrUpdateOperator")
    public void saveOrUpdateOperator(@NotNull(message = "请输入管理员名称") String operatorName,
                                     @NotNull(message = "请选择管理员角色") Long roleId,
                                     @NotNull(message = "请输入管理员ID") Long operatorId,
                                     @NotNull(message = "请输入管理员电话") String mobile,
                                     @RequestParam(defaultValue = "1") Integer deleted) {
        systemManageService.saveOrUpdateOperator(operatorName, roleId, operatorId, mobile, deleted);
    }

    @RequestMapping("/deleteOperator")
    public void deleteOperator(@NotNull(message = "请选择要删除的管理员") Long operatorId) {
        operatorService.deleteOperator(operatorId);
        operatorRoleRelationService.deleteOperatorRole(operatorId);
    }
}
