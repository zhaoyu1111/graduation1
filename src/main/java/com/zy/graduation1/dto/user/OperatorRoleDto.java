package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class OperatorRoleDto {

    /**
     * 联系ID
     */
    private Long relationId;
    /**
     * 管理员ID
     */
    private Long operatorId;
    /**
     * 菜单ID
     */
    private Long roleId;
    /**
     * 是否删除0-是 1-否
     */
    private Integer deleted;

    /**
     * 管理员名称
     */
    private String operatorName;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long utime;
}
