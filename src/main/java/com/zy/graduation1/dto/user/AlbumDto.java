package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class AlbumDto {

    /**
     * 相册ID
     */
    private Long albumId;
    /**
     * 相册名称
     */
    private String albumName;
    /**
     * 相册描述
     */
    private String albumDesc;
    /**
     * 相册封面图片路径
     */
    private String coverImg;
    /**
     * 相册所属组织ID，只到班级
     */
    private Long originId;
    /**
     * 父相册，为0表示学院相册（学院相册）
     */
    private Long parentId;
}
