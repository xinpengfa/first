package com.wcf.test;

import com.wcf.entity.Picture;
import com.wcf.service.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PictrueTest {
    @Resource
    private PictureService pictureService;
   /* @Test
    public void findAll(){
        List<Picture> pictures = pictureService.queryAll();
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }*/

    @Test
    public void findAll(){
        List<Picture> pictures = pictureService.queryAll(1,2);
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }

    @Test
    public void findOne(){
        Picture picture = pictureService.queryOne("1");
        System.out.println(picture);

    }

   /* @Test
    public void add(){
        Picture picture = new Picture("6", "xiaowang", "6.jpg", new Date(), "");
        pictureService.add(picture);
    }

    @Test
    public void update(){
        Picture picture = new Picture("6", "xiaowangeight", "6.jpg", new Date(), "");
        pictureService.modify(picture);
    }*/
}
