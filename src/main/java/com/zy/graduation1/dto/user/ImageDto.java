package com.zy.graduation1.dto.user;

import lombok.Data;

@Data
public class ImageDto {

    /**
     * 图片Id
     */
    private Long imageId;
    /**
     * 图片名称
     */
    private String imageName;
    /**
     * 图片路径
     */
    private String imageUrl;
    /**
     * 图片描述
     */
    private String imageDesc;
    /**
     * 所属相册
     */
    private Long albumId;
}
