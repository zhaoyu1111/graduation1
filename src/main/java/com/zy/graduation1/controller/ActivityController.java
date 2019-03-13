package com.zy.graduation1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ActivityDto;
import com.zy.graduation1.entity.Activity;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2019-03-08
 */
@RestController
@RequestMapping("/web/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/queryActivity")
    public MyPage<ActivityDto> queryActivity(String activityName, Long startTime, Integer status, Integer page) {
        IPage<Activity> activityPage = activityService.queryActivity(activityName, startTime, status, page);
        List<Activity> activities = activityPage.getRecords();
        if(CollectionUtils.isEmpty(activities)) {
            return new MyPage<>();
        }

        List<ActivityDto> activityDtos = Lists.newArrayList();
        for (Activity activity : activities) {
            ActivityDto activityDto = new ActivityDto();
            BeanUtils.copyProperties(activity, activityDto);
            activityDtos.add(activityDto);
        }

        return new MyPage<>(activityPage.getTotal(), activityDtos);
    }

    @RequestMapping("/saveOrUpdateActivity")
    public void saveOrUpdateActivity(Activity activity) {
        if(null == activity) {
            return ;
        }
        activityService.saveOrUpdateActivity(activity);
    }

    @RequestMapping("/deleteActivity")
    public void deleteActivity(@NotBlank(message = "请选择需要删除的活动") Long activityId) {
        Activity activity = activityService.getActivity(activityId);
        if(null == activity) {
            throw new BizException(OriginErrorCode.ACTIVITY_NOT_EXIST);
        }
        activityService.deleteActivity(activityId);
    }

    @RequestMapping("/getActivity")
    public Activity getActivity(@NotBlank(message = "请选择编辑的活动") Long activityId) {
        return activityService.getActivity(activityId);
    }
}

