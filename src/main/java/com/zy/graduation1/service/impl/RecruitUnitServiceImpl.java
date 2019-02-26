package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.RecruitUnit;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.RecruitUnitMapper;
import com.zy.graduation1.service.RecruitUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-05
 */
@Service
public class RecruitUnitServiceImpl extends ServiceImpl<RecruitUnitMapper, RecruitUnit> implements RecruitUnitService {

    @Override
    public Boolean saveOrUpdateUnit(RecruitUnit recruitUnit) {
        return this.insertOrUpdate(recruitUnit);
    }

    @Override
    public Boolean deleteUnit(Long unitId) {
        RecruitUnit unit = baseMapper.selectById(unitId);
        if(null == unit) {
            throw new BizException(OriginErrorCode.UNIT_NOT_EXIST);
        }
        unit.setDeleted(0);
        return baseMapper.updateById(unit) == 1;
    }

    @Override
    public IPage<RecruitUnit> queryRecruitUnit(String unitName, Integer property, Integer status, Integer currentPage) {
        Page<RecruitUnit> page = new Page<>(currentPage, 20);
        QueryWrapper<RecruitUnit> query = new QueryWrapper<>();
        query.orderByDesc("ctime");
        if(StringUtils.isNotEmpty(unitName)) {
            query.like("unit_name", unitName);
        }
        if(null != property) {
            query.eq("property", property);
        }
        if(null != status) {
            query.eq("status", status);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public RecruitUnit getRecruitUnit(Long unitId) {
        return baseMapper.selectById(unitId);
    }
}
