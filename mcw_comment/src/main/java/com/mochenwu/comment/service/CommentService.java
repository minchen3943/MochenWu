package com.mochenwu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.comment.model.McwComment;

import java.util.List;

/**
 * 评论服务接口
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
public interface CommentService extends IService<McwComment> {

    /**
     * 获取所有可见评论
     *
     * @return 可见评论列表
     */
    List<McwComment> getAllComment();

    /**
     * 新增评论
     *
     * @param mcwComment 评论实体对象
     */
    void addComment(McwComment mcwComment);

    /**
     * 分页查询评论
     *
     * @param page 当前页码
     * @param pageSize 每页数量
     * @return 分页结果列表
     */
    List<McwComment> getCommentByPage(int page, int pageSize);

    /**
     * 更新评论信息
     *
     * @param mcwComment 评论实体对象
     * @return 更新后的评论对象
     */
    McwComment updateComment(McwComment mcwComment);

    /**
     * 根据ID获取评论详情
     *
     * @param commentId 评论ID
     * @return 评论实体对象
     */
    McwComment getCommentById(int commentId);

    /**
     * 删除指定评论
     *
     * @param commentId 评论ID
     */
    void delCommentById(int commentId);

    /**
     * 获取评论总数
     *
     * @return 评论总数
     */
    int getAllCommentNumber();
}