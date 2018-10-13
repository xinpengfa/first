package com.wcf.controller;
import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wcf.entity.Picture;
import com.wcf.service.PictureService;
import com.wcf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("picture")
public class PictureController {
    @Resource
    private PictureService pictureService;
    @Autowired
    private FastFileStorageClient storageClient;
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer rows){
        System.out.println(page);
        System.out.println(rows);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Picture> pictures = pictureService.queryAll(page,rows);
        map.put("rows",pictures);
        map.put("total",pictureService.findCounts());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        return map;
    }

    @RequestMapping("findOne")
    @ResponseBody
    public Picture findOne(String id){
        Picture picture = pictureService.queryOne(id);
        return picture;
    }

    @RequestMapping("add")
    @ResponseBody
    public Result add(Picture picture, MultipartFile aa, HttpServletRequest request){
        System.out.println(picture);
        System.out.println(aa);
        String originalFilename = aa.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String suffix = split[split.length - 1];
        HashSet<MataData> mataData = new HashSet<MataData>();
        mataData.add(new MataData("width", "1024"));
        picture.setCreateDate(new Date());
        Result result = new Result();
        try {
            //上传到本地的方法
            //String realPath = request.getSession().getServletContext().getRealPath("/picture");
            //aa.transferTo(new File(realPath,aa.getOriginalFilename()));

            //上传到fdfs
            InputStream inputStream = aa.getInputStream();
            /*
            * inputStream 该文件的输出流
            * aa.getSize() 文件大小
            * suffix 文件的后缀
            * mataData 元数据
            * */
            StorePath storePath = storageClient.uploadFile(inputStream, aa.getSize(), suffix, mataData);
            //fixme 在fdfs中如何通过指令查文件
            //cd /data/fastdfs/storage/store/data/00/00
            //path M00/00/00/wKgdhVuYQceALR5GAAJN41YY4is682.jpg
            String path = storePath.getPath();
            String group = storePath.getGroup();
            picture.setImg_path(group+"//"+path);
            pictureService.add(picture);
            result.setSuccess(true);
            result.setMessage("添加成功");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败");
            e.printStackTrace();
            return result;
        }
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(Picture picture){
        Result result = new Result();
        try {
            pictureService.modify(picture);
            result.setSuccess(true);
            result.setMessage("修改成功");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改失败");
            e.printStackTrace();
            return result;
        }
    }

    @RequestMapping("download")
    public void download(String id, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.queryOne(id);
        String img_path = picture.getImg_path();
        System.out.println(img_path);
        String[] split = img_path.split("_");
        System.out.println(split[0]+"and"+split[1]);
        byte[] bytes = storageClient.downloadFile(split[0], split[1],new DownloadByteArray());
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + split[split.length-1]);
        ServletOutputStream outputStream = response.getOutputStream();
        //fixme 为什么不用关流
        outputStream.write(bytes);
    }

}
