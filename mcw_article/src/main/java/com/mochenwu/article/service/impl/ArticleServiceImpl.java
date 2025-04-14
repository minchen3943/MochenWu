package com.mochenwu.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mochenwu.article.mapper.ArticleMapper;
import com.mochenwu.article.model.McwArticle;
import com.mochenwu.article.service.ArticleService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章服务实现类
 * </p>
 * <p>
 * 实现文章相关业务逻辑，包括文章的新增、更新、查询、删除以及分页查询功能。
 * </p>
 *
 * <p>
 * 主要功能：
 * <ul>
 *     <li>新增文章：设置发布时间、初始化访问量与状态，调用数据访问层新增文章记录；</li>
 *     <li>更新文章：根据传入文章对象更新数据，并返回最新的记录；</li>
 *     <li>查询文章：支持全部查询、分页查询、以及根据ID查询单个文章；</li>
 *     <li>删除文章：根据文章ID删除数据库中对应记录；</li>
 *     <li>统计文章总数：获取所有文章的记录数用于分页等场景。</li>
 * </ul>
 * </p>
 *
 * @author 瞑尘
 * @date 2025-04-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, McwArticle> implements ArticleService {

    /**
     * 文章数据访问对象（Mapper），用于执行数据库操作
     */
    public final ArticleMapper articleMapper;

    /**
     * 日志记录对象，用于记录服务操作和异常信息
     */
    final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    /**
     * 构造方法
     *
     * @param articleMapper 文章数据访问对象，用于操作数据库中文章数据
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     * 新增文章
     * <p>
     * 方法描述：
     * <ol>
     *     <li>设置文章的发布时间为当前时间；</li>
     *     <li>初始化访问次数为0；</li>
     *     <li>初始化文章状态为0（状态定义由业务指定）；</li>
     *     <li>调用mapper方法新增文章记录；</li>
     *     <li>记录日志，并捕获异常后返回false表示操作失败。</li>
     * </ol>
     * </p>
     *
     * @param mcwArticle 文章对象，包含文章基础信息
     * @return 若新增成功返回true，否则返回false
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public boolean addArticle(@NotNull McwArticle mcwArticle) {
        // 设置文章发布时间为当前时间
        mcwArticle.setArticleDate(LocalDateTime.now());
        // 初始化文章访问量为0
        mcwArticle.setArticleVisitorCount(0);
        // 初始化文章状态为0（例如0表示未发布）
        mcwArticle.setArticleStatus(0);
        try {
            // 调用数据库层新增文章记录
            articleMapper.addArticle(mcwArticle);
            // 记录新增成功日志，输出新增后的文章ID
            logger.info("文章添加成功 articleId:{}", mcwArticle.getArticleId());
            return true;
        } catch (Exception e) {
            // 记录错误日志，包含文章ID及错误信息
            logger.warn("文章添加失败 articleId:{} info:{}", mcwArticle.getArticleId(), e.getMessage());
            return false;
        }
    }

    /**
     * 更新文章
     * <p>
     * 方法描述：
     * <ol>
     *     <li>调用mapper更新文章记录；</li>
     *     <li>记录更新成功日志；</li>
     *     <li>通过文章ID重新查询并返回更新后的文章对象。</li>
     * </ol>
     * </p>
     *
     * @param mcwArticle 文章对象，必须包含有效的文章ID以及需要更新的数据
     * @return 更新后的文章对象
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public McwArticle updateArticle(@NotNull McwArticle mcwArticle) {
        // 查询文章是否存在
        if (articleMapper.selectById(mcwArticle.getArticleId()) != null) {
            // 更新文章记录
            articleMapper.updateArticle(mcwArticle);
            // 记录日志：文章更新成功
            logger.info("文章更新成功 articleId:{}", mcwArticle.getArticleId());
            // 重新查询更新后的文章记录并返回
            return articleMapper.getArticleById(mcwArticle.getArticleId());
        } else {
            logger.warn("文章 articleId:{} 不存在", mcwArticle.getArticleId());
            return null;
        }

    }

    /**
     * 查询所有文章
     * <p>
     * 方法描述：
     * 调用数据访问层获取所有可见的文章列表，并记录查询日志。
     * </p>
     *
     * @return 返回所有可见文章的列表
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public List<McwArticle> getAllArticle() {
        // 记录日志，提示开始查询所有文章
        logger.info("查询所有文章成功");
        // 返回所有可见的文章，调用Mapper中相应方法
        return articleMapper.getAllVisibleArticle();
    }

    /**
     * 分页查询文章
     * <p>
     * 方法描述：
     * <ol>
     *     <li>使用PageHelper.startPage方法设置分页参数；</li>
     *     <li>调用Mapper方法查询所有可见文章并返回分页结果；</li>
     *     <li>根据查询结果记录日志，若结果为空则记录错误日志；</li>
     *     <li>若分页查询过程中发生异常，则捕获异常、记录错误日志，并抛出运行时异常。</li>
     * </ol>
     * </p>
     *
     * @param page     页码（从1开始）
     * @param pageSize 每页记录数
     * @return 返回分页后的文章列表
     * @throws RuntimeException 当分页查询过程中发生异常时抛出运行时异常
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public List<McwArticle> getArticleByPage(int page, int pageSize) {
        try (
                // 启用PageHelper进行分页设置，此处返回的Page对象不用于业务，只用于分页控制
                Page<McwArticle> ignored = PageHelper.startPage(page, pageSize)
        ) {
            // 调用Mapper获取分页后的文章数据
            List<McwArticle> articles = articleMapper.getAllVisibleArticle();
            // 根据查询结果记录日志
            if (articles.isEmpty()) {
                logger.warn("分页查询文章数据为空 {page:{}  pageSize:{}}", page, pageSize);
            } else {
                logger.info("分页查询成功 {page:{}  pageSize:{}}", page, pageSize);
            }
            return articles;
        } catch (Exception e) {
            // 记录异常日志，并抛出运行时异常
            logger.warn("分页查询文章时发生错误：{}", e.getMessage());
            throw new IllegalArgumentException("分页查询文章时发生错误：", e);
        }
    }

    /**
     * 根据ID查询文章
     * <p>
     * 方法描述：
     * 根据传入的文章ID调用数据访问层获取对应文章记录，记录查询日志。
     * 若未查询到文章则返回null。
     * </p>
     *
     * @param articleId 文章ID
     * @return 返回文章对象，如果文章不存在则返回null
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public McwArticle getArticleById(int articleId) {
        // 记录查询操作日志，标记正在根据ID查询文章
        logger.info("查询文章成功 articleId:{}", articleId);
        return articleMapper.getArticleById(articleId);
    }

    /**
     * 删除文章
     * <p>
     * 方法描述：
     * 根据传入的文章ID调用数据访问层删除对应的文章记录，
     * 同时记录删除操作日志。
     * </p>
     *
     * @param articleId 文章ID
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public void delArticleById(int articleId) {
        // 调用Mapper删除文章记录
        articleMapper.delArticleById(articleId);
        // 记录删除成功日志
        logger.info("删除文章成功 articleId:{}", articleId);
    }

    /**
     * 查询文章总数
     * <p>
     * 方法描述：
     * 调用数据访问层方法获取所有文章的总记录数，
     * 并记录查询日志。
     * </p>
     *
     * @return 返回文章总记录数
     * @author 瞑尘
     * @date 2025-04-06
     */
    @Override
    public int getAllArticleNumber() {
        // 调用Mapper方法获取文章总数
        int output = articleMapper.getAllArticleNumber();
        // 记录日志，显示查询结果
        logger.info("查询文章总数成功 {}", output);
        return output;
    }
}
