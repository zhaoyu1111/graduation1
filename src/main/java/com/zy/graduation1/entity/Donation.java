package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.zy.graduation1.entity.SuperEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2019-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Donation extends SuperEntity<Donation> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "donation_id", type = IdType.AUTO)
    private Long donationId;
    /**
     * 捐赠人UID
     */
    @TableField("donation_uid")
    private Long donationUid;

    /**
     * 捐赠人姓名
     */
    @TableField("donation_name")
    private String donationName;

    /**
     * 捐赠人联系方式
     */
    @TableField("donation_mobile")
    private String donationMobile;

    /**
     * 捐赠物品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 数量
     */
    @TableField("amount")
    private Integer amount;
    /**
     * 捐赠对象
     */
    @TableField("donation_object")
    private Integer donationObject;

    /**
     * 被捐赠人姓名
     */
    @TableField("object_name")
    private String objectName;
    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 处理人UID
     */
    @TableField("handle_uid")
    private Long handleUid;

    @TableField("status")
    private Integer status;

    @TableField("deleted")
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.donationId;
    }

}
