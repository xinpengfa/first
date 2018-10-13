package com.wcf.controller;

import com.wcf.entity.Statistic;
import com.wcf.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/activeUser")
    public Map<String, Object> activeUser() {
        Map<String, Object> dates = userService.queryDate();
        return dates;
    }

    @ResponseBody
    @RequestMapping("/distribution1")
    public List<Statistic> distribution1() {
        List<Statistic> data = userService.queryNumbers("男");
        return data;
    }// distribution1--男

    @ResponseBody
    @RequestMapping("/distribution2")
    public List<Statistic> distribution2() {
        List<Statistic> data = userService.queryNumbers("女");
        return data;
    }// distribution2--女

}
