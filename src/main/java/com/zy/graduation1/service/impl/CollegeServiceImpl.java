package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.mapper.CollegeMapper;
import com.zy.graduation1.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
