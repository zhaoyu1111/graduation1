package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public IPage<Class> queryClassInfo(Long classId, Long majorId, Long collegeId, Integer currentPage) {
        Page<Class> page = new Page<>(currentPage, 20);
        QueryWrapper<Class> query = new QueryWrapper<>();
        if(null != classId) {
            query.eq("class_id", classId);
        }
        if(null != majorId) {
            query.eq("major_id", majorId);
        }
        if(null != collegeId) {
            query.eq("college_id", collegeId);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public Boolean deleteClass(Long classId) {
        QueryWrapper<Class> query = new QueryWrapper<>();
        query.eq("class_id", classId);
        return deleteById(classId);
    }

    @Override
    public Boolean saveOrUpdateClass(Class classs) {
        return this.saveOrUpdateClass(classs);
    }
}
