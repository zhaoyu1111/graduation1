package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class OperatorDto {

    private Long operatorId;
    /**
     * 管理员名称
     */
    private String operatorName;
    /**
     * 管理员状态0-冻结 1-正常
     */
    private Integer operatorStatus;
    /**
     * 是否删除0-否 1-是
     */
    private Integer deleted;

    private String mobile;

    private Long roleId;

    private String roleName;

    private Long ctime;
}
