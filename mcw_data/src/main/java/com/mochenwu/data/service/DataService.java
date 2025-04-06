package com.mochenwu.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.data.model.McwData;

/**
 * 数据服务接口，提供访问者计数和点赞计数的管理功能。
 * <p>
 * 该接口继承自 MyBatis-Plus 的 IService 接口，针对 McwData 实体进行操作。
 * </p>
 *
 * @author 瞑尘
 * @date 2025/04/06
 */
public interface DataService extends IService<McwData> {

    /**
     * 增加访问者计数。
     *
     * @return 当前的访问者总数
     */
    int addVisitorCount();

    /**
     * 获取当前的访问者计数。
     *
     * @return 当前的访问者总数
     */
    int getVisitorCount();

    /**
     * 获取当前的点赞数。
     *
     * @return 当前的点赞总数
     */
    Integer getLikeCount();

    /**
     * 增加点赞数。
     *
     * @return 当前的点赞总数
     */
    Integer addLikeCount();
}
