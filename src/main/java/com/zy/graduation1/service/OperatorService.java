package com.zy.graduation1.service;

import com.zy.graduation1.entity.Operator;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
public interface OperatorService extends IService<Operator> {

    /**
     * 批量查询管理员信息
     * @param operatorIds
     * @return
     */
    List<Operator> getOperatorInfo(List<Long> operatorIds);
}
