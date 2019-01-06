package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("recruit_unit")
public class RecruitUnit extends SuperEntity<RecruitUnit> {

    private static final long serialVersionUID = 1L;

    /**
     * 单位ID
     */
    @TableId(value = "unit_id", type = IdType.ID_WORKER)
    private Long unitId;
    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 所属行业
     */
    @TableField("industry")
    private String industry;
    /**
     * 单位性质
     */
    @TableField("property")
    private Integer property;
    /**
     * 单位规模
     */
    @TableField("scale")
    private Integer scale;
    /**
     * 单位网站
     */
    @TableField("unit_web")
    private String unitWeb;
    /**
     * 联系人姓名
     */
    @TableField("contractor")
    private String contractor;
    /**
     * 联系人电话
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 状态1-审核 2-正常
     */
    @TableField("status")
    private Integer status;
    /**
     * 是否删除0-是 1-否
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.unitId;
    }

}
