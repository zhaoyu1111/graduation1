package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Activity;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.ActivityMapper;
import com.zy.graduation1.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-03-08
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Override
    public IPage<Activity> queryActivity(String activityName, Long startTime, Integer status, Integer page) {
        QueryWrapper<Activity> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(activityName)) {
            query.like("activity_name", activityName);
        }

        if(null != startTime) {
            query.ge("start_time", startTime);
        }

        if(null != status) {
            query.eq("status", status);
        }

        Page<Activity> activityPage = new Page<>(page, 10);
        return baseMapper.selectPage(activityPage, query);
    }

    @Override
    public void saveOrUpdateActivity(Activity activity) {
        this.insertOrUpdate(activity);
    }

    @Override
    public void deleteActivity(Long activityId) {
        Activity activity = baseMapper.selectById(activityId);
        if(null == activity) {
            throw new BizException(OriginErrorCode.ACTIVITY_NOT_EXIST);
        }
        baseMapper.deleteById(activity);
    }

    @Override
    public Activity getActivity(Long activityId) {
        return baseMapper.selectById(activityId);
    }
}
