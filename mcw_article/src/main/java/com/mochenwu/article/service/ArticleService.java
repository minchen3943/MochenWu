package com.mochenwu.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.article.model.McwArticle;

import java.util.List;

/**
 * 接口描述：文章业务接口
 * <p>
 * 提供文章的新增、更新、查询、删除、分页查询及统计总数等业务方法。
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
public interface ArticleService extends IService<McwArticle> {

    /**
     * 功能描述：新增文章
     * <p>
     * 新增一篇文章记录。
     * </p>
     *
     * @param mcwArticle 文章对象
     */
    void addArticle(McwArticle mcwArticle);

    /**
     * 功能描述：更新文章
     * <p>
     * 更新文章信息并返回更新后的文章对象。
     * </p>
     *
     * @param mcwArticle 文章对象
     * @return 更新后的文章对象
     */
    McwArticle updateArticle(McwArticle mcwArticle);

    /**
     * 功能描述：查询所有文章
     * <p>
     * 获取所有可见的文章列表。
     * </p>
     *
     * @return 文章列表
     */
    List<McwArticle> getAllArticle();

    /**
     * 功能描述：分页查询文章
     * <p>
     * 根据页码和每页记录数获取分页后的文章列表。
     * </p>
     *
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页后的文章列表
     */
    List<McwArticle> getArticleByPage(int page, int pageSize);

    /**
     * 功能描述：根据ID查询文章
     * <p>
     * 获取指定文章ID对应的文章对象。
     * </p>
     *
     * @param articleId 文章ID
     * @return 文章对象，如果不存在则返回null
     */
    McwArticle getArticleById(int articleId);

    /**
     * 功能描述：删除文章
     * <p>
     * 根据文章ID删除对应的文章记录。
     * </p>
     *
     * @param articleId 文章ID
     */
    void delArticleById(int articleId);

    /**
     * 功能描述：查询文章总数
     * <p>
     * 统计所有文章的记录数量。
     * </p>
     *
     * @return 文章总数
     */
    int getAllArticleNumber();
}
