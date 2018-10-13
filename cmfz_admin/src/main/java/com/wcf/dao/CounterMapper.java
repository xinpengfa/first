package com.wcf.dao;

import com.wcf.entity.Counter;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CounterMapper {


    int deleteByPrimaryKey(String id);

    int insert(Counter record);

    int insertSelective(Counter record);


    Counter selectByPrimaryKey(String id);



    int updateByPrimaryKeySelective(Counter record);

    int updateByPrimaryKey(Counter record);
}