package com.wcf.service;

import com.wcf.entity.Statistic;
import com.wcf.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> queryByPage(Integer page, Integer size);
    void add(User user);
    Long findCounts();
    List<User> queryAll();
    List<User> queryByColumns(String columns);
    List<Statistic> queryNumbers(String sex);
    Map<String,Object> queryDate();
}
