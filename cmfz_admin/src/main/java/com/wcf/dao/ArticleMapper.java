package com.wcf.dao;

import com.wcf.entity.Article;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    void insert(Article article);
    List<Article> findAll();
    List<Article> findByPage(Integer page,Integer size);
    Article findOne(String id);
//    void update(Article article);
    Long findCounts();
}