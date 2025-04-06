package com.mochenwu.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.article.model.McwArticle;

import java.util.List;

/**
 * 接口描述：文章数据访问层接口
 * <p>定义了文章相关的数据库操作方法</p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
public interface ArticleMapper extends BaseMapper<McwArticle> {

    /**
     * 新增文章
     *
     * @param mcwArticle 文章对象
     * @return 插入成功的记录条数
     */
    int addArticle(McwArticle mcwArticle);

    /**
     * 更新文章
     *
     * @param mcwArticle 文章对象
     */
    void updateArticle(McwArticle mcwArticle);

    /**
     * 查询所有可见的文章
     *
     * @return 可见文章列表
     */
    List<McwArticle> getAllVisibleArticle();

    /**
     * 根据文章ID获取文章
     *
     * @param articleId 文章ID
     * @return 文章对象
     */
    McwArticle getArticleById(int articleId);

    /**
     * 根据文章ID删除文章
     *
     * @param articleId 文章ID
     */
    void delArticleById(int articleId);

    /**
     * 获取所有文章的数量
     *
     * @return 文章总数
     */
    int getAllArticleNumber();
}
