package com.zy.graduation1.service;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ClassDto;
import com.zy.graduation1.dto.user.CollegeDto;
import com.zy.graduation1.dto.user.MajorDto;

public interface OriginManageService {

    /**
     * 分页查询班级信息
     * @param classId
     * @param collegeId
     * @param majorId
     * @param currentPage
     * @return
     */
    MyPage<ClassDto> queryClass(Long classId, Long collegeId, Long majorId, Integer currentPage);

    /**
     * 分页查询专业信息
     * @param collegeId
     * @param majorId
     * @return
     */
    MyPage<MajorDto> queryMajor(Long collegeId, Long majorId, Integer currentPage);

    /**
     * 分页查询学院信息
     * @param collegeName
     * @return
     */
    MyPage<CollegeDto> queryCollege(String collegeName, Integer currentPage);
}
