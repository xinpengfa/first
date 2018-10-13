package com.wcf.dao;

import com.wcf.entity.Statistic;
import com.wcf.entity.User;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    List<User> findByPage(Integer page, Integer size);
    void insert(User user);
    Long findCounts();
    List<User> findAll();
    List<User> findByColumns(@Param("columns") String columns);
    List<Statistic>  findNumbers(String sex);
    List<Long> findDays();
}