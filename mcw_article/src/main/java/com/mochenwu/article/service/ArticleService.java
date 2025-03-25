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

    List<McwArticle> getAllArticle();

    List<McwArticle> getArticleByPage(int page, int pageSize);

    McwArticle getArticleById(int articleId);

    void delArticleById(int articleId);

    int getAllArticleNumber();
}
