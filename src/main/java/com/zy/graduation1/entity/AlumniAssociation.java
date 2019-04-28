package com.zy.graduation1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("alumni_association")
public class AlumniAssociation extends SuperEntity<AlumniAssociation> {

    private static final long serialVersionUID = 1L;

    /**
     * 校友会ID
     */
    @TableId(value = "associa_id", type = IdType.AUTO)
    private Long associaId;
    /**
     * 校友会名称
     */
    @TableField("associa_name")
    private String associaName;
    /**
     * 校友会地址(仅需填写市不用填省)
     */
    @TableField("address")
    private String address;
    /**
     * 会长ID
     */
    @TableField("president_id")
    private Long presidentId;

    /**
     * 状态
     */
    private Integer deleted;

    /**
     * 描述
     * @return
     */
    @TableField("descrip")
    private String descrip;

    @Override
    protected Serializable pkVal() {
        return this.associaId;
    }

}
