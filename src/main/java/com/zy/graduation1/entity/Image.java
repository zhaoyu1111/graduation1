package com.zy.graduation1.entity;

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
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Image extends SuperEntity<Image> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片Id
     */
    @TableId(value = "image_id", type = IdType.ID_WORKER)
    private Long imageId;
    /**
     * 图片名称
     */
    @TableField("image_name")
    private String imageName;
    /**
     * 图片路径
     */
    @TableField("image_url")
    private String imageUrl;
    /**
     * 图片描述
     */
    @TableField("image_desc")
    private String imageDesc;
    /**
     * 所属相册
     */
    @TableField("album_id")
    private Long albumId;


    @Override
    protected Serializable pkVal() {
        return this.imageId;
    }

}
