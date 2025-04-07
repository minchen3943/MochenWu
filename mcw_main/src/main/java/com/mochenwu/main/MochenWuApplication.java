package com.mochenwu.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用程序的主入口类。
 *
 * <p>该类负责启动 Spring Boot 应用程序，并配置组件扫描路径和 MyBatis 的 Mapper 接口扫描路径。</p>
 *
 * @author 瞑尘
 * @date 2025/04/06
 */
@SpringBootApplication
@ComponentScan("com.mochenwu.comment")
@ComponentScan("com.mochenwu.article")
@ComponentScan("com.mochenwu.data")
@ComponentScan("com.mochenwu.main.config")
@MapperScan({"com.mochenwu.comment.mapper","com.mochenwu.article.mapper", "com.mochenwu.data.mapper"})
public class MochenWuApplication {
    /**
     * 应用程序的主入口方法。
     *
     * <p>该方法启动 Spring Boot 应用程序。</p>
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(MochenWuApplication.class, args);
    }
}
