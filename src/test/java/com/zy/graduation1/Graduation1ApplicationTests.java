package com.zy.graduation1;

import com.zy.graduation1.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Graduation1ApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;



    @Test
    public void contextLoads() {
    }

    @Test
    public void redis(){
        System.out.println(MD5Utils.encode("123456"));
    }

}

