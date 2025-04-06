package com.mochenwu.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.comment.model.McwComment;

import java.util.List;

/**
 * 接口描述：评论数据访问层接口
 * <p>
 * 定义与评论相关的数据库操作方法，包括获取所有可见评论、添加评论、更新评论、根据ID查询评论、删除评论以及统计评论数量。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
public interface CommentMapper extends BaseMapper<McwComment> {

    /**
     * 功能描述：获取所有可见的评论
     * <p>
     * 从数据库中查询所有状态为可见的评论记录。
     * </p>
     *
     * @return 返回包含所有可见评论的列表
     */
    List<McwComment> getAllVisibleComment();

    /**
     * 功能描述：添加新评论
     * <p>
     * 将新的评论数据插入到数据库中。
     * </p>
     *
     * @param mcwComment 评论对象，包含评论内容、作者信息等
     * @return 返回影响的行数，1表示添加成功，0表示添加失败
     */
    int addComment(McwComment mcwComment);

    /**
     * 功能描述：根据评论ID更新评论内容
     * <p>
     * 更新数据库中指定评论的内容信息。
     * </p>
     *
     * @param mcwComment 评论对象，包含评论ID和更新的内容
     * @return 返回影响的行数，1表示更新成功，0表示更新失败
     */
    int updateCommentById(McwComment mcwComment);

    /**
     * 功能描述：根据评论ID获取指定评论
     * <p>
     * 根据传入的评论ID查询对应的评论详情。
     * </p>
     *
     * @param commentId 评论的唯一标识符
     * @return 返回对应的评论对象，如果评论不存在则返回 null
     */
    McwComment getCommentById(int commentId);

    /**
     * 功能描述：删除评论
     * <p>
     * 根据评论ID删除数据库中的对应评论记录。
     * </p>
     *
     * @param commentId 评论的唯一标识符
     */
    void delCommentById(int commentId);

    /**
     * 功能描述：统计所有评论数量
     * <p>
     * 查询数据库中所有评论记录的总数。
     * </p>
     *
     * @return 返回评论总数
     */
    int getAllCommentNumber();
}
