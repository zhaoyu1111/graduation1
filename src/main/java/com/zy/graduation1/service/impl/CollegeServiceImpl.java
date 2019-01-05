package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.College;
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
    public IPage<College> queryCollegeInfo(String collegeName, Integer currentPage) {
        QueryWrapper<College> query = new QueryWrapper<>();
        query.like("college_name", collegeName);
        Page<College> collegePage = new Page<>(currentPage, 20);
        return baseMapper.selectPage(collegePage, query);
    }

    @Override
    public List<College> listCollegeName() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Boolean deleteCollege(Long collegeId) {
        QueryWrapper<College> query = new QueryWrapper<>();
        query.eq("college_id", collegeId);
        return baseMapper.deleteById(collegeId) == 1;
    }

    @Override
    public Boolean saveOrUppdateCollege(College college) {
        return this.insertOrUpdate(college);
    }
}
