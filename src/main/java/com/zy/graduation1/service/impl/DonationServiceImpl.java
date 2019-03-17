package com.zy.graduation1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.DonationDto;
import com.zy.graduation1.entity.Donation;
import com.zy.graduation1.entity.Operator;
import com.zy.graduation1.exception.BizException;
import com.zy.graduation1.exception.OriginErrorCode;
import com.zy.graduation1.mapper.DonationMapper;
import com.zy.graduation1.service.DonationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.graduation1.service.OperatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2019-03-15
 */
@Service
public class DonationServiceImpl extends ServiceImpl<DonationMapper, Donation> implements DonationService {

    @Autowired
    private OperatorService operatorService;

    @Override
    public MyPage<DonationDto> queryDonation(String donationName, Integer donationObject, Integer status, Integer currentPage) {
        IPage<Donation> donationPage = queryDoantion(donationName, donationObject, status, currentPage);
        List<Donation> donations = donationPage.getRecords();
        if(CollectionUtils.isEmpty(donations)) {
            return new MyPage<>();
        }

        List<Long> handleUids = donations.stream().map(Donation::getHandleUid).distinct().collect(Collectors.toList());
        List<Operator> operators = operatorService.getOperatorInfo(handleUids);
        Map<Long, String> handleMap = Maps.newHashMap();
        operators.forEach(operator -> {
            handleMap.put(operator.getOperatorId(), operator.getOperatorName());
        });

        List<DonationDto> donationDtos = Lists.newArrayList();
        for (Donation donation : donations) {
            DonationDto donationDto = new DonationDto();
            BeanUtils.copyProperties(donation, donationDto);
            donationDto.setHandleName(handleMap.get(donation.getHandleUid()));
            donationDtos.add(donationDto);
        }

        return new MyPage<>(donationPage.getTotal(), donationDtos);
    }

    private IPage<Donation> queryDoantion(String donationName, Integer donationObject, Integer status, Integer currentPage) {
        QueryWrapper<Donation> query = new QueryWrapper<>();
        Page<Donation> donationPage = new Page<>(currentPage, 10);
        if(StringUtils.isNotEmpty(donationName)) {
            query.like("donation_name", donationName);
        }
        if(null != donationObject) {
            query.eq("donation_object", donationObject);
        }
        if(null != status) {
            query.eq("status", status);
        }
        query.eq("deleted", 1);
        return baseMapper.selectPage(donationPage, query);
    }

    @Override
    public void saveOrUpdateDonation(Long donationId, Long donationUid,
                                     String donationName, String donationMobile,
                                     String goodsName, Integer amount,
                                     Integer donationObject, String objectName,
                                     String unitName) {
        Donation donation = new Donation();
        donation.setDonationUid(donationUid).setDonationName(donationName).setDonationMobile(donationMobile)
                .setGoodsName(goodsName).setAmount(amount).setDonationObject(donationObject).setObjectName(objectName)
                .setUnitName(unitName);
        if(null != donationId) {
            donation.setDonationId(donationId);
        }
        this.insertOrUpdate(donation);
    }

    @Override
    public void deleteDonation(Long donationId) {
        Donation donation = baseMapper.selectById(donationId);
        if(null == donation) {
            return ;
        }

        donation.setDeleted(0);
        baseMapper.updateById(donation);
    }

    @Override
    public void completeDonation(Long denationId, Long handleUid) {
        Donation donation = baseMapper.selectById(denationId);
        if(null == donation) {
            throw new BizException(OriginErrorCode.DONATION_PROJECT_NOT_EXIST);
        }
        donation.setHandleUid(handleUid);
        if(donation.getStatus().equals(2)) {
            donation.setStatus(3);
        }else {
            donation.setStatus(2);
        }
        baseMapper.updateById(donation);
    }
}
