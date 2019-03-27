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
public class Album extends SuperEntity<Album> {

    private static final long serialVersionUID = 1L;

    /**
     * 相册ID
     */
    @TableId(value = "album_id", type = IdType.ID_WORKER)
    private Long albumId;
    /**
     * 相册名称
     */
    @TableField("album_name")
    private String albumName;
    /**
     * 相册描述
     */
    @TableField("album_desc")
    private String albumDesc;
    /**
     * 相册封面图片路径
     */
    @TableField("cover_img")
    private String coverImg;
    /**
     * 相册所属组织ID，只到班级
     */
    @TableField("origin_id")
    private Long originId;
    /**
     * 父相册，为0表示学院相册（学院相册）
     */
    @TableField("parent_id")
    private Long parentId;


    @Override
    protected Serializable pkVal() {
        return this.albumId;
    }

}
