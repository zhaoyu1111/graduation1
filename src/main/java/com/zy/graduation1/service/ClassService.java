package com.zy.graduation1.service;

import com.zy.graduation1.entity.Class;
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
public interface ClassService extends IService<Class> {

    /**
     * 通过classIds批量查询班级信息
     * @param classIds
     * @return
     */
    List<Class> listClassInfo(List<Long> classIds);
}
