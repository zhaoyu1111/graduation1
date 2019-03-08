package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Recruit;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.RecruitMapper;
import com.zy.graduation1.service.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {

    @Override
    public Boolean saveOrUpdateRecruit(Recruit recruit) {
        return insertOrUpdate(recruit);
    }

    @Override
    public Boolean deleteRecruit(Long recruitId) {
        Recruit recruit = baseMapper.selectById(recruitId);
        if(null == recruit) {
            throw new BizException(OriginErrorCode.RECRUIT_NOT_EXIST);
        }
        return baseMapper.deleteById(recruit.getRecuritId()) == 1;
    }

    @Override
    public IPage<Recruit> queryRecruit(Long unitId, Long applyId, String title, Integer page) {
        QueryWrapper<Recruit> query = new QueryWrapper<>();
        Page<Recruit> recruitPage = new Page<>(page, 100);
        if(null != unitId) {
            query.eq("unit_id", unitId);
        }
        if(null != applyId) {
            query.eq("apply_id", applyId);
        }
        if(null != title) {
            query.like("title", title);
        }
        return baseMapper.selectPage(recruitPage, query);
    }

    @Override
    public Recruit getRecruit(Long recruitId) {
        return baseMapper.selectById(recruitId);
    }

    @Override
    public Boolean toggleRecruitStatus(Long recruitId) {
        Recruit recruit = baseMapper.selectById(recruitId);
        if(null == recruit) {
            throw new BizException(OriginErrorCode.RECRUIT_NOT_EXIST);
        }
        Integer oldStatus = recruit.getStatus();
        Integer newStatus = oldStatus == 0 ? 1 : 0;
        recruit.setStatus(newStatus);
        return baseMapper.updateById(recruit) == 1;
    }
}
