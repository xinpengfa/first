package com.wcf.controller;

import com.wcf.entity.Menu;
import com.wcf.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping("findAll")
    @ResponseBody
    public List<Menu> findAll(){
        List<Menu> menus = menuService.queryAll();
        /*for (Menu menu : menus) {
            for (Menu menu1 : menu.getChildren()) {
                System.out.println(menu1);
            }
        }*/
        return menus;
    }


}
