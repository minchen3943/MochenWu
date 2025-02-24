package com.mochenwu.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.comment.model.McwComment;

import java.util.List;

/**
 * @author 瞑尘
 * @description 针对表【mcw_comment(评论表)】的数据库操作 Mapper 接口
 * 提供了与评论相关的数据库操作，包括获取所有可见评论和添加评论。
 * 该接口继承自 MyBatis-Plus 提供的 BaseMapper，自动实现了基础的 CRUD 操作。
 * @Date 2025-02-21 22:05:04
 */
public interface CommentMapper extends BaseMapper<McwComment> {

    /**
     * 获取所有可见的评论
     * <p>
     * 该方法用于从数据库中查询所有状态为可见的评论，适用于需要展示的评论。
     * </p>
     *
     * @return 返回一个包含所有可见评论的列表
     */
    List<McwComment> getAllVisibleComment();

    /**
     * 添加新评论
     * <p>
     * 该方法用于将新评论数据插入到数据库中，成功插入返回影响的行数。
     * </p>
     *
     * @param mcwComment 要添加的评论对象，包含评论的内容、作者信息等
     * @return 返回影响的行数，通常为 1 表示插入成功，0 表示插入失败
     */
    int addComment(McwComment mcwComment);

    /**
     * 根据评论ID更新评论内容
     * <p>
     * 该方法用于根据评论ID更新指定评论的内容信息。
     * </p>
     *
     * @param mcwComment 要更新的评论对象，包含评论ID和更新的内容
     * @return 返回影响的行数，通常为 1 表示更新成功，0 表示更新失败
     */
    int updateCommentById(McwComment mcwComment);

    /**
     * 根据评论ID获取指定评论
     * <p>
     * 该方法用于根据评论ID从数据库中查询指定的评论详情。
     * </p>
     *
     * @param commentId 评论的唯一标识符
     * @return 返回指定评论的对象，如果评论不存在则返回 null
     */
    McwComment getCommentById(int commentId);

    void delCommentById(int commentId);

}
