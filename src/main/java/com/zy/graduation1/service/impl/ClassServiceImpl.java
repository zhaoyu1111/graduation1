package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
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
    public IPage<Class> queryClass(Long collegeId, String className, Long majorId, Integer page) {
        Page<Class> classPage = new Page<>(page, 10);
        QueryWrapper<Class> query = new QueryWrapper<>();
        if(null != collegeId) {
            query.eq("college_id", collegeId);
        }
        if(StringUtils.isNotEmpty(className)) {
            query.eq("class_name", className);
        }
        if(null != majorId) {
            query.eq("major_id", majorId);
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
        if(null != classs && !classs.getClassName().isEmpty()) {
            classs.setGeade(classs.getClassName().substring(0, 2));
        }
        return this.saveOrUpdateClass(classs);
    }

    @Override
    public List<Class> getClass(Long majorId) {
        QueryWrapper<Class> query = new QueryWrapper<>();
        query.eq("major_id", majorId);
        return baseMapper.selectList(query);
    }

    @Override
    public Long validClass(Long collegeId, Long majorId, String className) {
        QueryWrapper<Class> query = new QueryWrapper<>();
        query.eq("college_id", collegeId);
        query.eq("major_id", majorId);
        query.eq("class_name", className);
        Class cla = baseMapper.selectOne(query);
        if(null == cla) {
            throw new BizException(OriginErrorCode.CLASS_NOT_EXIST);
        }
        return cla.getClassId();
    }
}
