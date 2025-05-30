package com.mochenwu.article.util;

import com.mochenwu.article.controller.TencentCOSController;
import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.model.TencentCOSModel;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.qcloud.cos.utils.Jackson;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Policy;
import com.tencent.cloud.Response;
import com.tencent.cloud.Statement;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sts.v20180813.StsClient;
import com.tencentcloudapi.sts.v20180813.models.GetFederationTokenRequest;
import com.tencentcloudapi.sts.v20180813.models.GetFederationTokenResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯云 COS 工具类，用于文件上传功能封装
 * <p>
 * 本类实现临时密钥获取、COSClient 与 TransferManager 的创建以及文件上传操作。
 * <br>注意：部分配置属性通过 Spring 注入，请确认配置正确。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-12
 */
@Component
public class TencentCOSUtil {

    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(TencentCOSUtil.class);

    /**
     * 腾讯云 API 的 SecretId，配置文件中注入
     */
    @Value("${tencent.cos.secret.id}")
    private String secretId;

    /**
     * 腾讯云 API 的 SecretKey，配置文件中注入
     */
    @Value("${tencent.cos.secret.key}")
    private String secretKey;

    /**
     * 腾讯云 COS 所在区域，配置文件中注入
     */
    @Value("${tencent.cos.region}")
    private String region;

    /**
     * COS bucket名称，配置文件中注入
     */
    @Value("${tencent.cos.bucket-name}")
    private String bucketName;

    /**
     * 上传文件路径的前缀
     */
    private static final String PARTITION = "article";

    /**
     * 获取临时会话密钥
     * <p>
     * 使用腾讯云 STS 服务获取临时密钥，并返回 BasicSessionCredentials 对象。
     * </p>
     *
     * @return BasicSessionCredentials 实例，若获取失败返回 null
     */

    public @Nullable COSCredentials testGetCredential() {
        TreeMap<String, Object> config = new TreeMap<>();
        try {
            // 将 SecretId 与 SecretKey 存入配置对象
            config.put("secretId", secretId);
            config.put("secretKey", secretKey);

            // 设置临时密钥有效时长（单位：秒）
            config.put("durationSeconds", 300);
            // 指定存储桶和区域（请根据实际情况修改）
            config.put("bucket", bucketName);
            config.put("region", region);

            // 构建权限策略
            Policy policy = new Policy();
            Statement statement = new Statement();
            // 设置权限为允许
            statement.setEffect("allow");
            // 添加允许的操作权限，含简单上传、分片上传和删除操作
            statement.addActions(new String[]{
                    "name/cos:PutObject", "name/cos:PostObject",
                    "name/cos:InitiateMultipartUpload", "name/cos:ListMultipartUploads",
                    "name/cos:ListParts", "name/cos:UploadPart", "name/cos:CompleteMultipartUpload",
                    "name/cos:DeleteBucket", "name/cos:DeleteObject"});
            // 添加允许的操作路径
            statement.addResource("qcs::cos:" + region + ":uid/1354220332:" + bucketName + "/" + PARTITION + "/*");
            policy.addStatement(statement);
            // 配置策略转换成 JSON 字符串
            config.put("policy", Jackson.toJsonPrettyString(policy));

            // 调用腾讯云 STS 客户端获取临时密钥
            Response response = CosStsClient.getCredential(config);
            return new BasicSessionCredentials(response.credentials.tmpSecretId,
                    response.credentials.tmpSecretKey,
                    response.credentials.sessionToken);
        } catch (Exception e) {
            logger.error("获取临时密钥失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 创建 COSClient 实例，用于访问 COS 服务
     * <p>
     * 使用 testGetCredential() 获取临时密钥，并根据配置文件创建 COSClient，
     * 配置包含区域、协议、读取超时及连接超时等参数。
     * </p>
     *
     * @return COSClient 实例，供文件上传等操作使用
     */
    @Contract(" -> new")
    private @NotNull COSClient createCosClient() {
        // 创建客户端配置对象
        ClientConfig clientConfig = new ClientConfig();

        // 设置存储桶所在的区域
        clientConfig.setRegion(new Region(region));

        // 使用 HTTPS 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 设置 Socket 读取超时（15秒）
        clientConfig.setSocketTimeout(15 * 1000);
        // 设置建立连接超时（15秒）
        clientConfig.setConnectionTimeout(15 * 1000);

        // 使用临时密钥创建并返回 COSClient 实例
        return new COSClient(testGetCredential(), clientConfig);
    }

    /**
     * 创建 TransferManager 实例，用于调用高级上传接口（支持断点续传、分块上传等）
     *
     * @return TransferManager 对象，支持大文件及其他高级上传操作
     */
    private @NotNull TransferManager createTransferManager() {
        // 创建 COSClient 实例
        COSClient cosClient = createCosClient();

        // 创建自定义线程池，线程数建议根据实际网络环境调整
        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 初始化 TransferManager，并返回
        return new TransferManager(cosClient, threadPool);
    }

    /**
     * 关闭 TransferManager 实例，并释放内部的 COSClient 资源
     *
     * @param transferManager 待关闭的 TransferManager 实例
     */
    private void shutdownTransferManager(@NotNull TransferManager transferManager) {
        // 参数为 true 表示同时关闭 TransferManager 内部的 COSClient 实例
        transferManager.shutdownNow(true);
    }

    /**
     * 上传文件到腾讯云 COS
     * <p>
     * 该方法将上传 MultipartFile 文件至 COS 存储，
     * 生成随机文件名，并构造存储对象 Key，上传成功后返回包含文件元数据和访问 URL 的模型类 TencentCOSModel。
     * </p>
     *
     * @param file 上传的 MultipartFile 文件对象
     * @return 上传成功后包含文件信息的 TencentCOSModel 实例
     * @throws IOException 若文件读取过程中出现异常，则抛出该异常
     */
    public TencentCOSModel upload(@NotNull MultipartFile file) throws IOException {
        // 初始化保存文件元数据的模型
        TencentCOSModel cosModel = new TencentCOSModel(null, null, null, null);

        // 建议使用 getOriginalFilename() 获取上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();

        // 获取上传文件的输入流
        InputStream inputStream = file.getInputStream();

        // 生成随机文件名，避免文件名冲突，同时保留文件扩展名
        if (originalFilename != null) {
            cosModel.setFileName(UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(".")));
        }

        // 定义 COS 存储中的对象 Key，带有固定前缀
        String key = PARTITION + "/" + cosModel.getFileName();

        // 创建 TransferManager 实例
        TransferManager transferManager = createTransferManager();

        // 设置文件元数据，包括文件长度
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStream.available());

        // 构造 PutObjectRequest 请求对象，指定存储桶、对象 Key、文件输入流和元数据
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        // 设置存储类型为标准存储
        putObjectRequest.setStorageClass(StorageClass.Standard);

        try {
            // 发起上传请求，等待上传完成
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            cosModel.setBucket(bucketName);
            cosModel.setRegion(region);
            cosModel.setKey(uploadResult.getKey());
        } catch (CosClientException | InterruptedException e) {
            // 记录上传过程中发生的异常信息
            logger.error("上传文件发生异常: {}", e.getMessage());
        } finally {
            // 关闭 TransferManager，释放资源
            shutdownTransferManager(transferManager);
        }
        return cosModel;
    }


    public boolean delete(@NotNull McwArticle article) {
        COSClient cosClient = createCosClient();
        try {
            cosClient.deleteObject(article.getArticleBucket(), article.getArticleKey());
            logger.info("COS删除文件成功: articleId: {}", article.getArticleId());
            return true;
        } catch (CosClientException e) {
            logger.error("COS删除文件失败: articleId: {}, message: {}", article.getArticleId(), e.getMessage());
        } finally {
            cosClient.shutdown();
        }
        return false;
    }

    /**
    * 从腾讯云 COS 获取临时秘钥
    * <p>
    * 直接从腾讯云COS获取临时秘钥,生效时长180s
    * </p>
     * @return 一个完整的token {@link GetFederationTokenResponse} 为空则有错误，懒得写错误响应了
    */
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
    private @NotNull GetFederationTokenRequest getGetFederationTokenRequest() {
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
    private @NotNull StsClient getStsClient() {
        // 创建认证信息
        Credential cred = new Credential(secretId, secretKey);

        // 实例化HttpProfile，可对请求超时时间及访问endpoint做配置
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sts.tencentcloudapi.com");

        // 创建客户端配置，绑定HttpProfile
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 返回STS客户端实例，指定区域为 "ap-guangzhou"
        return new StsClient(cred, region, clientProfile);
    }
}