package com.mochenwu.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mochenwu.article.mapper.ArticleMapper;
import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 类描述：文章服务实现类
 * <p>
 * 实现文章相关业务逻辑，封装文章的新增、更新、查询、删除以及分页查询功能。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, McwArticle> implements ArticleService {

    public final ArticleMapper articleMapper;
    final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    /**
     * 构造方法
     *
     * @param articleMapper 文章数据访问对象
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     * 功能描述：新增文章
     * <p>
     * 设置文章的发布时间、访问量和状态后，调用数据访问层方法新增文章。
     * </p>
     *
     * @param mcwArticle 文章对象
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public void addArticle(McwArticle mcwArticle) {
        mcwArticle.setArticleDate(LocalDateTime.now());
        mcwArticle.setArticleVisitorCount(0);
        mcwArticle.setArticleStatus(0);
        articleMapper.addArticle(mcwArticle);
        logger.info("Article added {articleId:{}}", mcwArticle.getArticleId());
    }

    /**
     * 功能描述：更新文章
     * <p>
     * 根据传入的文章对象更新文章信息，并返回更新后的文章对象。
     * </p>
     *
     * @param mcwArticle 文章对象，必须包含有效的文章ID
     * @return 更新后的文章对象
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public McwArticle updateArticle(McwArticle mcwArticle) {
        articleMapper.updateArticle(mcwArticle);
        logger.info("Article updated {articleId:{}}", mcwArticle.getArticleId());
        return articleMapper.getArticleById(mcwArticle.getArticleId());
    }

    /**
     * 功能描述：查询所有文章
     * <p>
     * 获取所有可见文章列表。
     * </p>
     *
     * @return 可见文章列表
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public List<McwArticle> getAllArticle() {
        logger.info("Article query all");
        return articleMapper.getAllVisibleArticle();
    }

    /**
     * 功能描述：分页查询文章
     * <p>
     * 根据页码和页面大小使用PageHelper进行分页查询，获取分页后的文章列表。
     * </p>
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页后的文章列表
     * @throws RuntimeException 分页查询过程中发生异常时抛出运行时异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public List<McwArticle> getArticleByPage(int page, int pageSize) {
        try (Page<McwArticle> ignored = PageHelper.startPage(page, pageSize)) {
            // 调用articleMapper获取分页后的文章列表
            List<McwArticle> articles = articleMapper.getAllVisibleArticle();
            if (articles.isEmpty()) {
                logger.error("分页查询文章数据为空 {page:{}  pageSize:{}}", page, pageSize);
            } else {
                logger.info("分页查询成功 {page:{}  pageSize:{}}", page, pageSize);
            }
            return articles;
        } catch (Exception e) {
            // 捕获异常并抛出运行时异常
            logger.error("分页查询文章时发生错误：{}", e.getMessage());
            throw new RuntimeException("分页查询文章时发生错误：", e);
        }
    }

    /**
     * 功能描述：根据ID查询文章
     * <p>
     * 根据文章ID获取对应的文章信息。
     * </p>
     *
     * @param articleId 文章ID
     * @return 文章对象，如果未找到则返回null
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public McwArticle getArticleById(int articleId) {
        logger.info("Article query by id {}", articleId);
        return articleMapper.getArticleById(articleId);
    }

    /**
     * 功能描述：删除文章
     * <p>
     * 根据文章ID删除对应的文章记录。
     * </p>
     *
     * @param articleId 文章ID
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public void delArticleById(int articleId) {
        articleMapper.delArticleById(articleId);
        logger.info("Article del {articleId:{}}", articleId);
    }

    /**
     * 功能描述：查询文章总数
     * <p>
     * 获取所有文章的总记录数。
     * </p>
     *
     * @return 文章总数
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public int getAllArticleNumber() {
        int output = articleMapper.getAllArticleNumber();
        logger.info("查询文章总数成功 {}", output);
        return output;
    }
}
