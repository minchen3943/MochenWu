package com.mochenwu.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author 32035
 */
@Configuration
public class RequestLoggingConfig {
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        // 设置是否包含客户端信息（如 IP 地址、会话 ID）
        loggingFilter.setIncludeClientInfo(true);
        // 设置是否包含查询字符串（即 URL 中的参数部分）
        loggingFilter.setIncludeQueryString(true);
        // 设置是否包含请求体（对于 POST 请求等）
        loggingFilter.setIncludePayload(true);
        // 设置是否包含请求头信息
        loggingFilter.setIncludeHeaders(true);
        // 设置请求体的最大长度。如果请求体长度超过此值，将被截断。
        loggingFilter.setMaxPayloadLength(10000);
        return loggingFilter;
    }
}
