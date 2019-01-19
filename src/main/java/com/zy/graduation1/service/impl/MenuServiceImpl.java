package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.mapper.MenuMapper;
import com.zy.graduation1.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> listMenu(List<Long> menuIds) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.in("menu_id", menuIds);
        return baseMapper.selectList(query);
    }

    @Override
    public IPage<Menu> queryMenu(String title, Integer currentPage) {
        Page<Menu> page = new Page<>(currentPage, 10);
        QueryWrapper<Menu> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(title)){
            query.like("title", title);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public void deleteMenu(Long menuId) {
        baseMapper.deleteById(menuId);
    }

    @Override
    public void saveOrUpdateMenu(Long menuId, String title, String href, String icon, Long parentId) {
        Menu menu = new Menu();
        menu.setTitle(title).setHref(href).setIcon(icon).setParentId(parentId);
        if(null != menuId) {
            menu.setMenuId(menuId);
        }
        this.insertOrUpdate(menu);
    }
}
