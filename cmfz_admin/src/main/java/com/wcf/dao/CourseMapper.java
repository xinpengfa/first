package com.wcf.dao;

import com.wcf.entity.Course;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper {


    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);


    Course selectByPrimaryKey(String id);



    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}