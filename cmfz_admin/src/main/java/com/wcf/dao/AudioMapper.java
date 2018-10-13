package com.wcf.dao;

import com.wcf.entity.Audio;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AudioMapper {


    int deleteByPrimaryKey(String id);

    int insert(Audio record);

    int insertSelective(Audio record);


    Audio selectByPrimaryKey(String id);



    int updateByPrimaryKeySelective(Audio record);

    int updateByPrimaryKey(Audio record);
}