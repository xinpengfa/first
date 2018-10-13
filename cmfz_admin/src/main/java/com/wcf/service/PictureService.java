package com.wcf.service;

import com.wcf.entity.Picture;

import java.util.List;

public interface PictureService {
    void add(Picture picture);
    List<Picture> queryAll(Integer page,Integer size);
    void modify(Picture picture);
    Long findCounts();
    Picture queryOne(String id);
}
