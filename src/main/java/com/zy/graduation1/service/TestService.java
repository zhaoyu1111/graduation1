package com.zy.graduation1.service;

import com.zy.graduation1.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
public interface TestService extends IService<Test> {

    List<Test> select();
}
