package com.mochenwu.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.article.model.McwArticle;

import java.util.List;

/**
 * @author 32035
 */
public interface ArticleService extends IService<McwArticle> {
    void addArticle(McwArticle mcwArticle);

    McwArticle updateArticle(McwArticle mcwArticle);

    List<McwArticle> getAllComment();

    List<McwArticle> getCommentByPage(int page, int pageSize);

    McwArticle getCommentById(int commentId);

    void delArticleById(int articleId);
}
