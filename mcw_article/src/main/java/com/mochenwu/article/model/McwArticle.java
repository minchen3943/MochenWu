package com.mochenwu.article.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 * 类描述：文章实体类
 * <p>
 * 该实体类用于描述文章的基本信息，包括文章标题、文章地址、发布时间、访问量、状态以及文件名称
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
@Entity
@Table(name = "mcw_article")
public class McwArticle {

    /**
     * 文章ID，自增长主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    /**
     * 文章标题，最大长度50
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "article_title", nullable = false, length = 50)
    private String articleTitle;

    /**
     * 文章URL，最大长度50
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "article_url", nullable = false, length = 50)
    private String articleUrl;

    /**
     * 文章发布时间，默认为当前时间
     */
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "article_date")
    private LocalDateTime articleDate;

    /**
     * 文章访问量，不能为空
     */
    @NotNull
    @Column(name = "article_visitor_count", nullable = false)
    private Integer articleVisitorCount;

    /**
     * 文章状态，不能为空（状态值由业务逻辑定义）
     */
    @NotNull
    @Column(name = "article_status", nullable = false)
    private Integer articleStatus;

    /**
     * 文章名称，最大长度200，用于存储文件名称
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "article_name", nullable = false, length = 200)
    private String articleName;

    /**
     * 获取文章名称
     *
     * @return 文章名称
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * 设置文章名称
     *
     * @param articleName 文章名称
     */
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    /**
     * 获取文章ID
     *
     * @return 文章ID
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * 设置文章ID
     *
     * @param articleId 文章ID
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取文章标题
     *
     * @return 文章标题
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 设置文章标题
     *
     * @param articleTitle 文章标题
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取文章URL
     *
     * @return 文章URL
     */
    public String getArticleUrl() {
        return articleUrl;
    }

    /**
     * 设置文章URL
     *
     * @param articleUrl 文章URL
     */
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    /**
     * 获取文章发布时间
     *
     * @return 文章发布时间
     */
    public LocalDateTime getArticleDate() {
        return articleDate;
    }

    /**
     * 设置文章发布时间
     *
     * @param articleDate 文章发布时间
     */
    public void setArticleDate(LocalDateTime articleDate) {
        this.articleDate = articleDate;
    }

    /**
     * 获取文章访问量
     *
     * @return 文章访问量
     */
    public Integer getArticleVisitorCount() {
        return articleVisitorCount;
    }

    /**
     * 设置文章访问量
     *
     * @param articleVisitorCount 文章访问量
     */
    public void setArticleVisitorCount(Integer articleVisitorCount) {
        this.articleVisitorCount = articleVisitorCount;
    }

    /**
     * 获取文章状态
     *
     * @return 文章状态
     */
    public Integer getArticleStatus() {
        return articleStatus;
    }

    /**
     * 设置文章状态
     *
     * @param articleStatus 文章状态
     */
    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    /**
     * 重写toString方法，输出文章对象的主要属性信息
     *
     * @return 包含文章主要属性信息的字符串
     */
    @Override
    public String toString() {
        return ("{"
                + "articleId=" + articleId
                + ", articleTitle=" + articleTitle
                + ", articleUrl=" + articleUrl
                + ", articleDate=" + articleDate
                + ", articleVisitorCount=" + articleVisitorCount
                + ", articleStatus=" + articleStatus
                + "}");
    }
}
