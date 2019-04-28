package com.zy.graduation1.service;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.OperatorDto;
import com.zy.graduation1.dto.user.OperatorRoleDto;
import com.zy.graduation1.dto.user.RoleMenuRelationDto;
import com.zy.graduation1.entity.OperatorRoleRelation;

public interface SystemManageService {

    /**
     * 分页查询管理员角色表（管理员名称不支持模糊匹配）
     * @param operatorName
     * @param roleName
     * @return
     */
    MyPage<OperatorRoleRelation> queryOperatorRole(String operatorName, String roleName);

    /**
     * 新增角色菜单
     * @param roleId
     * @param menuId
     * @param relationId
     */
    void saveOrUpdateRoleMenu(Long relationId, Long roleId, Long menuId);

    /**
     * 查詢菜單角色列表
     * @param roleId
     * @param menuId
     * @param currentPage
     * @return
     */
    MyPage<RoleMenuRelationDto> queryRoleMenuRelation(Long roleId, Long menuId, Integer currentPage);

    /**
     * 分页查询管理员角色
     * @param roleId
     * @param operatorName
     * @param currentPage
     * @return
     */
    MyPage<OperatorRoleDto> queryOperatorRoleRelation(Long roleId, String operatorName, Integer currentPage);

    /**
     * 更新或新增管理员角色
     * @param relationId
     * @param operatorName
     * @param roleId
     */
    void saveOrUpdateOperatorRole(Long relationId, String operatorName, Long roleId);

    /**
     * 分页查询管理员信息
     * @param operatorName
     * @param roleId
     * @param currentPage
     * @return
     */
    MyPage<OperatorDto> queryOperator(String operatorName, Long roleId, Integer deleted, Integer currentPage);

    /**
     * 新增或更新管理员信息
     * @param operatorName
     * @param roleId
     * @param operatorId
     * @param mobile
     * @param deleted
     */
    void saveOrUpdateOperator(String operatorName, Long roleId, Long operatorId,
                              String mobile, Integer deleted);
}
