package com.mochenwu.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * 数据统计实体类
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@Entity
@Table(name = "mcw_data")
public class McwData {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    private Integer id;

    /**
     * 访问人数统计
     */
    @NotNull
    @Column(name = "visitor_count", nullable = false)
    private Integer visitorCount;

    /**
     * 喜欢人数统计
     */
    @NotNull
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    /**
     * 获取主键ID
     *
     * @return 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取访问人数
     *
     * @return 访问人数
     */
    public Integer getVisitorCount() {
        return visitorCount;
    }

    /**
     * 设置访问人数
     *
     * @param visitorCount 访问人数
     */
    public void setVisitorCount(Integer visitorCount) {
        this.visitorCount = visitorCount;
    }

    /**
     * 获取喜欢人数
     *
     * @return 喜欢人数
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * 设置喜欢人数
     *
     * @param likeCount 喜欢人数
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}