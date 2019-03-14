package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.Major;
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
public interface MajorService extends IService<Major> {

    /**
     * 通过majorIds批量查询专业信息
     * @param majorIds
     * @return
     */
    List<Major> listMajorByIds(List<Long> majorIds);

    /**
     * 根据专业名称查询专业信息
     * @param majorName
     * @return
     */
    Major getMajorInfo(String majorName);

    /**
     * 删除专业信息
     * @param majorId
     * @return
     */
    Boolean deleteMajor(Long majorId);

    /**
     * 新增或修改专业信息
     * @param major
     * @return
     */
    Boolean saveOrUpdateMajor(Major major);

    /**
     * 查询专业信息
     * @param collegeId
     * @return
     */
    IPage<Major> queryMajor(Long operatorId, Long collegeId, Long majorId, Integer currentPage);

    /**
     * 批量查询专业信息
     * @param majorIds
     * @return
     */
    List<Major> listMajor();
}
