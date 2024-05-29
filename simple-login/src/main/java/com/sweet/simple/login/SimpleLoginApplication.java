package com.sweet.simple.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@MapperScan("com.sweet.simple.login.mapper")
@SpringBootApplication
public class SimpleLoginApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleLoginApplication.class).headless(false).run(args);
        ViewStart.run();
    }
}
