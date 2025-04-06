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
 * 评论服务实现类
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, McwComment> implements CommentService {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * 评论数据访问接口
     */
    private final CommentMapper commentMapper;

    /**
     * 构造函数注入Mapper
     *
     * @param commentMapper 评论数据访问对象
     */
    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 获取所有可见评论
     *
     * @return 可见评论列表
     */
    @Override
    public List<McwComment> getAllComment() {
        return commentMapper.getAllVisibleComment();
    }

    /**
     * 新增评论
     *
     * @param mcwComment 评论实体对象
     */
    @Override
    public void addComment(McwComment mcwComment) {
        // 设置默认时间和状态
        mcwComment.setCommentDate(LocalDateTime.now());
        mcwComment.setCommentStatus(0);
        commentMapper.addComment(mcwComment);
        logger.info("Comment added {commentId:{}}", mcwComment.getCommentId());
    }

    /**
     * 分页查询评论
     *
     * @param page 当前页码
     * @param pageSize 每页数量
     * @return 分页结果列表
     * @throws RuntimeException 分页查询异常
     */
    @Override
    public List<McwComment> getCommentByPage(int page, int pageSize) {
        try (Page<McwComment> ignored = PageHelper.startPage(page, pageSize)) {
            List<McwComment> comments = commentMapper.getAllVisibleComment();
            // 处理空结果集
            if (comments.isEmpty()) {
                logger.error("分页查询评论数据为空 {page:{}  pageSize:{}}", page, pageSize);
            } else {
                logger.info("分页查询成功 {page:{}  pageSize:{}}", page, pageSize);
            }
            return comments;
        } catch (Exception e) {
            logger.error("分页查询评论时发生错误：{}", e.getMessage());
            throw new RuntimeException("分页查询评论时发生错误：", e);
        }
    }

    /**
     * 根据ID获取评论详情
     *
     * @param commentId 评论ID
     * @return 评论实体对象
     */
    @Override
    public McwComment getCommentById(int commentId) {
        McwComment comment = commentMapper.getCommentById(commentId);
        if (comment == null) {
            logger.error("query is empty, commentId:{}", commentId);
        } else {
            logger.info("Query Comment {commentId:{}}", commentId);
        }
        return comment;
    }

    /**
     * 更新评论信息
     *
     * @param mcwComment 评论实体对象
     * @return 更新后的评论对象
     */
    @Override
    public McwComment updateComment(McwComment mcwComment) {
        int id = mcwComment.getCommentId();
        int status = commentMapper.updateCommentById(mcwComment);
        // 处理更新结果
        if (status == 0) {
            logger.error("update comment failed, commentId:{}", id);
        } else {
            logger.info("Comment update {commentId:{}, Updates:{}}", id, status);
        }
        return getCommentById(id);
    }

    /**
     * 删除指定评论
     *
     * @param commentId 评论ID
     */
    @Override
    public void delCommentById(int commentId) {
        commentMapper.delCommentById(commentId);
        logger.info("Comment del {commentId:{}}", commentId);
    }

    /**
     * 获取评论总数
     *
     * @return 评论总数
     */
    @Override
    public int getAllCommentNumber() {
        int output = commentMapper.getAllCommentNumber();
        logger.info("查询评论总数成功 {}", output);
        return output;
    }
}