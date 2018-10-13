package com.wcf.dao;

import com.wcf.entity.Menu;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    List<Menu> findAll();
}