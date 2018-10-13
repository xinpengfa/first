package com.wcf.dao;

import java.util.List;

public interface BaseDAO<T> {

    void insert(T t);

    void update(T t);

    void delete(Integer id);

    void deleteBatch(List<Integer> ids);

    T findOne(Integer id);

    List<T> findByPage(Integer page, Integer rows);

    List<T> findAll();

    Long findTotals();
}
