package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class ArticleDto {

    /**
     * 文章ID
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 文章内容
     */
    private String context;
    /**
     * 文章来源
     */
    private String source;
    /**
     * 点击数
     */
    private Integer count;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 菜单Id
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;
}
