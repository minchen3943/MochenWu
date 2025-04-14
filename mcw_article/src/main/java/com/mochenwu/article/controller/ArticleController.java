package com.mochenwu.article.controller;

import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.model.TencentCOSModel;
import com.mochenwu.article.response.ApiResponse;
import com.mochenwu.article.service.ArticleService;
import com.mochenwu.article.util.TencentCOSUtil;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文章管理控制器
 * </p>
 * <p>
 * 负责文章的新增、删除、修改、查询等操作；
 * 同时还处理与腾讯COS对接的文件上传/删除操作。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
@RestController
@RequestMapping("/api/article/*")
public class ArticleController {

    /**
     * 腾讯COS工具类，用于文件上传和删除
     */
    private final TencentCOSUtil cosUtil;

    /**
     * 文章服务对象，封装文章相关的增删改查业务逻辑
     */
    private final ArticleService articleService;

    /**
     * 构造方法
     *
     * @param articleService 文章服务对象，负责文章数据的处理
     * @param cosUtil        腾讯COS工具类，用于处理文件上传与删除操作
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Autowired
    public ArticleController(ArticleService articleService, TencentCOSUtil cosUtil) {
        this.articleService = articleService;
        this.cosUtil = cosUtil;
    }

    /**
     * 新增文章
     * <p>
     * 通过上传 markdown 格式文件和文章信息新增文章。
     * 主要步骤：
     * <ol>
     *     <li>校验上传文件是否为空；</li>
     *     <li>校验文件的Content-Type（必须以"text/markdown"开头）；</li>
     *     <li>调用腾讯COS工具类上传文件，返回文件相关信息；</li>
     *     <li>将返回的文件信息保存至文章对象中；</li>
     *     <li>调用文章服务新增文章记录；</li>
     *     <li>处理异常，根据不同异常类型返回相应的错误状态码和信息。</li>
     * </ol>
     * </p>
     *
     * @param file       上传的 markdown 文件，非空且Content-Type符合规定
     * @param mcwArticle 文章信息对象，包含文章标题、描述等基本信息
     * @param errors     校验错误信息（若有校验规则，可通过此参数获取详细错误）
     * @return 新增文章的响应实体，包含操作状态和文章对象
     * @throws ValidationException 数据校验异常（例如参数格式不正确）
     * @author 瞑尘
     * @date 2025-04-06
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<McwArticle>> addArticle(@RequestParam("file") MultipartFile file, @ModelAttribute McwArticle mcwArticle, Errors errors) {
        // 1. 如果上传文件为空，则返回400错误
        if (file == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "提交文章为空", null));
        }
        // 获取文件的类型
        String contentType = file.getContentType();
        // 2. 判断文章标题非空、文件不为空且Content-Type符合markdown规范
        if (mcwArticle.getArticleTitle() != null && !file.isEmpty() && contentType != null && contentType.startsWith("text/markdown")) {
            try {
                // 上传文件到腾讯COS，返回上传结果对象
                TencentCOSModel output = cosUtil.upload(file);
                // 保存COS返回的各项信息到文章对象中
                mcwArticle.setArticleName(output.getFileName());
                mcwArticle.setArticleBucket(output.getBucket());
                mcwArticle.setArticleRegion(output.getRegion());
                mcwArticle.setArticleKey(output.getKey());
                // 调用文章服务新增文章记录，如添加失败则返回500错误
                if (!articleService.addArticle(mcwArticle)) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误", null));
                }
                // 返回200响应，表示添加文章成功
                return ResponseEntity.ok(new ApiResponse<>(200, "添加文章成功", mcwArticle));
            } catch (ValidationException e) {
                // 返回400错误，指明输入数据有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                // 其他异常返回500内部服务器错误
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 参数或文件不符合要求，返回400错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "提交文章有误", null));
        }
    }

    /**
     * 删除文章
     * <p>
     * 根据文章ID删除对应的文章记录，同时调用COS工具删除OSS上存储的文件。
     * 主要步骤：
     * <ol>
     *     <li>根据ID获取文章；</li>
     *     <li>若文章存在，调用COS删除文件；</li>
     *     <li>删除数据库中的文章记录；</li>
     *     <li>异常处理返回相应状态码。</li>
     * </ol>
     * </p>
     *
     * @param articleId 文章ID，用于确定需要删除的文章
     * @return 删除操作的响应实体，含操作结果状态码和提示信息
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<McwArticle>> deleteArticle(@RequestParam("articleId") int articleId) {
        // 先判断文章是否存在
        if (articleService.getArticleById(articleId) != null) {
            try {
                // 获取文章信息，用于后续删除COS文件和数据库记录
                McwArticle article = articleService.getArticleById(articleId);
                // 调用COS工具删除存储的文件，若删除失败则返回500错误
                if (!cosUtil.delete(article)) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "COS删除文章错误 articleId:" + articleId, null));
                }
                // 删除数据库中文章记录
                articleService.delArticleById(articleId);
                // 返回删除成功信息
                return ResponseEntity.ok(new ApiResponse<>(200, "删除文章成功, articleId:" + articleId, null));
            } catch (ValidationException e) {
                // 输入数据问题返回400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                // 其他异常返回500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 如果文章不存在，则返回404提示
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(404, "文章id:" + articleId + " 不存在", null));
        }
    }

    /**
     * 更新文章
     * <p>
     * 根据传入的文章对象更新文章内容，要求文章对象必须包含有效的articleId。
     * 主要步骤：
     * <ol>
     *     <li>判断传入文章是否含有articleId；</li>
     *     <li>调用更新服务进行数据更新；</li>
     *     <li>捕获异常，返回相应错误提示；</li>
     * </ol>
     * </p>
     *
     * @param mcwArticle 文章信息对象，必须包含 articleId 和要更新的数据
     * @return 更新操作的响应实体，包含更新后的文章对象及相应状态信息
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<McwArticle>> updateArticle(@RequestBody McwArticle mcwArticle) {
        // 校验文章ID是否存在，若不存在直接返回400错误
        if (mcwArticle.getArticleId() != null) {
            try {
                // 调用业务层更新操作，返回更新后的文章对象
                McwArticle updatedArticle = articleService.updateArticle(mcwArticle);
                if (updatedArticle == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(500, "文章id: " + mcwArticle.getArticleId() + " 不存在", null));
                }
                return ResponseEntity.ok(new ApiResponse<>(200, "修改文章成功", updatedArticle));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 若articleId为空，则更新操作无法进行
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "修改文章需要articleId", null));
        }
    }

    /**
     * 增加文章访问次数
     * <p>
     * 根据传入的文章ID获取文章后，将文章的访问计数加1，并更新文章记录。
     * 主要步骤：
     * <ol>
     *     <li>获取文章记录；</li>
     *     <li>增加文章访问次数；</li>
     *     <li>调用更新服务保存更新后的记录；</li>
     * </ol>
     * </p>
     *
     * @param articleId 文章ID
     * @return 返回更新后的文章记录及操作状态信息的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @PutMapping("/visitorAdd")
    public ResponseEntity<ApiResponse<McwArticle>> articleVisitorAdd(@RequestParam("articleId") int articleId) {
        // 获取文章对象
        McwArticle article = articleService.getArticleById(articleId);
        if (article != null) {
            try {
                // 累加访问计数：注意这里直接调用get/set确保线程安全由业务层保证
                article.setArticleVisitorCount(article.getArticleVisitorCount() + 1);
                // 更新记录后返回修改后的文章对象
                McwArticle updatedArticle = articleService.updateArticle(article);
                return ResponseEntity.ok(new ApiResponse<>(200, "阅读人数修改成功", updatedArticle));
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "输入数据无效: " + e.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
            }
        } else {
            // 无法找到对应文章时返回400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "文章id:" + articleId + " 不存在", null));
        }
    }

    /**
     * 查询所有文章
     * <p>
     * 获取所有可见文章列表，调用业务层获取文章数据。
     * </p>
     *
     * @return 包含文章列表的响应实体，状态码200表示获取成功
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<McwArticle>>> allArticle() {
        try {
            // 调用业务层获取所有文章列表
            List<McwArticle> articles = articleService.getAllArticle();
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有可见文章成功", articles));
        } catch (Exception e) {
            // 捕获异常，返回500错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 分页查询文章
     * <p>
     * 根据传入的页码和每页大小，分页获取文章列表。
     * 主要步骤：
     * <ol>
     *     <li>调用业务层分页查询方法；</li>
     *     <li>如果结果为空，返回404；</li>
     *     <li>否则返回文章列表。</li>
     * </ol>
     * </p>
     *
     * @param page     页码（从1开始）
     * @param pageSize 每页显示的文章数量
     * @return 包含分页文章列表的响应实体
     * @throws ValidationException 数据校验异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @GetMapping("/get/page")
    public ResponseEntity<ApiResponse<List<McwArticle>>> getArticleByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            // 调用分页查询，获取文章列表
            List<McwArticle> articles = articleService.getArticleByPage(page, pageSize);
            // 若查询结果为空，则返回404状态
            if (articles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "查询失败，数据为空", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "分页获取文章成功", articles));
        } catch (ValidationException e) {
            // 校验异常返回400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            // 其它异常返回500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 根据ID查询文章
     * <p>
     * 根据文章ID获取具体的文章对象。如果文章不存在，返回404状态码。
     * </p>
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
            // 获取指定ID的文章
            McwArticle article = articleService.getArticleById(articleId);
            // 如果查询结果为null则返回404
            if (article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "文章id:" + articleId + " 不存在", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "Id获取文章成功", article));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }

    /**
     * 获取文章总页数
     * <p>
     * 根据传入每页文章数量，计算所有文章的总页数，采用向上取整方式确保最后一页能完整显示剩余记录。
     * </p>
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
            // 获取文章总记录数，计算总页数（向上取整）
            int totalArticles = articleService.getAllArticleNumber();
            Integer pageNumber = (int) Math.ceil((double) totalArticles / pageSize);
            return ResponseEntity.ok(new ApiResponse<>(200, "获取所有页数成功", pageNumber));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(500, "服务器错误", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "服务器错误: " + e.getMessage(), null));
        }
    }
}
