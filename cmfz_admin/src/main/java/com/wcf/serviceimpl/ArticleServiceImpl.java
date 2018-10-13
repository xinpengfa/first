package com.wcf.serviceimpl;

import com.wcf.dao.ArticleMapper;
import com.wcf.entity.Article;
import com.wcf.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString().replace("-",""));
        articleMapper.insert(article);
    }

    @Override
    public List<Article> queryAll() {
        List<Article> articles = articleMapper.findAll();
        return articles;
    }

    @Override
    public List<Article> queryByPage(Integer page,Integer size) {
        List<Article> articles = articleMapper.findByPage((page-1)*size,size);
        return articles;
    }

    @Override
    public Article queryOne(String id) {
        Article article = articleMapper.findOne(id);
        return article;
    }

    @Override
    public Long queryCounts() {
        Long counts = articleMapper.findCounts();
        return counts;
    }
}
