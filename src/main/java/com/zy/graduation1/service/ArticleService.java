package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-03-19
 */
public interface ArticleService extends IService<Article> {

    IPage<Article> queryArticle(Long menuId, String articleName, Integer currentPage);

    void toggleArticle(Long articleId);

    String getContext(Long id);
}
