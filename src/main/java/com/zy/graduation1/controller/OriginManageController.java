package com.zy.graduation1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ClassDetail;
import com.zy.graduation1.dto.user.CollegeDto;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.entity.Major;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web/origin")
public class OriginManageController {

    @Autowired
    private OriginManageService originManageService;

    @Autowired
    private OriginService originService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Anonymous
    @RequestMapping("/deleteCollege")
    public void deleteCollege(Long collegeId) {
        collegeService.deleteById(collegeId);
    }

    @Anonymous
    @RequestMapping("/saveCollege")
    public void saveCollege(Long collegeId, String collegeName, String operatorName) {
        originManageService.saveCollege(collegeId, collegeName, operatorName);
    }

    @Anonymous
    @RequestMapping("/updateCollege")
    public void updateCollege(Long collegeId, String collegeName, String operatorName) {
        originManageService.updateCollege(collegeId, collegeName, operatorName);
    }

    @Anonymous
    @RequestMapping("/queryCollege")
    public MyPage<CollegeDto> queryCollege(String collegeName, Integer currentPage) {
        return originManageService.queryCollege(collegeName, currentPage);
    }

    @RequestMapping("/queryClass")
    public MyPage<ClassDetail> queryClass(Long collegeId, String className, Integer page) {
        return originManageService.queryClass(collegeId, className, page);
    }

    @RequestMapping("/listCollege")
    public List<College> listCollege() {
        return collegeService.listCollegeName();
    }

}
