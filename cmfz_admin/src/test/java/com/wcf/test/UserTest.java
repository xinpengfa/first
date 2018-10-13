package com.wcf.test;

import com.wcf.entity.User;
import com.wcf.service.PictureService;
import com.wcf.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

        @Resource
        private UserService userService;
    @Test
    public void findByPage(){
        List<User> users = userService.queryByPage(2,3);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
