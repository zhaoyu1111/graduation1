package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ArticleDto;
import com.zy.graduation1.entity.Article;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.service.ArticleManageService;
import com.zy.graduation1.service.ArticleService;
import com.zy.graduation1.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleManageServiceimpl implements ArticleManageService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MenuService menuService;

    @Override
    public MyPage<ArticleDto> queryArticle(Long menuId, String articleName, Integer currentPage) {
        IPage<Article> articleIPage = articleService.queryArticle(menuId, articleName, currentPage);
        List<Article> articles = articleIPage.getRecords();
        if(CollectionUtils.isEmpty(articles)) {
            return new MyPage<>();
        }

        List<Long> menuIds = articles.stream().map(Article::getMenuId).distinct().collect(Collectors.toList());
        List<Menu> menus = menuService.listMenu(menuIds);
        Map<Long, String> menuMap = Maps.newHashMap();
        menus.forEach(menu -> menuMap.put(menu.getMenuId(), menu.getTitle()));

        List<ArticleDto> articleDtos = Lists.newArrayList();
        for (Article article : articles) {
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(article, articleDto);
            articleDto.setMenuName(menuMap.get(article.getMenuId()));
            articleDtos.add(articleDto);
        }
        return new MyPage<>(articleIPage.getTotal(), articleDtos);
    }

    @Override
    public void saveOrUpdateArticle(Long articleId, String title, String author, String context, String source, Integer status) {
        Article article = new Article();
        article.setTitle(title).setAuthor(author).setContext(context).setSource(source).setStatus(status);
        if(null != articleId) {
            article.setArticleId(articleId);
        }
        articleService.insertOrUpdate(article);
    }

    @Override
    public void deletedArticle(Long articleId) {
        Article article = articleService.selectById(articleId);
        if(null == article) {
            throw new BizException(OriginErrorCode.ARTICLE_NOT_EXIST);
        }
        articleService.deleteById(articleId);
    }
}
