package com.mochenwu.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mochenwu.data.mapper.DataMapper;
import com.mochenwu.data.model.McwData;
import com.mochenwu.data.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据统计服务实现类
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, McwData> implements DataService {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);


    /**
     * 数据统计Mapper接口
     */
    private final DataMapper dataMapper;

    /**
     * 构造方法注入Mapper
     *
     * @param dataMapper 数据统计Mapper
     */
    @Autowired
    public DataServiceImpl(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    /**
     * 增加访问人数并返回最新值
     *
     * @return 最新访问人数
     */
    @Override
    public int addVisitorCount() {
        int visitorCount = dataMapper.selectVisitorCount();
        dataMapper.addOneVisitorCount(visitorCount + 1);
        logger.info("增加访问人数，更新后为 {}", visitorCount);
        return dataMapper.selectVisitorCount();
    }

    /**
     * 获取当前访问人数
     *
     * @return 当前访问人数
     */
    @Override
    public int getVisitorCount() {
        logger.info("获取访问人数: {}", dataMapper.selectVisitorCount());
        return dataMapper.selectVisitorCount();
    }

    /**
     * 增加喜欢人数并返回最新值
     *
     * @return 最新喜欢人数
     */
    @Override
    public Integer addLikeCount() {
        int likeCount = dataMapper.selectLikeCount();
        dataMapper.addOneLikeCount(likeCount + 1);
        logger.info("增加喜欢人数，更新后为: {}", likeCount);
        return dataMapper.selectLikeCount();
    }

    /**
     * 获取当前喜欢人数
     *
     * @return 当前喜欢人数
     */
    @Override
    public Integer getLikeCount() {
        logger.info("获取喜欢人数: {}", dataMapper.selectLikeCount());
        return dataMapper.selectLikeCount();
    }
}