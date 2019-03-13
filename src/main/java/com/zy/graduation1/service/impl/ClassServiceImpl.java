package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.mapper.ClassMapper;
import com.zy.graduation1.service.ClassService;
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
    public IPage<Class> queryClass(Long collegeId, String className, Integer page) {
        Page<Class> classPage = new Page<>(page, 10);
        QueryWrapper<Class> query = new QueryWrapper<>();
        if(null != collegeId) {
            query.eq("college_id", collegeId);
        }
        if(StringUtils.isNotEmpty(className)) {
            query.eq("class_name", className);
        }
        return baseMapper.selectPage(classPage, query);
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
