package com.zy.graduation1.controller;

import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.common.RequestUser;
import com.zy.graduation1.dto.user.ClassDetail;
import com.zy.graduation1.dto.user.CollegeDto;
import com.zy.graduation1.dto.user.MajorDto;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.entity.Major;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
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

    @RequestMapping("/deleteCollege")
    public void deleteCollege(@NotNull(message = "请选择要删除的学院") Long collegeId) {
        College college = collegeService.selectById(collegeId);
        if(null == college) {
            throw new BizException(OriginErrorCode.COLLEGE_NOT_EXIST);
        }
        collegeService.deleteById(collegeId);
    }

    @RequestMapping("/saveOrUpdateCollege")
    public void saveCollege(Long collegeId,@NotBlank(message = "请输入学院名称") String collegeName,
                            @NotBlank(message = "请输入管理员名称") String operatorName) {
        originManageService.saveOrUpdateCollege(collegeId, collegeName, operatorName);
    }

    @Anonymous
    @RequestMapping("/queryCollege")
    public MyPage<CollegeDto> queryCollege(String collegeName, Long collegeId, Integer page) {
        return originManageService.queryCollege(collegeName, collegeId, page);
    }

    @RequestMapping("/queryClass")
    public MyPage<ClassDetail> queryClass(Long collegeId, String className, Integer page) {
        return originManageService.queryClass(collegeId, className, page);
    }

    @RequestMapping("/listCollege")
    public List<College> listCollege() {
        return collegeService.listCollegeName();
    }

    @RequestMapping("/listMajor")
    public List<Major> listMajor() {
        return majorService.listMajor();
    }

    @RequestMapping("/queryMajor")
    public MyPage<MajorDto> queryMajor(Long collegeId, Long majorId, Integer page) {
        return originManageService.queryMajor(RequestUser.getCurrentUser(), collegeId, majorId, page);
    }

    @RequestMapping("/getCollege")
    public List<College> getCollege() {
        return collegeService.getCollege(RequestUser.getCurrentUser());
    }

    @RequestMapping("/saveOrUpdateMajor")
    public void saveOrUpdateMajor(@NotNull(message = "请输入专业编号") Long majorId,
                                  @NotBlank(message = "请输入专业名称") String majorName,
                                  @NotNull(message = "请选择学院") Long collegeId) {
        originManageService.saveOrUpdateMajor(majorId, majorName, collegeId);
    }

    @RequestMapping("/deleteMajor")
    public void deleteMajor(@NotNull(message = "请选择需要删除的专业") Long majorId) {
        Major major = majorService.selectById(majorId);
        if(null == major) {
            throw new BizException(OriginErrorCode.MAJOR_NOT_EXIST);
        }
        majorService.deleteMajor(majorId);
    }

    @RequestMapping("/getMajor")
    public List<Major> getMajor(@NotNull(message = "请选择学院信息") Long collegeId) {
        College college = collegeService.selectById(collegeId);
        if(null == college) {
            throw new BizException(OriginErrorCode.COLLEGE_NOT_EXIST);
        }
        return majorService.getMajor(collegeId);
    }

}
