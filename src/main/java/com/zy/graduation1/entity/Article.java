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
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends SuperEntity<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;
    /**
     * 文章标题
     */
    @TableField("title")
    private String title;
    /**
     * 作者
     */
    @TableField("author")
    private String author;
    /**
     * 文章内容
     */
    @TableField("context")
    private String context;
    /**
     * 文章来源
     */
    @TableField("source")
    private String source;
    /**
     * 点击数
     */
    @TableField("count")
    private Integer count;
    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    @TableField("menu_id")
    private Long menuId;


    @Override
    protected Serializable pkVal() {
        return this.articleId;
    }

}
