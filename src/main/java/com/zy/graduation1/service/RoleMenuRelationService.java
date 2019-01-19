package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.entity.RoleMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
public interface RoleMenuRelationService extends IService<RoleMenuRelation> {

    /**
     * 通过角色ID获取菜单列表
     * @param roleId
     * @return
     */
    List<RoleMenuRelation> listRoleMenu(Long roleId);

    /**
     * 添加或更新角色菜单联系
     * @param roleId
     * @param meuId
     */
    void saveOrUpdateRoleMenu(Long relationId, Long roleId, Long meuId);

    /**
     * 删除角色菜单联系
     * @param relationId
     */
    void deleteRoleMenu(Long relationId);

    /**
     * 分頁查詢角色菜單列表
     * @param roleId
     * @param menuId
     * @param currentPage
     * @return
     */
    IPage<RoleMenuRelation> queryRoleMenuRelation(Long roleId, Long menuId, Integer currentPage);

    /**
     * 獲得角色菜單
     * @param roleId
     * @param menuId
     * @return
     */
    RoleMenuRelation getRoleMenuRelation(Long roleId, Long menuId);
}
