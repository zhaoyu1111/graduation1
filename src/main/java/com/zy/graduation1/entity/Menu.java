package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.zy.graduation1.entity.SuperEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Menu extends SuperEntity<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;
    /**
     * 菜单名称
     */
    @TableField("title")
    private String title;
    /**
     * 菜单url
     */
    @TableField("href")
    private String href;
    /**
     * 菜单icon
     */
    @TableField("icon")
    private String icon;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 父ID，为0表示根目录
     * @return
     */
    private Long parentId;


    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
