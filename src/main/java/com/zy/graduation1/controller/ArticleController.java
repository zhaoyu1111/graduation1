package com.zy.graduation1.controller;


import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ArticleDto;
import com.zy.graduation1.service.ArticleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2019-03-19
 */
@RestController
@RequestMapping("/web/article")
public class ArticleController {

    @Autowired
    private ArticleManageService articleManageService;

    @RequestMapping("/queryArticle")
    public MyPage<ArticleDto> queryArticle(Long menuId, String articleName, Integer page) {
        return articleManageService.queryArticle(menuId, articleName, page);
    }

    @RequestMapping("/saveOrUpdateArticle")
    public void saveOrUpdateArticle(Long articleId, @NotBlank(message = "请输入文章标题") String title,
                                    @NotBlank(message = "请输入作者") String author,
                                    @NotBlank(message = "请输入文章内容") String context,
                                    String source,@RequestParam(defaultValue = "1") Integer status) {

        articleManageService.saveOrUpdateArticle(articleId, title, author, context, source, status);
    }

    /**
     * 删除文章信息
     * @param articleId
     */
    @RequestMapping("/deletedArticle")
    public void deletedArticle(@NotNull(message = "请选择需要删除的文章") Long articleId) {
        articleManageService.deletedArticle(articleId);
    }
}

