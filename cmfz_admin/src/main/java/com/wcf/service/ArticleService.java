package com.wcf.service;

import com.wcf.entity.Article;

import java.util.List;

public interface ArticleService {
    void add(Article article);
    List<Article> queryAll();
    List<Article> queryByPage(Integer page,Integer size);
    Article queryOne(String id);
    //    void update(Article article);
    Long queryCounts();
}
