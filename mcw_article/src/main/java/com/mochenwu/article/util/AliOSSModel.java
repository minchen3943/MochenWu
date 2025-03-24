package com.mochenwu.article.util;

/**
 * @author 32035
 */
public class AliOSSModel {
    String url;
    String fileName;

    public AliOSSModel() {
    }

    public AliOSSModel(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
