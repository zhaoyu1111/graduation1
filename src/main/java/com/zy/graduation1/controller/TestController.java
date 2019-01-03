package com.zy.graduation1.controller;


import com.zy.graduation1.common.Anonymous;
import com.zy.graduation1.entity.Test;
import com.zy.graduation1.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2018-12-30
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Anonymous
    @RequestMapping("")
    public List<Test> select() {
        return testService.select();
    }

    @Anonymous
    @RequestMapping("/string")
    public String test() {
        return "赵宇";
    }
}

