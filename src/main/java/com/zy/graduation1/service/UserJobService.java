package com.zy.graduation1.service;

import com.zy.graduation1.entity.UserJob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-04-25
 */
public interface UserJobService extends IService<UserJob> {

    void updateStatus(Long unitId);
}
