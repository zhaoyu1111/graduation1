package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.UserDto;
import com.zy.graduation1.dto.user.UserInfoDto;
import com.zy.graduation1.entity.Menu;
import com.zy.graduation1.entity.User;

import java.util.List;

public interface UserInfoManageService {

    /**
     * 查询改管理员下的用户
     * @return
     */
    MyPage<UserDto> queryUser(Long operatorId, Long majorId, Long classId,
                                  String nameOrId, Integer currentPage);

    /**
     * 获取用户信息
     * @param studentId
     * @return
     */
    User getUser(Long studentId);

    /**
     * 通过角色获取菜单列表
     * @param roleId
     * @return
     */
    List<Menu> listMenu(Long roleId);
}
