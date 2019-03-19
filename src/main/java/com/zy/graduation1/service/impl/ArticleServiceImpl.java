package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Article;
import com.zy.graduation1.mapper.ArticleMapper;
import com.zy.graduation1.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-03-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> queryArticle(Long menuId, String articleName, Integer currentPage) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        Page<Article> page = new Page<>(currentPage, 10);
        if(null != menuId) {
            query.eq("menu_id", menuId);
        }
        if(StringUtils.isNotEmpty(articleName)) {
            query.like("article_name", articleName);
        }
        return baseMapper.selectPage(page, query);
    }
}
