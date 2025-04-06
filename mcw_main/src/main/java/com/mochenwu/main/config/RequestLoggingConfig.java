package com.mochenwu.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * 全局请求日志记录配置类。
 *
 * <p>该配置类用于设置全局的请求日志记录策略，记录客户端信息、查询字符串、请求体和请求头等内容。</p>
 *
 * @author 瞑尘
 * @date 2025/04/06
 */
@Configuration
public class RequestLoggingConfig {

    /**
     * 配置请求日志记录过滤器。
     *
     * <p>该方法配置了 {@link CommonsRequestLoggingFilter}，用于记录HTTP请求的详细信息，包括：
     * 客户端信息、查询字符串、请求体和请求头等。</p>
     *
     * @return 配置好的 {@link CommonsRequestLoggingFilter} 实例
     */
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
