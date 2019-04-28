package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class AssociationDto {

    /**
     * 校友会ID
     */
    private Long associaId;
    /**
     * 校友会名称
     */
    private String associaName;
    /**
     * 校友会地址(仅需填写市不用填省)
     */
    private String address;
    /**
     * 会长ID
     */
    private Long presidentId;

    /**
     * 会长姓名
     */
    private String presidentName;

    /**
     * 当前状态 0-正常 1-冻结
     */
    private Integer deleted;

    private String descrip;

    /**
     * 创建时间
     */
    private Long ctime;
}
