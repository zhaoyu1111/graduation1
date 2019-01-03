package com.zy.graduation1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zy.project.mapper")
public class Graduation1Application {

    public static void main(String[] args) {
        SpringApplication.run(Graduation1Application.class, args);
    }
}

