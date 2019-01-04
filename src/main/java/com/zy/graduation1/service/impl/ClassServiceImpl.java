package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.mapper.ClassMapper;
import com.zy.graduation1.service.ClassService;
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
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Override
    public List<Class> listClassInfo(List<Long> classIds) {
        QueryWrapper<Class> query = new QueryWrapper<>();
        query.in("class_id", classIds);
        return selectList(query);
    }
}
