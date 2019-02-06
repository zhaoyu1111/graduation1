package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.mapper.OperatorMapper;
import com.zy.graduation1.service.OperatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.graduation1.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
@Service
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, Operator> implements OperatorService {

    @Override
    public List<Operator> getOperatorInfo(List<Long> operatorIds) {
        QueryWrapper<Operator> query = new QueryWrapper<>();
        query.in("operator_id", operatorIds);
        return baseMapper.selectList(query);
    }

    @Override
    public Operator getOperatorInfo(String operatorName) {
        QueryWrapper<Operator> query = new QueryWrapper<>();
        query.eq("operator_name", operatorName);
        return baseMapper.selectOne(query);
    }

    @Override
    public Operator getOperator(Long operatorId) {
        return baseMapper.selectById(operatorId);
    }

    @Override
    public IPage<Operator> queryOperator(String operatorName, Long roleId, Long operatorId, Integer currentPage) {
        QueryWrapper<Operator> query = new QueryWrapper<>();
        Page<Operator> page = new Page<>(currentPage, 10);
        if(StringUtils.isNotEmpty(operatorName)) {
            query.eq("operator_name", operatorName);
        }
        if(null != roleId) {
            query.eq("role_id", roleId);
        }
        if(null != operatorId) {
            query.eq("operator_id", operatorId);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public void saveOperator(String operatorName, Long roleId, Long operatorId, String mobile, Integer deleted) {
        Operator operator = new Operator();
        operator.setOperatorId(operatorId).setOperatorName(operatorName).setMobile(mobile)
                .setPwd(MD5Utils.encode("123456")).setDeleted(deleted).setRoleId(roleId);
        baseMapper.insert(operator);
    }

    @Override
    public void updateOperator(String operatorName, Long roleId, Long operatorId, String mobile, Integer deleted) {
        Operator operator = new Operator();
        operator.setOperatorId(operatorId).setOperatorName(operatorName).setMobile(mobile)
                .setDeleted(deleted).setRoleId(roleId);
        baseMapper.updateById(operator);
    }

    @Override
    public void deleteOperator(Long operatorId) {
        Operator operator = this.getOperator(operatorId);
        if(null == operator) {
            throw new BizException(BizErrorCode.OPERATOR_NOT_EXIST);
        }
        baseMapper.deleteById(operatorId);
    }
}
