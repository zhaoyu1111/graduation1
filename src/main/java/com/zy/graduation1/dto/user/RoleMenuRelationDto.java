package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class RoleMenuRelationDto {

    /**
     * 联系ID
     */
    private Long relationId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单状态
     */
    private Integer status;

    /**
     * 角色名稱
     */
    private String roleName;

    /**
     * 菜單名稱
     */
    private String menuName;

    /**
     * 創建時間
     */
    private Long ctime;

    /**
     * 更新時間
     */
    private Long utime;
}
