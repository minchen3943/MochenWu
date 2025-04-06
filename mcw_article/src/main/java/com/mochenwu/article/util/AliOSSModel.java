package com.mochenwu.article.util;

/**
 * 类描述：阿里云OSS文件模型
 * <p>
 * 该类用于封装文件在阿里云OSS上传后返回的访问URL及文件名称信息
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
public class AliOSSModel {

    /**
     * 文件访问URL
     */
    String url;

    /**
     * 文件名称
     */
    String fileName;

    /**
     * 默认构造方法
     *
     * @author 瞑尘
     * @date 2025-04-06
     */
    public AliOSSModel() {
    }

    /**
     * 带参构造方法
     *
     * @param url 文件访问URL
     * @param fileName 文件名称
     * @author 瞑尘
     * @date 2025-04-06
     */
    public AliOSSModel(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    /**
     * 获取文件访问URL
     *
     * @return 文件访问URL
     * @author 瞑尘
     * @date 2025-04-06
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文件访问URL
     *
     * @param url 文件访问URL
     * @author 瞑尘
     * @date 2025-04-06
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取文件名称
     *
     * @return 文件名称
     * @author 瞑尘
     * @date 2025-04-06
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     * @author 瞑尘
     * @date 2025-04-06
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
