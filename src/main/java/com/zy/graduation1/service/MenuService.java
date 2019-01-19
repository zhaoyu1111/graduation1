package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
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

    /**
     * 分页查询菜单列表
     * @return
     */
    IPage<Menu> queryMenu(String title, Integer currentPage);

    /**
     * 删除菜单
     * @param menuId
     */
    void deleteMenu(Long menuId);

    /**
     * 更新或新增菜单
     * @param menuId
     * @param title
     * @param href
     * @param icon
     * @param parentId
     */
    void saveOrUpdateMenu(Long menuId, String title, String href, String icon, Long parentId);
}
