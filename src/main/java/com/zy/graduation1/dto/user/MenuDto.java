package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class MenuDto {

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单url
     */
    private String href;
    /**
     * 菜单icon
     */
    private String icon;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父ID，为0表示根目录
     * @return
     */
    private Long parentId;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long utime;
}
