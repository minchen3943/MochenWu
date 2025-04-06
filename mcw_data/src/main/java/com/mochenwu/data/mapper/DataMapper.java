package com.mochenwu.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.data.model.McwData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据统计Mapper接口
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@Mapper
public interface DataMapper extends BaseMapper<McwData> {

    /**
     * 增加访问人数统计
     *
     * @param visitorCount 要增加的访问人数
     */
    void addOneVisitorCount(int visitorCount);

    /**
     * 查询当前访问人数
     *
     * @return 当前访问人数
     */
    int selectVisitorCount();

    /**
     * 查询当前喜欢人数
     *
     * @return 当前喜欢人数
     */
    Integer selectLikeCount();

    /**
     * 增加喜欢人数统计
     *
     * @param likeCount 要增加的喜欢人数
     */
    void addOneLikeCount(int likeCount);
}