package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.graduation1.entity.DictionaryData;
import com.zy.graduation1.mapper.DictionaryDataMapper;
import com.zy.graduation1.service.DictionaryDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-04-25
 */
@Service
public class DictionaryDataServiceImpl extends ServiceImpl<DictionaryDataMapper, DictionaryData> implements DictionaryDataService {

    @Override
    public List<DictionaryData> unitData(String dictValue) {
        QueryWrapper<DictionaryData> query = new QueryWrapper<>();
        query.eq("dict_value", dictValue);
        return baseMapper.selectList(query);
    }
}
