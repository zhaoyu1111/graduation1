package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * 根据学院名称查询学院信息
     * @param collegeName
     * @return
     */
    IPage<College> queryCollegeInfo(String collegeName, Integer currentPage);

    /**
     * 查询学院
     * @return
     */
    List<College> listCollegeName();

    /**
     * 删除学院
     * @param collegeId
     * @return
     */
    Boolean deleteCollege(Long collegeId);

    /**
     * 增加或修改学院(当collegeId为空时表示增加)
     * @param college
     * @return
     */
    Boolean saveOrUppdateCollege(College college);
}
