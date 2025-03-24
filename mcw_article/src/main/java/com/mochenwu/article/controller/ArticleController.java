package com.mochenwu.article.controller;


import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.service.ArticleService;
import com.mochenwu.article.util.AliOSSModel;
import com.mochenwu.article.util.AliOSSUtil;
import com.mochenwu.general.model.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 瞑尘
 **/

@RestController
@RequestMapping("/article/*")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<McwArticle>> addArticle(@RequestParam("file") MultipartFile file, @ModelAttribute McwArticle mcwArticle) {
        if (!(mcwArticle.getArticleTitle() == null) && !(file.isEmpty()) && !(file.getContentType() == null) && file.getContentType().startsWith("text/markdown")) {
            try {
                AliOSSModel output = AliOSSUtil.upload(file);
                mcwArticle.setArticleUrl(output.getUrl());
                mcwArticle.setArticleName(output.getFileName());
                articleService.addArticle(mcwArticle);
                return ResponseEntity.ok(new ApiResponse<>(200, "添加文章成功", mcwArticle));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "提交文章有误", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<McwArticle>> deleteArticle(@RequestParam("articleId") int articleId) {
        if (articleService.getArticleById(articleId) != null) {
            try {
                McwArticle article = articleService.getArticleById(articleId);
                AliOSSUtil.delete(article);
                articleService.delArticleById(articleId);
                return ResponseEntity.ok(new ApiResponse<>(200, "删除文章成功", null));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(404, "文章id:" + articleId + " 不存在", null));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<McwArticle>> updateArticle(@RequestParam("mcwArticle") McwArticle mcwArticle) {
        if (mcwArticle.getArticleId() != null) {
            try {
                return ResponseEntity.ok(new ApiResponse<>(200, "修改文章成功", articleService.updateArticle(mcwArticle)));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "修改文章需要articleId", null));
        }
    }

    @PutMapping("/visitorAdd")
    public ResponseEntity<ApiResponse<McwArticle>> articleVisitorAdd(@RequestParam("articleId") int articleId) {
        McwArticle article = articleService.getArticleById(articleId);
        if (article != null) {
            try {
                article.setArticleVisitorCount(article.getArticleVisitorCount() + 1);
                return ResponseEntity.ok(new ApiResponse<>(200, "阅读人数修改成功", articleService.updateArticle(article)));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "文章id:" + articleId + " 不存在", null));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<McwArticle>>> allArticle() {
        try {
            // 尝试从服务层获取所有文章
            List<McwArticle> articles = articleService.getAllArticle();
            // 返回包含文章列表的成功响应
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有可见文章成功", articles));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get/page")
    public ResponseEntity<ApiResponse<List<McwArticle>>> getArticleByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            // 获取指定页码的文章数据
            List<McwArticle> articles = articleService.getArticleByPage(page, pageSize);
            // 如果没有文章数据，返回 404 错误
            if (articles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "查询失败", null));
            }
            // 返回成功响应，包含文章列表
            return ResponseEntity.ok(new ApiResponse<>(200, "分页获取文章成功", articles));
        } catch (ValidationException e) {
            // 处理分页参数错误时的异常，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            // 捕获其他所有异常并返回服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get/id")
    public ResponseEntity<ApiResponse<McwArticle>> getArticleById(@RequestParam("articleId") int articleId) {
        try {
            // 获取指定ID的文章数据
            McwArticle article = articleService.getArticleById(articleId);
            // 如果没有找到文章，返回 404 错误
            if (article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "文章id:" + articleId + " 不存在", null));
            }
            // 返回成功响应，包含文章对象
            return ResponseEntity.ok(new ApiResponse<>(200, "Id获取文章成功", article));
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
