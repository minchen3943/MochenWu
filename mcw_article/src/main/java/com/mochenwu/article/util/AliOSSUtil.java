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
 * @author : 瞑尘
 **/
public class AliOSSUtil {

    private static final String ENDPOINT = "https://oss-cn-chengdu.aliyuncs.com";
    private static final String BUCKET_NAME = "mcw-article";
    private static final String REGION = "cn-chengdu";

    /**
     * 实现上传图片到OSS
     */
    public static AliOSSModel upload(MultipartFile file) throws IOException, ClientException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();
        Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
        AliOSSModel ossModel = new AliOSSModel(null, null);
        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            ossModel.setFileName(UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(".")));
        }
        //上传文件到 OSS
        // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();
        ossClient.putObject(BUCKET_NAME, ossModel.getFileName(), inputStream);

        //文件访问路径
        ossModel.setUrl(ENDPOINT.split("//")[0] + "//" + BUCKET_NAME + "." + ENDPOINT.split("//")[1] + "/" + ossModel.getFileName());
        logger.info("文件上传阿里云OSS成功 {url:{}}", ossModel.getUrl());

        // 关闭ossClient
        ossClient.shutdown();
        // 返回上传到oss的路径
        return ossModel;
    }

    public static void delete(McwArticle article) throws ClientException {
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。
        String objectName = article.getArticleName();

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();

        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(BUCKET_NAME, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            ossClient.shutdown();
        }
    }
}

