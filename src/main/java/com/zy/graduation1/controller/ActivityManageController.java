package com.zy.graduation1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.ActivityDto;
import com.zy.graduation1.dto.user.DonationDto;
import com.zy.graduation1.entity.Activity;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.service.ActivityService;
import com.zy.graduation1.service.DonationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2019-03-08
 */
@Validated
@RestController
@RequestMapping("/web/activity")
public class ActivityManageController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DonationService donationService;

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
    public void deleteActivity(@NotNull(message = "请选择需要删除的活动") Long activityId) {
        Activity activity = activityService.getActivity(activityId);
        if(null == activity) {
            throw new BizException(OriginErrorCode.ACTIVITY_NOT_EXIST);
        }
        activityService.deleteActivity(activityId);
    }

    @RequestMapping("/getActivity")
    public Activity getActivity(@NotNull(message = "请选择编辑的活动") Long activityId) {
        return activityService.getActivity(activityId);
    }

    @RequestMapping("queryDonation")
    public  MyPage<DonationDto> queryDonation(String donationName, Integer donationObject,
                                              Integer status, Integer page) {
        return donationService.queryDonation(donationName, donationObject, status, page);
    }

    @RequestMapping("saveOrUpdateDonation")
    public void saveOrUpdateDonation(Long donationId, Long donationUid,
                                     @NotBlank(message = "请输入捐赠人姓名") String donationName,
                                     @NotBlank(message = "请输入联系方式") String donationMobile,
                                     @NotBlank(message = "请输入物品名称") String goodsName,
                                     @NotNull(message = "请输入捐赠数量")Integer amount,
                                     @NotNull(message = "请选择捐赠对象") Integer donationObject,
                                     @NotBlank(message = "请输入捐赠对象名称，如：软件学院或赵宇") String objectName,
                                     String unitName) {
        donationService.saveOrUpdateDonation(donationId, donationUid, donationName, donationMobile,
                goodsName, amount, donationObject, objectName, unitName);
    }

    @RequestMapping("/deleteDonation")
    public void deleteDonation(Long donationId) {
        donationService.deleteDonation(donationId);
    }

    @RequestMapping("/completeDonation")
    public void completeDonation(@NotNull(message = "请选择捐赠项目") Long donationId) {
        donationService.completeDonation(donationId, RequestUser.getCurrentUser());
    }
}

