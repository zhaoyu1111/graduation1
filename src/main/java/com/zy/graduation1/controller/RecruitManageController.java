package com.zy.graduation1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.RecruitUnitDto;
import com.zy.graduation1.entity.RecruitUnit;
import com.zy.graduation1.service.RecruitUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/web/recruit")
public class RecruitManageController {

    @Autowired
    private RecruitUnitService recruitUnitService;

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
    public void saveOrUpdateUnit(@RequestBody RecruitUnit recruitUnit) {
        if(null == recruitUnit) {
            return ;
        }
        recruitUnitService.saveOrUpdateUnit(recruitUnit);
    }

    @RequestMapping("/deleteUnit")
    public void deleteUnit(@NotNull(message = "请选择需要删除的招聘单位") Long unitId) {
        recruitUnitService.deleteUnit(unitId);
    }
}
