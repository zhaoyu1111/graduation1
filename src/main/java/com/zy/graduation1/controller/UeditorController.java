package com.zy.graduation1.controller;

import com.alibaba.fastjson.JSON;
import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.ueditor.PublicMsg;
import com.zy.graduation1.ueditor.Ueditor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

@Controller
@RequestMapping("/web")
public class UeditorController {

    @Anonymous
    @RequestMapping("/ueditor")
    public String saveArticle(String action,HttpServletRequest request,HttpServletResponse response) {
        return "redirect:/static/ueditor/jsp/config.json";
    }

    @Anonymous
    @PostMapping(value = "/imgUpload")
    @ResponseBody
    public Ueditor imgUpdate(HttpServletRequest request) throws FileNotFoundException {

        Ueditor ueditor = new Ueditor();
        ueditor.setState("error");

        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest)request;
        MultipartFile file1 = multipart.getFile("upfile");
        if (file1.isEmpty()) {
            return ueditor;
        }

        // 获取文件名
        String fileName = file1.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 这里我使用随机字符串来重新命名图片
        fileName = Calendar.getInstance().getTimeInMillis() + suffixName;

        //String realPath = request.getContextPath();
        //String realPath = "E:\\upload\\images\\";
        String realPath = "images\\";
        String path1 = "E:\\upload\\";

        //String path = realPath+"\\"+fileName;
        String path = path1 + realPath + fileName;
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file1.transferTo(dest);
            ueditor.setState("SUCCESS");
            ueditor.setUrl(fileName);
            ueditor.setTitle(fileName);
            ueditor.setOriginal(fileName);
            return ueditor;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ueditor.setState("error");
        return ueditor;

    }
}
