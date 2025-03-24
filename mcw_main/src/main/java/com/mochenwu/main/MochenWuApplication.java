package com.mochenwu.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 瞑尘
 */
@SpringBootApplication
@ComponentScan("com.mochenwu.comment")
@ComponentScan("com.mochenwu.article")
@ComponentScan("com.mochenwu.data")
@MapperScan({"com.mochenwu.comment.mapper","com.mochenwu.article.mapper", "com.mochenwu.data.mapper"})
public class MochenWuApplication {
    public static void main(String[] args) {
        SpringApplication.run(MochenWuApplication.class, args);
    }

}