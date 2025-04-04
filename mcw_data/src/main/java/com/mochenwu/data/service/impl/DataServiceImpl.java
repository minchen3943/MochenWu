package com.mochenwu.data.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mochenwu.data.mapper.DataMapper;
import com.mochenwu.data.model.McwData;
import com.mochenwu.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 瞑尘
 **/
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, McwData> implements DataService {

    private final DataMapper dataMapper;

    @Autowired
    public DataServiceImpl(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    @Override
    public int addVisitorCount() {
        int visitorCount = dataMapper.selectVisitorCount();
        dataMapper.addOneVisitorCount(visitorCount + 1);
        return dataMapper.selectVisitorCount();
    }

    @Override
    public int getVisitorCount() {
        return dataMapper.selectVisitorCount();
    }

    @Override
    public Integer addLikeCount() {
        int likeCount = dataMapper.selectLikeCount();
        dataMapper.addOneLikeCount(likeCount + 1);
        return dataMapper.selectLikeCount();
    }

    @Override
    public Integer getLikeCount() {
        return dataMapper.selectLikeCount();
    }




}
