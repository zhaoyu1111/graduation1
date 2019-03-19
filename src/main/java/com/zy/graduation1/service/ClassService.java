package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
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

    /**
     * 删除班级
     * @param classId
     * @return
     */
    Boolean deleteClass(Long classId);

    /**
     * 新增或删除班级信息
     * @param classs
     * @return
     */
    Boolean saveOrUpdateClass(Class classs);

    /**
     * 查询班级信息
     * @param classId
     * @param majorId
     * @param collegeId
     * @param currentPage
     * @return
     */
    IPage<Class> queryClass(Long collegeId, String className, Integer page);

    /**
     * 获取班级
     * @param majorId
     * @return
     */
    List<Class> getClass(Long majorId);
}
