package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class RecruitUnitDto {

    /**
     * 单位ID
     */
    private Long unitId;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 单位性质
     */
    private Integer property;

    /**
     * 单位规模
     */
    private Integer scale;

    /**
     * 单位网站
     */
    private String unitWeb;
    /**
     * 联系人姓名
     */
    private String contractor;
    /**
     * 联系人电话
     */
    private String mobile;

    private String direct;

    private String companyPhone;
    /**
     * 状态1-正在审核 2-审核通过 3-拒绝通过
     */
    private Integer status;
    /**
     * 是否删除0-是 1-否
     */
    private Integer deleted;
}
