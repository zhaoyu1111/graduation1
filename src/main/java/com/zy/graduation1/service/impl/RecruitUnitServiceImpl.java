package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.RecruitUnit;
import com.zy.graduation1.enums.YesOrNoEnum;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.RecruitUnitMapper;
import com.zy.graduation1.service.RecruitUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return baseMapper.deleteById(unit.getUnitId()) == 1;
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

    @Override
    public List<RecruitUnit> getRecruitUnit(List<Long> unitIds) {
        QueryWrapper<RecruitUnit> query = new QueryWrapper<>();
        query.in("unit_id", unitIds);
        return baseMapper.selectList(query);
    }

    @Override
    public List<RecruitUnit> getAllRecruitUnit() {
        QueryWrapper<RecruitUnit> query = new QueryWrapper<>();
        query.eq("deleted", YesOrNoEnum.NO.getCode());
        query.eq("status", 3);
        return baseMapper.selectList(query);
    }
}
