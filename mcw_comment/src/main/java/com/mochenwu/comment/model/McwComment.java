package com.mochenwu.comment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 * @author 瞑尘
 * @description 评论实体类，映射数据库中的 mcw_comment 表。
 * 该类用于表示用户评论的数据模型，包含评论的各个字段及其验证规则。
 * @Date 2025-02-22 13:26:30
 */
@Entity  // 表示这是一个JPA实体类，映射到数据库中的一张表
@Table(name = "mcw_comment")  // 指定映射的表名为 mcw_comment
public class McwComment {

    /**
     * 评论的唯一标识符，主键
     * 映射到数据库中的 comment_id 字段，自动生成主键值
     */
    @Id
    @TableId(type = IdType.AUTO)  // 使用 MyBatis Plus 的自动生成主键策略
    @Column(name = "comment_id", nullable = false)  // 映射到表中的 comment_id 列
    private Integer commentId;

    /**
     * 评论用户名，不能为空，长度为20个字符
     * 映射到数据库中的 comment_user_name 字段，要求非空
     */
    @Column(name = "comment_user_name", nullable = false, length = 20)
    @NotBlank(message = "用户名不能为空")
    private String commentUserName;

    /**
     * 评论用户的电子邮件，可为空，长度为20个字符
     * 映射到数据库中的 comment_user_email 字段
     */
    @Column(name = "comment_user_email", length = 40)
    private String commentUserEmail;

    /**
     * 评论用户的IP地址，不能为空，长度为20个字符
     * 映射到数据库中的 comment_user_ip 字段，要求非空
     */
    @Column(name = "comment_user_ip", nullable = false, length = 40)
    @NotBlank(message = "IP地址不能为空")
    private String commentUserIp;

    /**
     * 评论内容，不能为空，使用Lob注解表示可能包含大量数据
     * 映射到数据库中的 comment_content 字段，要求非空
     */
    @Lob
    @Column(name = "comment_content", nullable = false)
    @NotBlank(message = "评论内容不能为空")
    private String commentContent;

    /**
     * 评论时间戳，默认为当前时间
     * 映射到数据库中的 comment_date 字段，格式为 ISO 8601
     */
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "comment_date")  // 映射到表中的 comment_date 列
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")  // 时间格式化为 ISO 8601 格式
    private LocalDateTime commentDate;

    /**
     * 评论状态，不能为空，用于表示评论是否被审核或批准
     * 映射到数据库中的 comment_status 字段，要求非空
     */
    @Column(name = "comment_status", nullable = false)
    @NotBlank(message = "评论状态不能为空")
    private Integer commentStatus;

    /**
     * 获取评论ID
     *
     * @return 评论ID
     */
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取评论用户名
     *
     * @return 评论用户名
     */
    public String getCommentUserName() {
        return commentUserName;
    }

    /**
     * 设置评论用户名
     *
     * @param commentUserName 评论用户名
     */
    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    /**
     * 获取评论用户电子邮件
     *
     * @return 评论用户电子邮件
     */
    public String getCommentUserEmail() {
        return commentUserEmail;
    }

    /**
     * 设置评论用户电子邮件
     *
     * @param commentUserEmail 评论用户电子邮件
     */
    public void setCommentUserEmail(String commentUserEmail) {
        this.commentUserEmail = commentUserEmail;
    }

    /**
     * 获取评论用户IP地址
     *
     * @return 评论用户IP地址
     */
    public String getCommentUserIp() {
        return commentUserIp;
    }

    /**
     * 设置评论用户IP地址
     *
     * @param commentUserIp 评论用户IP地址
     */
    public void setCommentUserIp(String commentUserIp) {
        this.commentUserIp = commentUserIp;
    }

    /**
     * 获取评论内容
     *
     * @return 评论内容
     */
    public String getCommentContent() {
        return commentContent;
    }

    /**
     * 设置评论内容
     *
     * @param commentContent 评论内容
     */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    /**
     * 获取评论时间
     *
     * @return 评论时间
     */
    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    /**
     * 设置评论时间
     *
     * @param commentDate 评论时间
     */
    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * 获取评论状态
     *
     * @return 评论状态
     */
    public Integer getCommentStatus() {
        return commentStatus;
    }

    /**
     * 设置评论状态
     *
     * @param commentStatus 评论状态
     */
    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * 重写 toString 方法，返回评论对象的字符串表示
     *
     * @return 评论对象的字符串表示
     */
    @Override
    public String toString() {
        return "{" +
                ", commentUserName='" + commentUserName + '\'' +
                ", commentUserEmail='" + commentUserEmail + '\'' +
                ", commentUserIp='" + commentUserIp + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentDate=" + commentDate +
                ", commentStatus=" + commentStatus +
                '}';
    }


}