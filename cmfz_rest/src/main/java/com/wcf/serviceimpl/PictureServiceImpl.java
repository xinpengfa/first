package com.wcf.serviceimpl;

import com.wcf.dao.PictureMapper;
import com.wcf.entity.Picture;
import com.wcf.service.PictureService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
@Service("pictureService")
@Transactional
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureMapper pictureMapper;
    @Override
    public void add(Picture picture) {
        picture.setId(UUID.randomUUID().toString().replace("-",""));
        pictureMapper.insert(picture);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Picture> queryAll(Integer page,Integer size) {
        System.out.println(page);
        System.out.println(size);
        List<Picture> pictures = pictureMapper.findAll((page-1)*size,size);
        return pictures;
    }

    @Override
    public void modify(Picture picture) {
        pictureMapper.update(picture);
    }

    @Override
    public Long findCounts() {
        Long counts = pictureMapper.findCounts();
        return counts;
    }

    @Override
    public Picture queryOne(String id) {
        Picture picture = pictureMapper.findOne(id);
        return picture;
    }
}
