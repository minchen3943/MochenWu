package com.mochenwu.article.controller;

import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.service.ArticleService;
import com.mochenwu.article.util.AliOSSModel;
import com.mochenwu.article.util.AliOSSUtil;
import com.mochenwu.general.response.ApiResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 类描述：文章管理控制器
 * <p>负责文章的新增、删除、修改、查询等操作</p>
 * @author 瞑尘
 * @date 2025-04-06
 */
@RestController
@RequestMapping("/api/article/*")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 构造方法
     *
     * @param articleService 文章服务对象
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 功能描述：新增文章
     * <p>通过上传markdown格式文件和文章信息新增文章</p>
     *
     * @param file       上传的markdown文件
     * @param mcwArticle 文章信息对象
     * @return 新增文章的响应实体，包含操作状态和文章对象
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
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

    /**
     * 功能描述：删除文章
     * <p>根据文章ID删除对应的文章，同时删除OSS上存储的文件</p>
     *
     * @param articleId 文章ID
     * @return 删除操作的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
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

    /**
     * 功能描述：更新文章
     * <p>根据传入的文章对象更新文章内容，文章对象必须包含articleId</p>
     *
     * @param mcwArticle 文章信息对象
     * @return 更新操作的响应实体，包含更新后的文章对象
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<McwArticle>> updateArticle(@RequestBody McwArticle mcwArticle) {
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

    /**
     * 功能描述：增加文章访问次数
     * <p>根据文章ID增加文章的访问计数</p>
     *
     * @param articleId 文章ID
     * @return 更新操作的响应实体，包含更新后的文章对象
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
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

    /**
     * 功能描述：查询所有文章
     * <p>获取所有可见文章列表</p>
     *
     * @return 包含文章列表的响应实体
     * @throws Exception 捕获所有异常返回服务器错误
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<McwArticle>>> allArticle() {
        try {
            List<McwArticle> articles = articleService.getAllArticle();
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有可见文章成功", articles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：分页查询文章
     * <p>根据页码和页面大小获取文章列表</p>
     *
     * @param page     页码
     * @param pageSize 每页大小
     * @return 包含分页文章列表的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get/page")
    public ResponseEntity<ApiResponse<List<McwArticle>>> getArticleByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            List<McwArticle> articles = articleService.getArticleByPage(page, pageSize);
            if (articles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "查询失败，数据为空", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "分页获取文章成功", articles));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：根据ID查询文章
     * <p>获取指定文章ID对应的文章</p>
     *
     * @param articleId 文章ID
     * @return 包含文章对象的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get/id")
    public ResponseEntity<ApiResponse<McwArticle>> getArticleById(@RequestParam("articleId") int articleId) {
        try {
            McwArticle article = articleService.getArticleById(articleId);
            if (article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "文章id:" + articleId + " 不存在", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "Id获取文章成功", article));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 功能描述：获取文章总页数
     * <p>根据每页大小计算所有文章的总页数</p>
     *
     * @param pageSize 每页文章数量
     * @return 包含总页数的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get/page/number")
    public ResponseEntity<ApiResponse<Integer>> getArticlePageNumber(@RequestParam("pageSize") int pageSize) {
        try {
            Integer pageNumber = (int) Math.ceil((double) articleService.getAllArticleNumber() / (double) pageSize);
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有页数成功", pageNumber));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }
}
