package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ClassDetail;
import com.zy.graduation1.dto.user.ClassDto;
import com.zy.graduation1.dto.user.CollegeDto;
import com.zy.graduation1.dto.user.MajorDto;
import com.zy.graduation1.entity.Class;
import com.zy.graduation1.entity.College;
import com.zy.graduation1.entity.Major;
import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OriginManageServiceImpl implements OriginManageService {

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private ClassService classService;

    @Autowired
    private OperatorService operatorService;

    @Override
    public MyPage<ClassDetail> queryClass(Long collegeId, String className, Integer page) {
        IPage<Class> classPage = classService.queryClass(collegeId, className, page);
        List<Class> classes = classPage.getRecords();
        if(CollectionUtils.isEmpty(classes)) {
            return new MyPage<>();
        }

        List<Long> collegeIds = classes.stream().map(Class::getCollegeId).distinct().collect(Collectors.toList());
        List<College> colleges =  collegeService.listCollege(collegeIds);
        Map<Long, String> collegeMap = Maps.newHashMap();
        colleges.forEach(college -> {
            collegeMap.put(college.getCollegeId(), college.getCollegeName());
        });

        List<Long> majorIds = classes.stream().map(Class::getMajorId).distinct().collect(Collectors.toList());
        List<Major> majors = majorService.listMajorByIds(majorIds);
        Map<Long, String> majorMap = Maps.newHashMap();
        majors.forEach(major -> {
            majorMap.put(major.getMajorId(), major.getMajorName());
        });

        List<ClassDetail> details = Lists.newArrayList();
        for (Class aClass : classes) {
            ClassDetail classDetail = new ClassDetail();
            BeanUtils.copyProperties(aClass, classDetail);
            classDetail.setCollegeName(collegeMap.get(aClass.getCollegeId()));
            classDetail.setMajorName(collegeMap.get(aClass.getMajorId()));
            details.add(classDetail);
        }
        return new MyPage<>(classPage.getTotal(), details);
    }

    @Override
    public MyPage<MajorDto> queryMajor(Long collegeId, Long majorId, Integer currentPage) {
        IPage<Major> majorPage = majorService.queryMajor(collegeId, majorId, currentPage);
        List<Major> majors = majorPage.getRecords();
        if(CollectionUtils.isEmpty(majors)) {
            return new MyPage<>();
        }

        /* 查询学院信息*/
        List<Long> collegeIds = majors.stream().map(Major::getCollegeId).distinct().collect(Collectors.toList());
        List<College> colleges = collegeService.listCollege(collegeIds);
        Map<Long, College> collegeMap = Maps.uniqueIndex(colleges.iterator(), College::getCollegeId);

        /* 查询专业管理员信息*/
        List<Long> majorOperatorIds = majors.stream().map(Major::getOperatorId).distinct().collect(Collectors.toList());
        List<Operator> majorOperators = operatorService.getOperatorInfo(majorOperatorIds);
        Map<Long, Operator> majorOperatorMap = Maps.uniqueIndex(majorOperators.iterator(), Operator::getOperatorId);

        /* 查询学院管理员信息*/
        List<Long> collegeOperatorIds = colleges.stream().map(College::getOperatorId).distinct().collect(Collectors.toList());
        List<Operator> collegeOperators = operatorService.getOperatorInfo(collegeOperatorIds);
        Map<Long, Operator> collegeOperatorMap = Maps.uniqueIndex(collegeOperators.iterator(), Operator::getOperatorId);

        List<MajorDto> majorInfos = Lists.newArrayList();
        for (Major major : majors) {
            MajorDto majorDto = new MajorDto();
            BeanUtils.copyProperties(major, majorDto);
            College college = collegeMap.get(major.getCollegeId());
            if(null != college) {
                majorDto.setCollegeName(college.getCollegeName());
                majorDto.setCollegeId(college.getCollegeId());
                Operator collegeOperator = collegeOperatorMap.get(college.getOperatorId());
                if(null != collegeOperator) {
                    majorDto.setCollegeOperatorName(collegeOperator.getOperatorName());
                    majorDto.setCollegeOperatorMobile(collegeOperator.getMobile());
                }
            }
            Operator majorOperator = majorOperatorMap.get(major.getOperatorId());
            if(null != majorOperator) {
                majorDto.setMajorOperatorName(majorOperator.getOperatorName());
                majorDto.setMajorOperatorMobile(majorOperator.getMobile());
            }
            majorInfos.add(majorDto);
        }
        return new MyPage<>(majorPage.getTotal(), majorInfos);
    }

    @Override
    public MyPage<CollegeDto> queryCollege(String collegeName, Integer currentPage) {
        IPage<College> collegePage = collegeService.queryCollegeInfo(collegeName, currentPage);
        List<College> colleges = collegePage.getRecords();
        if(CollectionUtils.isEmpty(colleges)) {
            return new MyPage<>();
        }

        /* 查询管理员信息*/
        List<Long> operatorIds = colleges.stream().map(College::getOperatorId).distinct().collect(Collectors.toList());
        List<Operator> operators = operatorService.getOperatorInfo(operatorIds);
        Map<Long, Operator> operatorMap = Maps.uniqueIndex(operators.iterator(), Operator::getOperatorId);

        List<CollegeDto> collegeInfos = Lists.newArrayList();
        for (College college : colleges) {
            CollegeDto collegeDto = new CollegeDto();
            BeanUtils.copyProperties(college, collegeDto);
            Operator operator = operatorMap.get(college.getOperatorId());
            if(null != operator) {
                collegeDto.setOperatorName(operator.getOperatorName());
                collegeDto.setMobile(operator.getMobile());
            }
            collegeInfos.add(collegeDto);
        }
        return new MyPage<>(collegePage.getTotal(), collegeInfos);
    }

    @Override
    public void saveCollege(Long collegeId, String collegeName, String operatorName) {
        Operator operator = operatorService.getOperatorInfo(operatorName);
        if(null == operator) {
            throw new BizException(BizErrorCode.ACCOUNT_NOT_EXIST);
        }

        College college = collegeService.selectById(collegeId);
        if(null != college) {
            throw new BizException(BizErrorCode.ORIGIN_EXIST);
        }
        collegeService.saveCollege(collegeId, collegeName, operator.getOperatorId());
    }

    @Override
    public void updateCollege(Long collegeId, String collegeName, String operatorName) {
        Operator operator = operatorService.getOperatorInfo(operatorName);
        if(null == operator) {
            throw new BizException(BizErrorCode.ACCOUNT_NOT_EXIST);
        }

        College college = collegeService.selectById(collegeId);
        if(null == college) {
            throw new BizException(BizErrorCode.ORIGIN_NOT_EXIST);
        }
        collegeService.updateCollege(collegeId, collegeName, operator.getOperatorId());
    }

    private Long validateCollege(Long collegeId, String operatorName) {
        Operator operator = operatorService.getOperatorInfo(operatorName);
        if(null == operator) {
            throw new BizException(BizErrorCode.ACCOUNT_NOT_EXIST);
        }

        College college = collegeService.selectById(collegeId);
        if(null != college) {
            throw new BizException(BizErrorCode.ORIGIN_EXIST);
        }
        return operator.getOperatorId();
    }
}
