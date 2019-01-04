package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Major;
import com.zy.graduation1.mapper.MajorMapper;
import com.zy.graduation1.service.MajorService;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Override
    public List<Major> listMajor(List<Long> majorIds) {
        QueryWrapper<Major> query = new QueryWrapper<>();
        query.in("major_id", majorIds);
        return baseMapper.selectList(query);
    }
}
