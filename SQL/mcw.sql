CREATE DATABASE
    IF
    NOT EXISTS `mochen_wu` CHARACTER
    SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `mochen_wu`;
DROP TABLE
    IF
        EXISTS `mcw_admin`;
CREATE TABLE `mcw_admin`
(
    admin_id            INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    admin_user_name     VARCHAR(20) NOT NULL COMMENT '用户名',
    admin_user_password VARCHAR(50) NOT NULL COMMENT '用户密码'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '管理员用户簿';
DROP TABLE
    IF
        EXISTS `mcw_articles`;
CREATE TABLE `mcw_article`
(
    article_id            INT AUTO_INCREMENT PRIMARY KEY COMMENT '博客id',
    article_title         VARCHAR(80) NOT NULL COMMENT '博客标题',
    article_url          VARCHAR(200) NOT NULL COMMENT '文章路径',
    article_date          DATETIME COMMENT '文章上传时间',
    article_visitor_count INT         NOT NULL COMMENT '文章阅读人数',
    article_status        INT         NOT NULL COMMENT '文章状态(0不显示;1显示;2状态异常)'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '博客表';
DROP TABLE
    IF
        EXISTS `mcw_comment`;
CREATE TABLE `mcw_comment`
(
    comment_id         INT AUTO_INCREMENT PRIMARY KEY COMMENT '评论id',
    comment_user_name  VARCHAR(40) NOT NULL COMMENT '评论用户名',
    comment_user_email VARCHAR(40) COMMENT '评论用户邮箱',
    comment_user_ip    VARCHAR(20) NOT NULL COMMENT '评论用户ip',
    comment_content    TEXT        NOT NULL COMMENT '评论内容',
    comment_date       DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    comment_status     INT         NOT NULL COMMENT '评论状态(0不显示;1显示;2状态异常)'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '评论表';
DROP TABLE
    IF
        EXISTS `mcw_friend_link`;
CREATE TABLE `mcw_friend_link`
(
    link_id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '友链id',
    link_title   VARCHAR(40) NOT NULL COMMENT '友链标题',
    link_url     VARCHAR(40) NOT NULL COMMENT '友链链接',
    link_img_url VARCHAR(200) NOT NULL COMMENT '友链图片路径',
    link_brief   VARCHAR(60) COMMENT '友链简介',
    link_status  INT         NOT NULL COMMENT '友链状态(0不显示;1显示;2状态异常)'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '友链表';

DROP TABLE IF EXISTS `mcw_data`;
CREATE TABLE `mcw_data`
(
    visitor_count INT NOT NULL COMMENT '网站访问人数'
)