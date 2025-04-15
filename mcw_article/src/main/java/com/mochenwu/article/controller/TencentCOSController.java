package com.mochenwu.article.controller;

import com.mochenwu.article.util.TencentCOSUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sts.v20180813.StsClient;
import com.tencentcloudapi.sts.v20180813.models.GetFederationTokenRequest;
import com.tencentcloudapi.sts.v20180813.models.GetFederationTokenResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 腾讯COS控制器
 * </p>
 * <p>
 * 本控制器主要用于获取腾讯COS临时访问token，以实现对COS服务的访问控制，
 * 调用腾讯云STS服务来获取指定权限和时长的临时令牌（token），供前端使用。
 * </p>
 * <p>
 * 主要功能包括：
 * <ul>
 *     <li>构造STS客户端（StsClient）对象；</li>
 *     <li>构造并设置获取临时token的请求参数；</li>
 *     <li>调用STS接口获取临时token；</li>
 *     <li>捕获腾讯云SDK调用异常并记录日志，返回相应调用结果。</li>
 * </ul>
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-13
 */
@RestController
@RequestMapping("/api/cos/*")
public class TencentCOSController {

    public final TencentCOSUtil cosUtil;

    @Autowired
    public TencentCOSController(TencentCOSUtil cosUtil) {
        this.cosUtil = cosUtil;
    }

    /**
     * 获取临时访问Token
     *
     * @return {@link GetFederationTokenResponse} 对象包含临时访问token信息；调用失败时返回null
     * @author 瞑尘
     * @date 2025-04-13
     */
    @GetMapping("/token/get")
    public GetFederationTokenResponse getToken() {
        return cosUtil.getToken();
    }


}
