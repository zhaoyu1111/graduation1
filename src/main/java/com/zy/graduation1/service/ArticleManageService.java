package com.zy.graduation1.service;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ArticleDto;
import org.springframework.stereotype.Service;

public interface ArticleManageService {

    /**
     * 分页查询文章信息
     * @param menuId
     * @param articleName
     * @return
     */
    MyPage<ArticleDto> queryArticle(Long menuId, String articleName, Integer currentPage);

    /**
     * 新增或修改文章信息
     * @param articleId
     * @param title
     * @param author
     * @param context
     * @param source
     * @param status
     */
    void saveOrUpdateArticle(Long articleId, String title, String author, String context, String source, Long menuId, Integer status);

    /**
     * 删除文章信息
     * @param articleId
     */
    void deletedArticle(Long articleId);
}
