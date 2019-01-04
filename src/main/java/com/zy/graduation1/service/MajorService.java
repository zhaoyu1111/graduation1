package com.zy.graduation1.service;

import com.zy.graduation1.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-01-04
 */
public interface MajorService extends IService<Major> {

    /**
     * 通过majorIds批量查询专业信息
     * @param majorIds
     * @return
     */
    List<Major> listMajor(List<Long> majorIds);
}
