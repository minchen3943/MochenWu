package com.mochenwu.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mochenwu.comment.mapper.CommentMapper;
import com.mochenwu.comment.model.McwComment;
import com.mochenwu.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 瞑尘
 * @className CommentServiceImpl
 * @description 实现 CommentService 接口，提供对评论数据表【mcw_comment】的操作服务。
 * 该类继承自 MyBatis-Plus 的 ServiceImpl 类，实现了对评论数据的增删改查操作。
 * 包含了获取所有评论、分页查询评论、添加评论、更新评论等功能。
 * @Date 2025-02-21 22:05:04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, McwComment> implements CommentService {

    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    /**
     * 注入 CommentMapper 数据库操作接口
     */
    private final CommentMapper commentMapper;

    /**
     * 构造方法，注入 CommentMapper
     *
     * @param commentMapper 注入的 CommentMapper 实例，负责与数据库进行交互
     */
    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 获取所有可见的评论
     *
     * @return 返回所有可见的评论列表
     */
    @Override
    public List<McwComment> getAllComment() {
        // 调用 CommentMapper 获取所有可见的评论数据
        return commentMapper.getAllVisibleComment();
    }

    /**
     * 添加新的评论
     *
     * @param mcwComment 要添加的评论对象，包含评论的内容及其他信息
     */
    @Override
    public void addComment(McwComment mcwComment) {
        // 设置当前时间为评论时间
        mcwComment.setCommentDate(LocalDateTime.now());
        mcwComment.setCommentStatus(0);
        // 调用 CommentMapper 插入评论数据
        commentMapper.addComment(mcwComment);
        logger.info("Comment added {commentId:{}}", mcwComment.getCommentId());
    }

    /**
     * 分页查询可见的评论
     *
     * @param page 当前页码，用于分页查询评论数据
     * @return 返回分页后的评论列表
     * @throws RuntimeException 如果分页查询过程中发生异常，则抛出运行时异常
     */
    @Override
    public List<McwComment> getCommentByPage(int page, int pageSize) {
        // 使用 PageHelper 实现分页功能
        // 通过 try-with-resources 确保 Page 对象被自动关闭
        try (Page<McwComment> ignored = PageHelper.startPage(page, pageSize)) {
            // 调用 CommentMapper 获取分页后的评论列表
            logger.info("分页查询成功 {page:{}}", page);
            return commentMapper.getAllVisibleComment();
        } catch (Exception e) {
            // 捕获异常并抛出运行时异常
            logger.error("分页查询评论时发生错误：{}", e.getMessage());
            throw new RuntimeException("分页查询评论时发生错误：", e);
        }
    }

    /**
     * 根据评论ID获取指定评论
     *
     * @param commentId 评论ID，唯一标识每一条评论
     * @return 返回指定评论的详细信息
     */
    @Override
    public McwComment getCommentById(int commentId) {
        McwComment comment = commentMapper.getCommentById(commentId);
        if (comment == null) {
            logger.error("query is empty, commentId:{}", commentId);
            return null;
        } else {
            logger.info("Query Comment {commentId:{}}", commentId);
            return comment;
        }
    }

    /**
     * 更新评论信息
     *
     * @param mcwComment 包含修改内容的评论对象
     *                   根据评论ID更新对应的评论记录
     * @return 返回更新后的评论对象
     */
    @Override
    public McwComment updateComment(McwComment mcwComment) {
        int id = mcwComment.getCommentId();
        int status = commentMapper.updateCommentById(mcwComment);
        // 执行更新评论操作
        if (status == 0) {
            logger.error("update comment failed, commentId:{}", id);
        } else {
            logger.info("Comment update {commentId:{}, Updates:{}}", id, status);
        }
        // 返回更新后的评论对象
        return getCommentById(id);
    }

    @Override
    public void delCommentById(int commentId) {
        commentMapper.delCommentById(commentId);
        logger.info("Comment del {commentId:{}}", commentId);
    }
}
