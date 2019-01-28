package com.zy.graduation1.controller;

import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.CollegeDto;
import com.zy.graduation1.service.CollegeService;
import com.zy.graduation1.service.OriginManageService;
import com.zy.graduation1.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/origin")
public class OriginManageController {

    @Autowired
    private OriginManageService originManageService;

    @Autowired
    private OriginService originService;

    @Autowired
    private CollegeService collegeService;

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

}
