package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Major;
import com.zy.graduation1.mapper.MajorMapper;
import com.zy.graduation1.service.MajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-04
 */
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Override
    public List<Major> listMajorByIds(List<Long> majorIds) {
        QueryWrapper<Major> query = new QueryWrapper<>();
        query.in("major_id", majorIds);
        return baseMapper.selectList(query);
    }

    @Override
    public Major getMajorInfo(String majorName) {
        QueryWrapper<Major> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(majorName)) {
            query.like("major_name", majorName);
        }
        List<Major> majors = baseMapper.selectList(query);
        return CollectionUtils.isEmpty(majors) ? null : majors.get(0);
    }

    @Override
    public Boolean deleteMajor(Long majorId) {
        QueryWrapper<Major> query = new QueryWrapper();
        query.eq("major_id", majorId);
        return baseMapper.deleteById(majorId) == 1;
    }

    @Override
    public Boolean saveOrUpdateMajor(Major major) {
        return this.insertOrUpdate(major);
    }

    @Override
    public IPage<Major> queryMajor(Long operatorId, Long collegeId, Long majorId, Integer currentPage) {
        Page<Major> majorPage = new Page<>(currentPage, 20);
        QueryWrapper<Major> query = new QueryWrapper<>();
        if(null != collegeId) {
            query.eq("college_id", collegeId);
        }
        if(null != majorId) {
            query.eq("major_id", majorId);
        }

        if(null != operatorId) {
            query.eq("operator_id", operatorId);
        }
        query.orderByAsc("college_id").orderByDesc("major_id");
        return baseMapper.selectPage(majorPage, query);
    }

    @Override
    public List<Major> listMajor() {
        return baseMapper.selectList(new QueryWrapper<>());
    }
}
