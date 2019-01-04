package com.zy.graduation1.service;

import com.zy.graduation1.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-04
 */
public interface CollegeService extends IService<College> {

    /**
     * 通过collegeIds批量查询学院信息
     * @param collegeIds
     * @return
     */
    List<College> listCollege(List<Long> collegeIds);
}
