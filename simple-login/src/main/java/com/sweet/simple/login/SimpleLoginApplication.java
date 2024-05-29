package com.sweet.simple.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sweet.simple.login.mapper")
@SpringBootApplication
public class SimpleLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleLoginApplication.class, args);
    }
}
