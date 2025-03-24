package com.mochenwu.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * @author 32035
 */
@Entity
@Table(name = "mcw_data")
public class McwData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "visitor_count", nullable = false)
    private Integer visitorCount;

    @NotNull
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(Integer visitorCount) {
        this.visitorCount = visitorCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

}