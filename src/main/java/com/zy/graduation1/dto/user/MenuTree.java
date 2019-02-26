package com.zy.graduation1.dto.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTree {

    /**
     * 菜单Id
     */
    private Long value;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 子菜单
     */
    private List<MenuTree> data;

    /**
     * 父Id
     */
    private Long parentId;

    /**
     * 是否选中
     */
    private Boolean checked;

    public MenuTree() {
        this.data = new ArrayList<>();
    }
}
