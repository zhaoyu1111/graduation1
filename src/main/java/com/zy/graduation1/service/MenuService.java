package com.zy.graduation1.service;

import com.zy.graduation1.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-12
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过菜单Id获取菜单信息
     * @param menuIds
     * @return
     */
    List<Menu> listMenu(List<Long> menuIds);
}
