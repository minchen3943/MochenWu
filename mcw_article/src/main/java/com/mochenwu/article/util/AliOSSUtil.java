package com.mochenwu.article.util;


import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.mochenwu.article.service.impl.ArticleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author : 瞑尘
 **/
public class AliOSSUtil {

    private static String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
    private static String bucketName = "mcw-article";
    private static String region = "cn-chengdu";

    /**
     * 实现上传图片到OSS
     */
    public static String upload(MultipartFile file) throws IOException, ClientException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();
        Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = null;
        if (originalFilename != null) {
            fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        //上传文件到 OSS
        // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        logger.info("文件上传阿里云OSS成功 {url:{}}", url);

        // 关闭ossClient
        ossClient.shutdown();
        // 返回上传到oss的路径
        return url;
    }
}

