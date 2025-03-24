package com.mochenwu.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mochenwu.data.model.McwData;

/**
 * @author 32035
 */
public interface DataService extends IService<McwData> {
    int addVisitorCount();
    int getVisitorCount();

    Integer getLikeCount();

    Integer addLikeCount();
}
