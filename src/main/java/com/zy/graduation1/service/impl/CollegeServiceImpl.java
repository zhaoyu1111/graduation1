package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.CollegeMapper;
import com.zy.graduation1.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-01-04
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public List<College> listCollege(List<Long> collegeIds) {
        QueryWrapper<College> query = new QueryWrapper<>();
        query.in("college_id", collegeIds);
        return baseMapper.selectList(query);
    }

    @Override
    public IPage<College> queryCollegeInfo(String collegeName, Long collegeId, Integer currentPage) {
        QueryWrapper<College> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(collegeName)) {
            query.like("college_name", collegeName);
        }
        if(null != collegeId) {
            query.eq("college_id", collegeId);
        }
        Page<College> collegePage = new Page<>(currentPage, 20);
        return baseMapper.selectPage(collegePage, query);
    }

    @Override
    public List<College> listCollegeName() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Boolean saveOrUpdateCollege(Long collegeId, String collegeName, Long operatorId) {
        College college = new College();
        college.setCollegeName(collegeName).setOperatorId(operatorId);
        if(null != collegeId) {
            college.setCollegeId(collegeId);
        }
        return this.insertOrUpdate(college);
    }

    @Override
    public Boolean updateCollege(Long collegeId, String collegeName, Long operatorId) {
        College college = new College();
        college.setCollegeId(collegeId).setCollegeName(collegeName).setOperatorId(operatorId);
        return baseMapper.updateById(college) == 1;
    }

    @Override
    public College getCollege(String collegeName) {
        QueryWrapper<College> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(collegeName)) {
            query.eq("college_name", collegeName);
        }
        return baseMapper.selectOne(query);
    }

    @Override
    public List<College> getCollege(String collegeName, Long collegeId) {
        QueryWrapper<College> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(collegeName)) {
            query.eq("college_name", collegeName);
        }
        if(null != collegeId) {
            query.ne("college_id", collegeId);
        }
        return baseMapper.selectList(query);
    }

    @Override
    public List<College> getCollege(Long operatorId) {
        QueryWrapper<College> query = new QueryWrapper<>();
        if(null != operatorId) {
            query.eq("operator_id", operatorId);
        } else {
            return null;
        }
        return baseMapper.selectList(query);
    }

    @Override
    public Long validCollege(String collegeName) {
        QueryWrapper<College> query = new QueryWrapper<>();
        query.eq("college_name", collegeName);
        College college = baseMapper.selectOne(query);
        if(null == college) {
            throw new BizException(OriginErrorCode.COLLEGE_NOT_EXIST);
        }
        return college.getCollegeId();
    }
}
