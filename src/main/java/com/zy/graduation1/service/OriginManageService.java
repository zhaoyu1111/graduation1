package com.zy.graduation1.service;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.ClassDetail;
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
    MyPage<ClassDetail> queryClass(Long collegeId, String className, Integer page);

    /**
     * 分页查询专业信息
     * @param collegeId
     * @param majorId
     * @return
     */
    MyPage<MajorDto> queryMajor(Long operatorId, Long collegeId, Long majorId, Integer currentPage);

    /**
     * 分页查询学院信息
     * @param collegeName
     * @return
     */
    MyPage<CollegeDto> queryCollege(String collegeName, Long collegeId, Integer currentPage);

    /**
     * 新增学院信息（首先管理员要存在）
     * @param collegeId
     * @param collegeName
     * @param operatorName
     */
    void saveOrUpdateCollege(Long collegeId, String collegeName, String operatorName);

    /**
     * 更新学院信息
     * @param collegeId
     * @param collegeName
     * @param operatorName
     */
    void updateCollege(Long collegeId, String collegeName, String operatorName);

    /**
     * 新增或修改专业信息
     * @param majorId
     * @param majorName
     * @param collegeId
     */
    void saveOrUpdateMajor(Long majorId, String majorName, Long collegeId);
}
