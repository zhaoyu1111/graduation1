package com.zy.graduation1.service;

import com.zy.graduation1.entity.DictionaryData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-04-25
 */
public interface DictionaryDataService extends IService<DictionaryData> {

    List<DictionaryData> unitData(String dictValue);
}
