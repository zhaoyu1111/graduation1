package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class DonationDto {

    /**
     * 主键ID
     */
    private Long donationId;
    /**
     * 捐赠人UID
     */
    private Long donationUid;

    /**
     * 捐赠人姓名
     */
    private String donationName;

    /**
     * 捐赠人联系方式
     */
    private String donationMobile;

    /**
     * 捐赠物品名称
     */
    private String goodsName;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 捐赠对象
     */
    private Integer donationObject;

    /**
     * 被捐赠人姓名
     */
    private String objectName;
    /**
     * 单位名称
     */
    private Long unitName;
    /**
     * 处理人UID
     */
    private Long handleUid;

    /**
     * 处理人名称
     */
    private String handleName;

    /**
     * 当前状态
     */
    private Integer status;
}
