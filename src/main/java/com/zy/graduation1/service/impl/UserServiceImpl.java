package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.exception.BizErrorCode;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.entity.User;
import com.zy.graduation1.mapper.UserMapper;
import com.zy.graduation1.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.graduation1.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.management.Query;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2018-12-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Boolean validatAccount(String account) {
        User user = baseMapper.selectById(account);
        return user == null ? false : true;
    }

    @Override
    public User getUserInfo(Long studentId) {
        return baseMapper.selectById(studentId);
    }

    @Override
    public Boolean resetPwd(Long studentId, String oldPwd, String newPwd) {
        User user = baseMapper.selectById(studentId);
        if(null == user) {
            throw new BizException(BizErrorCode.ACCOUNT_NOT_EXIST);
        }
        String md5Pwd = MD5Utils.encode(oldPwd);
        if(!(user.getPwd().equals(md5Pwd))) {
            throw new BizException(BizErrorCode.PASSWORD_ERROR);
        }
        user.setPwd(md5Pwd);
        return baseMapper.updateById(user) == 1;
    }

    @Override
    public Boolean saveOrUpdateUser(User user) {
        return this.saveOrUpdateUser(user);
    }

    @Override
    public Boolean deleteUser(Long studentId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("student_id", studentId);
        return baseMapper.deleteById(studentId) == 1;
    }
}
