package com.mochenwu.article.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.service.impl.ArticleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 类描述：阿里云OSS工具类
 * <p>
 * 该工具类用于实现文件上传和删除操作至阿里云OSS，
 * 支持防止文件覆盖及生成文件访问路径。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
public class AliOSSUtil {

    /**
     * 阿里云OSS服务的访问域名（Endpoint）
     */
    private static final String ENDPOINT = "https://oss-cn-chengdu.aliyuncs.com";

    /**
     * 阿里云OSS的存储空间名称（Bucket Name）
     */
    private static final String BUCKET_NAME = "mcw-article";

    /**
     * 阿里云OSS的区域标识
     */
    private static final String REGION = "cn-chengdu";

    /**
     * 实现上传文件到阿里云OSS
     *
     * @param file 上传的文件，支持MultipartFile格式
     * @return 返回包含文件名称及文件访问URL的AliOSSModel对象
     * @throws IOException     文件输入流读取异常
     * @throws ClientException 客户端异常，例如获取凭证失败等
     * @author 瞑尘
     * @date 2025-04-06
     */
    public static AliOSSModel upload(MultipartFile file) throws IOException, ClientException {
        // 获取上传文件的输入流
        InputStream inputStream = file.getInputStream();
        Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
        AliOSSModel ossModel = new AliOSSModel(null, null);

        // 生成随机文件名，防止文件覆盖
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            ossModel.setFileName(UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(".")));
        }

        // 从环境变量中获取访问凭证，确保已配置OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET环境变量
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 配置OSS客户端，显式声明使用V4签名算法
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        // 创建OSSClient实例
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();

        // 上传文件到指定Bucket中
        ossClient.putObject(BUCKET_NAME, ossModel.getFileName(), inputStream);

        // 生成文件访问URL，格式为：https://{bucket}.{endpoint}/{fileName}
        ossModel.setUrl(ENDPOINT.split("//")[0] + "//" + BUCKET_NAME + "." + ENDPOINT.split("//")[1] + "/" + ossModel.getFileName());
        logger.info("文件上传阿里云OSS成功 {url:{}}", ossModel.getUrl());

        // 关闭OSSClient
        ossClient.shutdown();
        return ossModel;
    }

    /**
     * 实现删除阿里云OSS中的文件
     *
     * @param article 包含待删除文件信息的文章对象
     * @throws ClientException 客户端异常，例如获取凭证失败等
     * @author 瞑尘
     * @date 2025-04-06
     */
    public static void delete(McwArticle article) throws ClientException {
        // 从环境变量中获取访问凭证，确保已配置OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET环境变量
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 获取待删除文件的完整路径（文件名，不包含Bucket名称）
        String objectName = article.getArticleName();

        // 配置OSS客户端，显式声明使用V4签名算法
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        // 创建OSSClient实例
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();

        try {
            // 删除指定Bucket中的文件
            ossClient.deleteObject(BUCKET_NAME, objectName);
        } catch (OSSException oe) {
            // 捕获OSSException异常并输出错误详细信息
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }
    }
}
