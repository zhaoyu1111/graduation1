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
    IPage<College> queryCollegeInfo(String collegeName, Long collegeId, Integer currentPage);

    /**
     * 查询学院
     * @return
     */
    List<College> listCollegeName();

    /**
     * 增加学院(当collegeId为空时表示增加)
     * @param collegeId
     * @param collegeName
     * @param operatorId
     * @return
     */
    Boolean saveOrUpdateCollege(Long collegeId, String collegeName, Long operatorId);

    /**
     * 更新学院信息
     * @param collegeId
     * @param collegeName
     * @param operatorId
     * @return
     */
    Boolean updateCollege(Long collegeId, String collegeName, Long operatorId);

    /**
     * 查询学院是否存在
     * @param collegeName
     * @return
     */
    College getCollege(String collegeName);

    /**
     * 查询学院信息
     * @param collegeName
     * @param collegeId
     * @return
     */
    List<College> getCollege(String collegeName, Long collegeId);

    /**
     * 查询学院信息
     * @param operatorId
     * @return
     */
    List<College> getCollege(Long operatorId);

    /**
     * 验证学院是否存在
     * @param collegeName
     * @return
     */
    Long validCollege(String collegeName);
}
