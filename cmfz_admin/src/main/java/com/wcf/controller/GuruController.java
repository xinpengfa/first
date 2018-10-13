package com.wcf.controller;
import com.wcf.entity.Guru;
import com.wcf.service.GuruService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

    @Controller
    @RequestMapping("/guru")
    public class GuruController {

        @Resource
        private GuruService guruService;

        @RequestMapping("/queryAll")
        @ResponseBody
        public List<Guru> queryAll() {
            return guruService.queryAll();
        }
    }

