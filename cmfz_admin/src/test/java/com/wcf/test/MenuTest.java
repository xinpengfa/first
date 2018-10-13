package com.wcf.test;

import com.wcf.entity.Manager;
import com.wcf.entity.Menu;
import com.wcf.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuTest {
    @Resource
    private MenuService menuService;
    @Test
    public void findAll(){
        List<Menu> menus = menuService.queryAll();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }
}
