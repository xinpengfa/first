package com.wcf.dao;

import com.wcf.entity.Manager;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

public interface ManagerMapper {


    void delete(String id);

    void insert(Manager record);

    Manager findOne(String name);

    void update(Manager record);

    List<Manager> findAll();
}