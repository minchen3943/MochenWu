package com.mochenwu.article.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.article.model.McwArticle;

import java.util.List;

/**
 * @author : 瞑尘
 **/
public interface ArticleMapper extends BaseMapper<McwArticle> {
    int addArticle(McwArticle mcwArticle);

    void updateArticle(McwArticle mcwArticle);

    List<McwArticle> getAllVisibleArticle();

    McwArticle getArticleById(int articleId);

    void delArticleById(int articleId);
}
