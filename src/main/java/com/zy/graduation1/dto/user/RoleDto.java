package com.zy.graduation1.dto.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

@Data
public class RoleDto {
    /**
     * 角色ID
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Long ctime;
}
