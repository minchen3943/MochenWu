package com.mochenwu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.comment.model.McwComment;

import java.util.List;

/**
 * @author 瞑尘
 * @interface CommentService
 * @description 提供对评论表【mcw_comment】的数据库操作服务接口。
 * 该接口继承自 MyBatis-Plus 的 IService 接口，自动提供基本的 CRUD 操作。
 * 用于定义评论相关的业务逻辑操作接口。
 * @Date 2025-02-21 22:05:04
 */
public interface CommentService extends IService<McwComment> {

    /**
     * 获取所有可见的评论列表。
     *
     * @return 返回所有可见的评论集合。
     */
    List<McwComment> getAllComment();

    /**
     * 添加新的评论。
     *
     * @param mcwComment 要添加的评论对象。
     *                   该评论对象将被保存到数据库中。
     */
    void addComment(McwComment mcwComment);

    /**
     * 分页查询可见的评论列表。
     *
     * @param page 当前页码，分页查询时使用。
     *             根据页码返回对应的评论列表。
     * @return 返回分页后的评论列表。
     */
    List<McwComment> getCommentByPage(int page);

    /**
     * 更新评论信息。
     *
     * @param mcwComment 包含修改内容的评论对象。
     *                   根据评论ID更新对应的评论记录。
     * @return 返回更新后的评论对象。
     */
    McwComment updateComment(McwComment mcwComment);

    /**
     * 根据评论ID获取指定评论。
     *
     * @param commentId 评论ID，唯一标识每一条评论。
     * @return 返回指定评论的详细信息。
     */
    McwComment getCommentById(int commentId);

    void delCommentById(int commentId);
}
