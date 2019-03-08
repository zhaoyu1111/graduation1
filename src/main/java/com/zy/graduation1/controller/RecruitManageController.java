package com.zy.graduation1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.RecruitDto;
import com.zy.graduation1.dto.user.RecruitUnitDto;
import com.zy.graduation1.entity.Recruit;
import com.zy.graduation1.entity.RecruitUnit;
import com.zy.graduation1.service.RecruitService;
import com.zy.graduation1.service.RecruitUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/web/recruit")
public class RecruitManageController {

    @Autowired
    private RecruitUnitService recruitUnitService;

    @Autowired
    private RecruitService recruitService;

    @RequestMapping("/queryRecruitUnit")
    public MyPage<RecruitUnitDto> queryRecruitUnit(String unitName, Integer property, Integer status,
                                                   @RequestParam(defaultValue = "1") Integer currentPage) {
        IPage<RecruitUnit> recruitUnitPage = recruitUnitService.queryRecruitUnit(unitName, property, status, currentPage);
        List<RecruitUnit> units = recruitUnitPage.getRecords();
        if(CollectionUtils.isEmpty(units)) {
            return new MyPage<>();
        }

        List<RecruitUnitDto> unitDtos = Lists.newArrayList();
        for (RecruitUnit unit : units) {
            RecruitUnitDto unitDto = new RecruitUnitDto();
            BeanUtils.copyProperties(unit, unitDto);
            unitDtos.add(unitDto);
        }
        return new MyPage<>(recruitUnitPage.getTotal(), unitDtos);
    }

    @RequestMapping("/saveOrUpdateUnit")
    public void saveOrUpdateUnit(RecruitUnit recruitUnit) {
        if(null == recruitUnit) {
            return ;
        }
        recruitUnitService.saveOrUpdateUnit(recruitUnit);
    }

    @RequestMapping("/getAllUnit")
    public List<RecruitUnit> getAllUnit() {
        return recruitUnitService.getAllRecruitUnit();
    }



    @RequestMapping("/deleteUnit")
    public void deleteUnit(@NotNull(message = "请选择需要删除的招聘单位") Long unitId) {
        recruitUnitService.deleteUnit(unitId);
    }

    @RequestMapping("/getRecruitUnit")
    public RecruitUnit getRecruitUnit(@NotNull(message = "请选择需要编辑的招聘单位") Long unitId) {
        return recruitUnitService.getRecruitUnit(unitId);
    }

    @RequestMapping("/queryRecruit")
    public MyPage<RecruitDto> queryRecruit(Long unitId, Long applyId, String title, Integer page) {
        IPage<Recruit> recruitPage = recruitService.queryRecruit(unitId, applyId, title, page);
        List<Recruit> recruits = recruitPage.getRecords();
        if(CollectionUtils.isEmpty(recruits)) {
            return  new MyPage<>();
        }

        List<Long> unitIds = recruits.stream().map(Recruit::getUnitId).distinct().collect(Collectors.toList());
        List<RecruitUnit> recruitUnits = recruitUnitService.getRecruitUnit(unitIds);
        Map<Long, RecruitUnit> unitMap = Maps.newHashMap();
        recruitUnits.forEach(recruitUnit -> {
            unitMap.put(recruitUnit.getUnitId(), recruitUnit);
        });

        List<RecruitDto> recruitDtos = Lists.newArrayList();
        for (Recruit recruit : recruits) {
            RecruitDto recruitDto = new RecruitDto();
            BeanUtils.copyProperties(recruit, recruitDto);
            RecruitUnit recruitUnit = unitMap.get(recruit.getUnitId());
            if(null != recruitUnit) {
                recruitDto.setUnitName(recruitUnit.getUnitName());
            }
            recruitDtos.add(recruitDto);
        }
        return new MyPage<>(recruitPage.getTotal(), recruitDtos);
    }

    @RequestMapping("/saveOrUpdateRecruit")
    public void saveOrUpdateRecruit(Long recuritId,@NotBlank(message = "请输入标题") String title,
                                    @NotBlank(message = "请输入薪酬范围") String salary,@NotBlank(message = "请输入招聘人数") Integer members,
                                    @NotBlank(message = "请输入结束时间") Long endTime,@NotBlank(message = "请输入联系人") String contractor,
                                    @NotBlank(message = "请输入手机号") String mobile,@NotBlank(message = "请输入职位名称") String posName,
                                    @RequestParam(defaultValue = "无") String posDescript,@NotBlank(message = "请输入福利") String welfare,
                                    @NotBlank(message = "请输入工作地点") String workPlace,@NotBlank(message = "请选择招聘公司") Long unitId,
                                    @NotBlank(message = "请输入邮箱") Long email,@RequestParam(defaultValue = "1") Integer status,
                                    @RequestParam(defaultValue = "0") Integer deleted) {
        Recruit recruit = new Recruit();
        recruit.setTitle(title).setMembers(members).setEndTime(endTime).setContractor(contractor).setMobile(mobile).setPosName(posName)
                .setSalary(salary).setPosDescript(posDescript).setWelfare(welfare).setWorkPlace(workPlace).setUnitId(unitId).setEmail(email)
                .setDeleted(deleted).setStatus(status);
        if(null != recuritId) {
            recruit.setRecuritId(recuritId);
        }
        recruitService.saveOrUpdateRecruit(recruit);
    }

    @RequestMapping("/deleteRecruit")
    public void deleteRecruit(@NotNull(message = "请选择需要删除的职位") Long recruitId) {
        recruitService.deleteRecruit(recruitId);
    }

    @RequestMapping("/getRecruit")
    public Recruit getRecruit(@NotNull(message = "请选择需要修改的职位") Long recuritId) {
        return recruitService.getRecruit(recuritId);
    }
}
