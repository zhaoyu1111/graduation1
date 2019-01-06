package com.zy.graduation1.service;

import com.zy.graduation1.dto.user.SessionDto;
import com.zy.graduation1.enums.SystemTypeStatue;

public interface UserSessionManageService {

    /**
     * protal用户登录
     * @param account
     * @param pwd
     */
    SessionDto protalLogin(Long account, String pwd, SystemTypeStatue typeStatue);

    /**
     * web用户登录
     * @param operatorId
     * @param pwd
     * @return
     */
    SessionDto webLogin(Long operatorId, String pwd);
}
