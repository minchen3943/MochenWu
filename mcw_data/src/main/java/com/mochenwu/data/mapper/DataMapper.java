package com.mochenwu.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mochenwu.data.model.McwData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 32035
 */
@Mapper
public interface DataMapper extends BaseMapper<McwData> {

    void addOneVisitorCount(int count);

    int selectVisitorCount();
}