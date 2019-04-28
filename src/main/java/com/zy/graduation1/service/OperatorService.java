package com.zy.graduation1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.OperatorDto;
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

    /**
     * 通过管理员名称查询管理员信息（不支持模糊查询）
     * @param operatorName
     * @return
     */
    Operator getOperatorInfo(String operatorName);

    /**
     * 获取管理员信息
     * @param operatorId
     * @return
     */
    Operator getOperator(Long operatorId);

    /**
     * 分页查询管理员信息
     * @param operatorName
     * @param roleId
     * @param currentPage
     * @return
     */
    IPage<Operator> queryOperator(String operatorName, Long roleId, Integer deleted, Integer currentPage);

    /**
     * 新增管理员信息
     * @param operatorName
     * @param roleId
     * @param operatorId
     * @param mobile
     * @param deleted
     */
    void saveOperator(String operatorName, Long roleId, Long operatorId, String mobile, Integer deleted);

    /**
     * 更新管理员信息
     * @param operatorName
     * @param roleId
     * @param operatorId
     * @param mobile
     * @param deleted
     */
    void updateOperator(String operatorName, Long roleId, Long operatorId, String mobile, Integer deleted);

    /**
     * 删除管理员
     * @param operatorId
     */
    void deleteOperator(Long operatorId);

    /**
     * 获取管理员列表
     * @return
     */
    List<Operator> getOperator();
}
