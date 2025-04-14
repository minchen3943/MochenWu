package com.mochenwu.article.controller;

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

    /**
     * 腾讯云 API 的 SecretId，配置文件中注入
     */
    @Value("${tencent.cos.secret.id}")
    private static String secretId;

    /**
     * 腾讯云 API 的 SecretKey，配置文件中注入
     */
    @Value("${tencent.cos.secret.key}")
    private static String secretKey;

    /**
     * 获取临时访问Token
     * <p>
     * 方法描述：
     * 1. 通过 {@link #getStsClient()} 获取STS客户端实例；<br>
     * 2. 调用 {@link #getGetFederationTokenRequest()} 构造获取token的请求参数；<br>
     * 3. 调用STS接口，获取包含临时token信息的响应对象；<br>
     * 4. 记录操作日志，若调用失败则捕获异常并记录错误日志；<br>
     * 5. 异常情况下返回null，调用方需判断返回结果。<br>
     * </p>
     *
     * @return {@link GetFederationTokenResponse} 对象包含临时访问token信息；调用失败时返回null
     * @author 瞑尘
     * @date 2025-04-13
     */
    @GetMapping("/token/get")
    public GetFederationTokenResponse getToken() {
        try {
            // 获取STS客户端实例
            StsClient client = getStsClient();
            // 构造获取临时token的请求对象
            GetFederationTokenRequest req = getGetFederationTokenRequest();
            // 调用STS接口获取临时token信息
            GetFederationTokenResponse response = client.GetFederationToken(req);
            // 输出日志，表明token获取成功
            logger.info("成功获取临时token");
            return response;
        } catch (TencentCloudSDKException e) {
            // 捕获异常并记录详细的错误日志
            logger.error("获取临时token失败，TencentCloudSDKException：", e);
        }
        // 出现异常时返回null，调用方需做非null判断
        return null;
    }

    /**
     * 构造获取临时Token请求对象
     * <p>
     * 方法描述：
     * 1. 实例化 {@link GetFederationTokenRequest} 对象；<br>
     * 2. 设置权限角色名称（例如"readOnly"）；<br>
     * 3. 设置授权策略，策略内容为JSON格式，指定允许调用COS的GetObject操作，且资源范围限定；<br>
     * 4. 设置token生效时长（单位秒，此处设为180秒）；<br>
     * 5. 返回构造好的请求对象。<br>
     * </p>
     *
     * @return 一个已配置好的 {@link GetFederationTokenRequest} 请求对象，不为空
     * @author 瞑尘
     * @date 2025-04-13
     */
    private static @NotNull GetFederationTokenRequest getGetFederationTokenRequest() {
        // 创建获取临时token的请求对象
        GetFederationTokenRequest req = new GetFederationTokenRequest();

        // 设置请求名称，标识当前token对应的角色名称，此处为 "readOnly"
        req.setName("readOnly");

        /* 设置权限策略
         * 策略为JSON字符串，定义允许调用COS的GetObject操作，
         * 限定资源为指定Bucket下article目录内所有文件
         */
        req.setPolicy("{\"version\":\"2.0\",\"statement\":[{\"effect\":\"allow\",\"action\":[\"name/cos:GetObject\"],\"resource\":\"qcs::cos:ap-guangzhou:uid/1354220332:mochenwu-1354220332/article/*\"}]}");

        // 设置token的有效时间，单位为秒，此处为180秒
        req.setDurationSeconds(180L);

        return req;
    }


    /**
     * 日志记录对象，用于输出接口调用日志及异常信息
     */
    final Logger logger = LoggerFactory.getLogger(TencentCOSController.class);

    /**
     * 获取腾讯云STS客户端实例
     * <p>
     * 方法描述：
     * 1. 构造认证信息（AKID、密钥）；<br>
     * 2. 创建HttpProfile，配置访问的endpoint；<br>
     * 3. 构造ClientProfile，并关联HttpProfile；<br>
     * 4. 根据认证信息和配置返回一个StsClient实例，区域为"ap-guangzhou"。<br>
     * </p>
     *
     * @return 一个非空的 {@link StsClient} 实例，用于调用STS接口
     * @author 瞑尘
     * @date 2025-04-13
     */
    private static @NotNull StsClient getStsClient() {
        // 创建认证信息
        Credential cred = new Credential(secretId, secretKey);

        // 实例化HttpProfile，可对请求超时时间及访问endpoint做配置
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sts.tencentcloudapi.com");

        // 创建客户端配置，绑定HttpProfile
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 返回STS客户端实例，指定区域为 "ap-guangzhou"
        return new StsClient(cred, "ap-guangzhou", clientProfile);
    }
}
