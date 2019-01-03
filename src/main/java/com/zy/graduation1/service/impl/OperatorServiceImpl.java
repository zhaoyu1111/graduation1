package com.zy.graduation1.service.impl;

import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.mapper.OperatorMapper;
import com.zy.graduation1.service.OperatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.graduation1.utils.MD5Utils;
import org.springframework.stereotype.Service;

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

}
