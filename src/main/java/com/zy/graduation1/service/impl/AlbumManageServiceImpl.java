package com.zy.graduation1.service.impl;

import com.zy.graduation1.service.AlbumManageService;
import com.zy.graduation1.service.AlbumService;
import com.zy.graduation1.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

public class AlbumManageServiceImpl implements AlbumManageService {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ImageService imageService;


}
