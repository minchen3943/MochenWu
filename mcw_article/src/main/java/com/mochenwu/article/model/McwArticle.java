package com.mochenwu.article.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章实体类（McwArticle）
 * </p>
 * <p>
 * 该类对应数据库中的表 {@code mcw_article}（位于 schema {@code mochen_wu} 下），用于存储文章的基本信息，
 * 包括文章标题、对应的COS存储信息、发布日期、访问计数以及状态等。<br>
 * 注意：各字段的长度和非空约束由注解 {@code @Size} 和 {@code @NotNull} 明确规定，符合数据库和业务规则要求。
 * </p>
 *
 * <p>
 * 主要字段说明：
 * <ul>
 *     <li>{@code articleId}：文章主键，自动递增；</li>
 *     <li>{@code articleTitle}：文章标题，长度限制80；</li>
 *     <li>{@code articleBucket}：存储文章文件的COS Bucket名称，长度限制200；</li>
 *     <li>{@code articleRegion}：存储所在的区域，长度限制200；</li>
 *     <li>{@code articleKey}：COS中文件的唯一标识Key，长度限制200；</li>
 *     <li>{@code articleName}：文件名，长度限制200；</li>
 *     <li>{@code articleDate}：文章发布日期，使用 {@link LocalDateTime} 类型；</li>
 *     <li>{@code articleVisitorCount}：文章访问次数，不允许为空；</li>
 *     <li>{@code articleStatus}：文章状态码，不允许为空（具体状态码由业务定义）；</li>
 * </ul>
 * </p>
 *
 * @author 32035
 */
@Entity
@Table(name = "mcw_article", schema = "mochen_wu")
public class McwArticle {

    /**
     * 文章ID，数据库主键，自动递增
     */
    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    /**
     * 文章标题，不能为空，最大长度80
     */
    @Size(max = 80)
    @NotNull
    @Column(name = "article_title", nullable = false, length = 80)
    private String articleTitle;

    /**
     * 存储文章文件的COS Bucket名称，不能为空，最大长度200
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "article_bucket", nullable = false, length = 200)
    private String articleBucket;

    /**
     * 存储所在区域（Region），不能为空，最大长度200
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "article_region", nullable = false, length = 200)
    private String articleRegion;

    /**
     * COS中文件的唯一Key，不能为空，最大长度200
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "article_key", nullable = false, length = 200)
    private String articleKey;

    /**
     * 文件名称（文章名称），不能为空，最大长度200
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "article_name", nullable = false, length = 200)
    private String articleName;

    /**
     * 文章发布日期，采用LocalDateTime类型进行存储
     */
    @Column(name = "article_date")
    private LocalDateTime articleDate;

    /**
     * 文章访问次数，不能为空
     */
    @NotNull
    @Column(name = "article_visitor_count", nullable = false)
    private Integer articleVisitorCount;

    /**
     * 文章状态码，不能为空，状态由业务定义，例如：0表示未发布，1表示已发布等
     */
    @NotNull
    @Column(name = "article_status", nullable = false)
    private Integer articleStatus;

    // ===================== Getter/Setter =====================

    /**
     * 获取文章ID
     *
     * @return 文章ID
     */
    public Integer getArticleId() {
        return articleId;
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
     * @param articleTitle 文章标题，不能为空且最长80字符
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 获取COS存储的Bucket名称
     *
     * @return Bucket名称
     */
    public String getArticleBucket() {
        return articleBucket;
    }

    /**
     * 设置COS存储的Bucket名称
     *
     * @param articleBucket Bucket名称，不能为空且最长200字符
     */
    public void setArticleBucket(String articleBucket) {
        this.articleBucket = articleBucket;
    }

    /**
     * 获取文章所在区域（Region）
     *
     * @return 文章区域
     */
    public String getArticleRegion() {
        return articleRegion;
    }

    /**
     * 设置文章所在区域（Region）
     *
     * @param articleRegion 文章区域，不能为空且最长200字符
     */
    public void setArticleRegion(String articleRegion) {
        this.articleRegion = articleRegion;
    }

    /**
     * 获取COS中文件的唯一标识Key
     *
     * @return 文件Key
     */
    public String getArticleKey() {
        return articleKey;
    }

    /**
     * 设置COS中文件的唯一标识Key
     *
     * @param articleKey 文件Key，不能为空且最长200字符
     */
    public void setArticleKey(String articleKey) {
        this.articleKey = articleKey;
    }

    /**
     * 获取文章对应的文件名称
     *
     * @return 文件名称
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * 设置文章对应的文件名称
     *
     * @param articleName 文件名称，不能为空且最长200字符
     */
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    /**
     * 获取文章发布日期
     *
     * @return 文章发布日期，可能为null
     */
    public LocalDateTime getArticleDate() {
        return articleDate;
    }

    /**
     * 设置文章发布日期
     *
     * @param articleDate 文章发布日期
     */
    public void setArticleDate(LocalDateTime articleDate) {
        this.articleDate = articleDate;
    }

    /**
     * 获取文章的访问次数
     *
     * @return 访问次数
     */
    public Integer getArticleVisitorCount() {
        return articleVisitorCount;
    }

    /**
     * 设置文章访问次数
     *
     * @param articleVisitorCount 访问次数，不能为空
     */
    public void setArticleVisitorCount(Integer articleVisitorCount) {
        this.articleVisitorCount = articleVisitorCount;
    }

    /**
     * 获取文章状态
     *
     * @return 文章状态码
     */
    public Integer getArticleStatus() {
        return articleStatus;
    }

    /**
     * 设置文章状态
     *
     * @param articleStatus 文章状态码，不能为空
     */
    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }
}
