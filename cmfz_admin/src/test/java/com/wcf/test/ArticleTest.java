package com.wcf.test;

import com.wcf.entity.Article;
import com.wcf.entity.Guru;
import com.wcf.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleTest {
    @Resource
    private ArticleService articleService;
    @Test
    public void queryAll(){
        List<Article> articles = articleService.queryAll();
        System.out.println(articles);
    }

    @Test
    public void add(){
        articleService.add(new Article(null,"1",null,"diyizhong","111",new Guru(),"1111","shangjia"));
    }

}
