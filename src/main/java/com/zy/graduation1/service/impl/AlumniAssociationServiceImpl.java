package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.graduation1.entity.AlumniAssociation;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.AlumniAssociationMapper;
import com.zy.graduation1.service.AlumniAssociationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-03-18
 */
@Service
public class AlumniAssociationServiceImpl extends ServiceImpl<AlumniAssociationMapper, AlumniAssociation> implements AlumniAssociationService {

    @Override
    public IPage<AlumniAssociation> queryAlumniAssociation(String associaName, String address, Integer currentPage) {
        QueryWrapper<AlumniAssociation> query = new QueryWrapper<>();
        Page<AlumniAssociation> page = new Page<>(currentPage, 10 );
        if(StringUtils.isNotEmpty(associaName)) {
            query.like("associa_name", associaName);
        }
        if(StringUtils.isNotEmpty(address)) {
            query.eq("address", address);
        }
        return baseMapper.selectPage(page, query);
    }

    @Override
    public void saveOrUpdateAssocia(Long associaId, String associaName, String address, Long presidentId
            , String descrip, Integer deleted) {
        AlumniAssociation getAssociation = this.getAssociation(address);
        if(null != getAssociation && null == associaId) {
            throw new BizException(OriginErrorCode.ASSOCIATION_EXIST);
        }
        AlumniAssociation association = new AlumniAssociation();
        association.setAssociaName(associaName).setAddress(address).setPresidentId(presidentId)
            .setDescrip(descrip).setDeleted(deleted);
        if(null != associaId) {
            association.setAssociaId(associaId);
        }
        this.insertOrUpdate(association);
    }

    @Override
    public void deleteAssocia(Long associaId) {
        AlumniAssociation association = baseMapper.selectById(associaId);
        if(null == association) {
            throw new BizException(OriginErrorCode.ASSOCIATION_NOT_EXIST);
        }
        baseMapper.deleteById(associaId);
    }

    public AlumniAssociation getAssociation(String address) {
        QueryWrapper<AlumniAssociation> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(address)) {
            query.eq("address", address);
        }
        List<AlumniAssociation> associations = baseMapper.selectList(query);
        return CollectionUtils.isEmpty(associations) ? null : associations.get(0);
    }
}
