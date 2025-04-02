package com.mochenwu.comment.controller;


import com.mochenwu.comment.model.McwComment;
import com.mochenwu.comment.service.CommentService;
import com.mochenwu.general.model.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论管理模块的控制器类
 * <p>
 * 提供对评论的增、查功能接口，包括获取所有评论、分页查询评论、通过ID查询评论，以及添加和修改评论。
 * </p>
 *
 * @author 瞑尘
 * @Date 2025-02-21 22:15:04
 */
@RestController
@RequestMapping("/comment/*")
public class CommentController {

    private final CommentService commentService;

    /**
     * 构造函数注入 CommentService 实现类
     * <p>
     * 通过构造函数注入 CommentService，用于处理评论相关的业务逻辑。
     * </p>
     *
     * @param commentService 注入的 CommentService 实现类
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 获取所有评论的接口
     * <p>
     * 该接口用于返回系统中所有的评论数据。
     * </p>
     *
     * @return 包含评论列表和状态信息的响应实体
     */
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<McwComment>>> allComment() {
        try {
            // 尝试从服务层获取所有评论
            List<McwComment> comments = commentService.getAllComment();
            // 返回包含评论列表的成功响应
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有可见评论成功", comments));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 分页获取评论的接口
     * <p>
     * 该接口用于分页查询评论数据，根据传入的页码返回相应的评论数据。
     * </p>
     *
     * @param page 查询的页码
     * @return 包含评论列表和状态信息的响应实体
     */
    @GetMapping("/get/page")
    public ResponseEntity<ApiResponse<List<McwComment>>> getCommentByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            // 获取指定页码的评论数据
            List<McwComment> comments = commentService.getCommentByPage(page, pageSize);
            // 如果没有评论数据，返回 404 错误
            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "查询失败，数据为空", null));
            }
            // 返回成功响应，包含评论列表
            return ResponseEntity.ok(new ApiResponse<>(200, "分页获取评论成功", comments));
        } catch (ValidationException e) {
            // 处理分页参数错误时的异常，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 根据评论ID获取单条评论的接口
     * <p>
     * 该接口用于根据评论ID查询单条评论。
     * </p>
     *
     * @param commentId 评论ID
     * @return 包含评论对象和状态信息的响应实体
     */
    @GetMapping("/get/id")
    public ResponseEntity<ApiResponse<McwComment>> getCommentById(@RequestParam("commentId") int commentId) {
        try {
            // 获取指定ID的评论数据
            McwComment comment = commentService.getCommentById(commentId);
            // 如果没有找到评论，返回 404 错误
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "评论id:" + commentId + " 不存在", null));
            }
            // 返回成功响应，包含评论对象
            return ResponseEntity.ok(new ApiResponse<>(200, "Id获取评论成功", comment));
        } catch (ValidationException e) {
            // 处理分页参数错误时的异常，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 添加新评论的接口
     * <p>
     * 该接口用于接收用户提交的新评论，并将其保存到数据库中。
     * </p>
     *
     * @param mcwComment 提交的评论对象
     * @return 包含状态信息和已添加评论的响应实体
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ApiResponse<McwComment>> setComment(@RequestBody McwComment mcwComment) {
        if (mcwComment != null) {
            try {
                // 尝试通过服务层添加新的评论
                commentService.addComment(mcwComment);
                // 返回成功响应，包含已添加的评论
                return ResponseEntity.ok(new ApiResponse<>(200, "添加评论成功", mcwComment));
            } catch (ValidationException e) {
                // 处理评论数据的验证异常（例如字段缺失或数据无效），返回 400 错误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                // 捕获其他所有异常并返回服务器错误响应
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 如果评论对象不正确，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "提交评论有误", null));
        }
    }

    /**
     * 修改评论的接口
     * <p>
     * 该接口用于修改已存在的评论。
     * </p>
     *
     * @param mcwComment 更新的评论对象
     * @return 包含状态信息和更新后的评论的响应实体
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<McwComment>> updateComment(@RequestBody McwComment mcwComment) {
        if (mcwComment != null) {
            try {
                McwComment finalComment = commentService.updateComment(mcwComment);
                if (finalComment == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "提交评论有误", null));
                } else {
                    // 更新评论并返回成功响应，包含更新后的评论
                    return ResponseEntity.ok(new ApiResponse<>(200, "修改评论成功", commentService.updateComment(mcwComment)));
                }
            } catch (ValidationException e) {
                // 处理评论数据的验证异常（例如字段缺失或数据无效），返回 400 错误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                // 捕获其他所有异常并返回服务器错误响应
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 如果评论对象不正确，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "提交评论有误", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<McwComment>> delComment(@RequestParam("commentId") int commentId) {
        try {
            // 获取指定ID的评论数据
            McwComment comment = commentService.getCommentById(commentId);
            // 如果没有找到评论，返回 404 错误
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "评论id:" + commentId + " 不存在", null));
            }
            // 删除评论并返回成功响应
            commentService.delCommentById(commentId);
            return ResponseEntity.ok(new ApiResponse<>(200, "删除评论成功", null));
        } catch (ValidationException e) {
            // 处理评论数据的验证异常（例如字段缺失或数据无效），返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }
    @GetMapping("/get/page/number")
    public ResponseEntity<ApiResponse<Integer>> getCommentPageNumber(@RequestParam("pageSize") int pageSize) {
        try {
            Integer pageNumber = (int) Math.ceil((double) commentService.getAllCommentNumber() / (double) pageSize);
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有页数成功", pageNumber));
        } catch (ValidationException e) {
            // 处理分页参数错误时的异常，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }
}
