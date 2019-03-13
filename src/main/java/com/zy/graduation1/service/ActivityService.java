package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-03-08
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 分页查询活动列表
     * @param activityName
     * @return
     */
    IPage<Activity> queryActivity(String activityName, Long startTime, Integer status, Integer page);

    /**
     * 新增或修改活动
     * @param activity
     */
    void saveOrUpdateActivity(Activity activity);

    /**
     * 删除活动
     * @param activityId
     */
    void deleteActivity(Long activityId);

    /**
     * 获取活动信息
     * @param activityId
     */
    Activity getActivity(Long activityId);
}
