package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.mapper.MenuMapper;
import com.zy.graduation1.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
