package com.zy.graduation1.controller;


import com.alibaba.fastjson.JSON;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ArticleDto;
import com.zy.graduation1.service.ArticleManageService;
import com.zy.graduation1.service.ArticleService;
import com.zy.graduation1.ueditor.PublicMsg;
import com.zy.graduation1.ueditor.Ueditor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Validated
@RestController
@RequestMapping("/web/article")
public class ArticleController {

    @Autowired
    private ArticleManageService articleManageService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/queryArticle")
    public MyPage<ArticleDto> queryArticle(Long menuId, String title, Integer page) {
        return articleManageService.queryArticle(menuId, title, page);
    }

    @RequestMapping("/saveOrUpdateArticle")
    public void saveOrUpdateArticle(Long articleId, @NotBlank(message = "请输入文章标题") String title,
                                    @NotBlank(message = "请输入作者") String author,
                                    String context, @NotNull(message = "请选择文章所属的菜单") Long menuId,
                                    String source, Integer status) {

        articleManageService.saveOrUpdateArticle(articleId, title, author, context, source, menuId, status);
    }

    /**
     * 删除文章信息
     * @param articleId
     */
    @RequestMapping("/deletedArticle")
    public void deletedArticle(@NotNull(message = "请选择需要删除的文章") Long articleId) {
        articleManageService.deletedArticle(articleId);
    }

    @RequestMapping("/toggleStatus")
    public void toggleStatus(@NotNull(message = "请选择需要操作的文章") Long articleId) {
        articleService.toggleArticle(articleId);
    }

    @RequestMapping("/getContext")
    public String getContext(@NotNull(message = "请选择需要操作的文章") Long articleId) {
        return articleService.getContext(articleId);
    }
}

