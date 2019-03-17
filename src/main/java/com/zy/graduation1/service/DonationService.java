package com.zy.graduation1.service;

import com.zy.graduation1.common.MyPage;
import com.zy.graduation1.dto.user.DonationDto;
import com.zy.graduation1.entity.Donation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2019-03-15
 */
public interface DonationService extends IService<Donation> {

    /**
     * 分页查询捐赠信息
     * @param donationName
     * @param donationObject
     * @param status
     * @param currentPage
     * @return
     */
    MyPage<DonationDto> queryDonation(String donationName,
                                      Integer donationObject, Integer status, Integer currentPage);


    /**
     * 新增或修改捐赠信息
     * @param donationId
     * @param donationUid
     * @param donationName
     * @param donationMobile
     * @param goodsName
     * @param amount
     * @param donationObject
     * @param objectName
     * @param unitName
     */
    void saveOrUpdateDonation(Long donationId, Long donationUid,
                              String donationName, String donationMobile,
                              String goodsName, Integer amount,
                              Integer donationObject, String objectName,
                              String unitName);

    /**
     * 删除捐赠信息
     * @param donationId
     */
    void deleteDonation(Long donationId);

    /**
     * 捐赠处理完成
     */
    void completeDonation(Long denationId, Long handleUid);
}
