package com.mochenwu.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 全局跨域配置类。
 *
 * <p>该配置类用于设置全局的跨域资源共享（CORS）策略，允许指定的前端域名访问后端资源。</p>
 *
 * @author 瞑尘
 * @date 2025/04/06
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * 配置跨域过滤器。
     *
     * <p>该方法配置了允许访问的前端域名、是否允许携带凭证、允许的请求方法、允许的请求头部信息等。</p>
     *
     * @return 配置好的 {@link CorsFilter} 实例
     */
    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加 CORS 配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许访问的前端域名列表
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://127.0.0.1:3000",
                "http://192.168.5.2:3000",
                "http://192.168.5.3:3000"
        ));
        // 是否允许携带凭证（如 Cookie）
        config.setAllowCredentials(true);
        // 允许的请求方法，* 表示支持所有
        config.addAllowedMethod("*");
        // 允许的请求头部信息，* 表示支持所有
        config.addAllowedHeader("*");
        // 暴露的头部信息，* 表示支持所有
        config.addExposedHeader("*");

        // 2. 添加映射路径，对所有请求应用上述配置
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);

        // 3. 返回新的 CorsFilter 实例
        return new CorsFilter(corsConfigurationSource);
    }
}
