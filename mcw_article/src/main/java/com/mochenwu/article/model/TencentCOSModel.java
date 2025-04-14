package com.mochenwu.article.model;

/**
 * <p>
 * 腾讯COS模型实体类
 * </p>
 * <p>
 * 用于封装与腾讯云COS存储相关的信息，包括Bucket名称、区域、文件Key和文件名等，
 * 该对象通常作为文件上传、下载及删除操作的返回结果携带相关COS信息。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-12
 */
public class TencentCOSModel {

    /**
     * 存储文件的Bucket名称
     */
    private String bucket;

    /**
     * 存储区域（Region），用于标识COS所在的数据中心
     */
    private String region;

    /**
     * COS中文件的唯一标识Key
     */
    private String key;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 默认构造函数
     * <p>
     * 用于创建一个空的腾讯COS模型对象，后续通过Setter方法赋值
     * </p>
     */
    public TencentCOSModel() {
    }

    /**
     * 带参数构造函数
     * <p>
     * 初始化腾讯COS模型对象，设置Bucket、Region、Key和文件名四个属性
     * </p>
     *
     * @param bucket   存储文件的Bucket名称
     * @param region   存储区域（Region），如“ap-guangzhou”
     * @param key      COS中文件的唯一标识Key
     * @param fileName 文件名称
     */
    public TencentCOSModel(String bucket, String region, String key, String fileName) {
        this.bucket = bucket;
        this.region = region;
        this.key = key;
        this.fileName = fileName;
    }

    /**
     * 获取Bucket名称
     *
     * @return 返回存储文件的Bucket名称
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * 设置Bucket名称
     *
     * @param bucket 存储文件的Bucket名称，应符合COS存储规范
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * 获取存储区域
     *
     * @return 返回COS存储区域（Region），例如“ap-guangzhou”
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置存储区域
     *
     * @param region 存储区域（Region），必须正确填写以确保文件操作针对正确的数据中心
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取文件的唯一Key
     *
     * @return 返回COS中文件的唯一标识Key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置文件的唯一Key
     *
     * @param key COS中文件的唯一标识Key，应确保唯一性
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取文件名称
     *
     * @return 返回文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称，应包含文件后缀信息
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
