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
 * @author 32035
 */
@Entity
@Table(name = "mcw_article")
public class McwArticle {
    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Size(max = 50)
    @NotNull
    @Column(name = "article_title", nullable = false, length = 50)
    private String articleTitle;

    @Size(max = 50)
    @NotNull
    @Column(name = "article_url", nullable = false, length = 50)
    private String articleUrl;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "article_date")
    private LocalDateTime articleDate;

    @NotNull
    @Column(name = "article_visitor_count", nullable = false)
    private Integer articleVisitorCount;

    @NotNull
    @Column(name = "article_status", nullable = false)
    private Integer articleStatus;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public LocalDateTime getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(LocalDateTime articleDate) {
        this.articleDate = articleDate;
    }

    public Integer getArticleVisitorCount() {
        return articleVisitorCount;
    }

    public void setArticleVisitorCount(Integer articleVisitorCount) {
        this.articleVisitorCount = articleVisitorCount;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

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