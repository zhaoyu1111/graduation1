package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Test;
import com.zy.graduation1.mapper.TestMapper;
import com.zy.graduation1.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public List<Test> select() {
        QueryWrapper query = new QueryWrapper();
        query.eq("gender", 1);
        return baseMapper.selectList(query);
    }
}
