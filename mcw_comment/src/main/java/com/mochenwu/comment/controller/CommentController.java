package com.mochenwu.comment.controller;

import com.mochenwu.comment.model.McwComment;
import com.mochenwu.comment.service.CommentService;
import com.mochenwu.general.response.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类描述：评论管理控制器类
 * <p>
 * 提供对评论的增、查、删、改等操作接口，包括获取所有评论、分页查询评论、通过ID查询评论，以及添加、修改和删除评论。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@RestController
@RequestMapping("/api/comment/*")
public class CommentController {

    private final CommentService commentService;

    /**
     * 构造函数注入 CommentService 实现类
     *
     * @param commentService 评论业务逻辑服务对象
     * @author 瞑尘
     * @date 2025-02-21
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 功能描述：获取所有评论
     * <p>
     * 查询并返回系统中所有的评论数据。
     * </p>
     *
     * @return 包含评论列表和状态信息的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<McwComment>>> allComment() {
        try {
            List<McwComment> comments = commentService.getAllComment();
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有可见评论成功", comments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：分页获取评论
     * <p>
     * 根据页码和页面大小分页查询评论数据，并返回相应的评论列表。
     * </p>
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 包含分页评论列表和状态信息的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @GetMapping("/get/page")
    public ResponseEntity<ApiResponse<List<McwComment>>> getCommentByPage(@RequestParam("page") int page,
                                                                          @RequestParam("pageSize") int pageSize) {
        try {
            List<McwComment> comments = commentService.getCommentByPage(page, pageSize);
            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "查询失败，数据为空", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "分页获取评论成功", comments));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：根据评论ID查询评论
     * <p>
     * 根据传入的评论ID查询对应的评论数据。
     * </p>
     *
     * @param commentId 评论ID
     * @return 包含查询到的评论对象和状态信息的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @GetMapping("/get/id")
    public ResponseEntity<ApiResponse<McwComment>> getCommentById(@RequestParam("commentId") int commentId) {
        try {
            McwComment comment = commentService.getCommentById(commentId);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "评论id:" + commentId + " 不存在", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "Id获取评论成功", comment));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：添加新评论
     * <p>
     * 接收前端提交的评论数据，并调用服务层新增评论。
     * </p>
     *
     * @param mcwComment 提交的评论对象
     * @return 包含添加成功状态和评论对象的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ApiResponse<McwComment>> setComment(@RequestBody McwComment mcwComment) {
        if (mcwComment != null) {
            try {
                commentService.addComment(mcwComment);
                return ResponseEntity.ok(new ApiResponse<>(200, "添加评论成功", mcwComment));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "提交评论有误", null));
        }
    }

    /**
     * 功能描述：修改评论
     * <p>
     * 接收前端提交的更新评论数据，并调用服务层更新对应的评论记录。
     * </p>
     *
     * @param mcwComment 更新后的评论对象
     * @return 包含修改成功状态和更新后评论对象的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<McwComment>> updateComment(@RequestBody McwComment mcwComment) {
        if (mcwComment != null) {
            try {
                McwComment finalComment = commentService.updateComment(mcwComment);
                if (finalComment == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse<>(400, "提交评论有误", null));
                } else {
                    return ResponseEntity.ok(new ApiResponse<>(200, "修改评论成功", finalComment));
                }
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "提交评论有误", null));
        }
    }

    /**
     * 功能描述：删除评论
     * <p>
     * 根据评论ID删除对应的评论记录。
     * </p>
     *
     * @param commentId 评论ID
     * @return 包含删除成功状态的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<McwComment>> delComment(@RequestParam("commentId") int commentId) {
        try {
            McwComment comment = commentService.getCommentById(commentId);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "评论id:" + commentId + " 不存在", null));
            }
            commentService.delCommentById(commentId);
            return ResponseEntity.ok(new ApiResponse<>(200, "删除评论成功", null));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：获取评论分页总页数
     * <p>
     * 根据每页记录数计算所有评论的总页数。
     * </p>
     *
     * @param pageSize 每页记录数
     * @return 包含总页数的响应实体
     * @author 瞑尘
     * @date 2025-02-21
     */
    @GetMapping("/get/page/number")
    public ResponseEntity<ApiResponse<Integer>> getCommentPageNumber(@RequestParam("pageSize") int pageSize) {
        try {
            Integer pageNumber = (int) Math.ceil((double) commentService.getAllCommentNumber() / (double) pageSize);
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有页数成功", pageNumber));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }
}
