package com.zy.graduation1.mapper;

import com.zy.graduation1.entity.UserJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2019-04-25
 */
@Mapper
public interface UserJobMapper extends BaseMapper<UserJob> {

    void updateStatus(@Param("unitId") Long unitId);
}
