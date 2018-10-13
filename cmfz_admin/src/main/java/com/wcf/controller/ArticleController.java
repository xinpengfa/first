package com.wcf.controller;
import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wcf.entity.Article;
import com.wcf.service.ArticleService;
import com.wcf.vo.Result;
import com.wcf.vo.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("article")
@Slf4j
public class ArticleController {
    @Autowired
    private FastFileStorageClient storageClient;
    @Resource
    private ArticleService articleService;

    @RequestMapping("upload")
    public UploadResult upload(MultipartFile[] keyName, HttpServletRequest request){
        UploadResult uploadResult = new UploadResult();
        try {
            ArrayList<String> data = new ArrayList<String>();
            HashSet<MataData> mataData = new HashSet<MataData>();
            mataData.add(new MataData("width", "1024"));
            // MultipartFile对象代表的是一个上传的文件
            for (MultipartFile file : keyName) {
                InputStream inputStream = file.getInputStream();
                String originalFilename = file.getOriginalFilename();
                String[] split = originalFilename.split("\\.");
                String suffix = split[split.length - 1];
                // 调用fastdfs分布式文件系统 上传文件的api 将图片上传到文件系统

                StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(), suffix, mataData);
                // file_id  group+path
                // http://192.168.128.134:8888/group/path

                /*
                //上传到本地
                String path = request.getSession().getServletContext().getRealPath("/") + "upload/" + file.getOriginalFilename();
                file.transferTo(new File(path));
                data.add(request.getContextPath() + "/upload/" + file.getOriginalFilename());*/
            }
            uploadResult.setErrno(0);
            uploadResult.setData(data);
        } catch (Exception e) {
            uploadResult.setErrno(1);
            e.printStackTrace();
        }
        return uploadResult;
    }

    @RequestMapping("add")
    @ResponseBody
    public Result add(Article article){
        Result result = new Result();
        try {
            if(article.getStatus()==null){
                article.setStatus("下架");
            }
            articleService.add(article);
            result.setSuccess(true);
            result.setMessage("添加成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未添加");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/queryByPage")
    @ResponseBody
    public Map<String, Object> queryByPages(Integer page, Integer rows) {
        log.info("userController======当前页:" + page + ", 每页显示的条数: " + rows);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Article> articles = articleService.queryByPage(page, rows);
        Long totals = articleService.queryCounts();
        map.put("total", totals);
        map.put("rows", articles);
        return map;
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Article queryOne(String id) {
        return articleService.queryOne(id);
    }
}
