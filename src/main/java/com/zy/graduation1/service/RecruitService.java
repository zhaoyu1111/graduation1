package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.Recruit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-05
 */
public interface RecruitService extends IService<Recruit> {

    /**
     * 增加或修改职位信息
     * @param recruit
     * @return
     */
    Boolean saveOrUpdateRecruit(Recruit recruit);

    /**
     * 删除职位信息
     * @param recruit
     * @return
     */
    Boolean deleteRecruit(Long recruit);

    /**
     * 分页查询职位信息
     * @param unitId
     * @param applyId
     * @param title
     * @return
     */
    IPage<Recruit> queryRecruit(Long unitId, Long applyId, String title, Integer currentPage);

    /**
     * 通过职位ID查询职位信息
     * @param recruitId
     * @return
     */
    Recruit getRecruit(Long recruitId);

    /**
     * 切换职位状态
     * @param recruitId
     * @return
     */
    Boolean toggleRecruitStatus(Long recruitId);
}
