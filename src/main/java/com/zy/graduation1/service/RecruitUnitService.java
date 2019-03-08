package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.RecruitUnit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-05
 */
public interface RecruitUnitService extends IService<RecruitUnit> {

    /**
     * 新增或更新公司信息
     * @param recruitUnit
     * @return
     */
    Boolean saveOrUpdateUnit(RecruitUnit recruitUnit);

    /**
     * 删除公司
     * @param unitId
     * @return
     */
    Boolean deleteUnit(Long unitId);

    /**
     * 分页查询公司信息
     * @param unitName
     * @param property
     * @param currentPage
     * @return
     */
    IPage<RecruitUnit> queryRecruitUnit(String unitName, Integer property, Integer status, Integer currentPage);

    /**
     * 通过单位ID查询单位信息
     * @param unitId
     * @return
     */
    RecruitUnit getRecruitUnit(Long unitId);

    /**
     * 获取招聘单位信息
     * @param unitIds
     * @return
     */
    List<RecruitUnit> getRecruitUnit(List<Long> unitIds);

    /**
     * 获取所有的单位信息
     * @return
     */
    List<RecruitUnit> getAllRecruitUnit();
}
