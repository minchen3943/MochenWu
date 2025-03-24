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
 * @author 32035
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, McwArticle> implements ArticleService {
    public final ArticleMapper articleMapper;
    Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public void addArticle(McwArticle mcwArticle) {
        mcwArticle.setArticleDate(LocalDateTime.now());
        mcwArticle.setArticleVisitorCount(0);
        mcwArticle.setArticleStatus(0);
        articleMapper.addArticle(mcwArticle);
        logger.info("Article added {articleId:{}}", mcwArticle.getArticleId());
    }

    @Override
    public McwArticle updateArticle(McwArticle mcwArticle) {
        articleMapper.updateArticle(mcwArticle);
        logger.info("Article updated {articleId:{}}", mcwArticle.getArticleId());
        return articleMapper.getArticleById(mcwArticle.getArticleId());
    }

    @Override
    public List<McwArticle> getAllComment() {
        return articleMapper.getAllVisibleArticle();
    }

    @Override
    public List<McwArticle> getCommentByPage(int page, int pageSize) {
        try (Page<McwArticle> ignored = PageHelper.startPage(page, pageSize)) {
            // 调用 articleMapper 获取分页后的文章列表
            logger.info("分页查询成功 {page:{}  pageSize:{}}", page, pageSize);
            return articleMapper.getAllVisibleArticle();
        } catch (Exception e) {
            // 捕获异常并抛出运行时异常
            logger.error("分页查询文章时发生错误：{}", e.getMessage());
            throw new RuntimeException("分页查询文章时发生错误：", e);
        }
    }

    @Override
    public McwArticle getCommentById(int commentId) {
        return articleMapper.getArticleById(commentId);
    }

    @Override
    public void delArticleById(int articleId) {
        articleMapper.delArticleById(articleId);
        logger.info("Article del {articleId:{}}", articleId);
    }

}
