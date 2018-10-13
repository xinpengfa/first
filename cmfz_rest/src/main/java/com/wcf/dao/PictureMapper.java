package com.wcf.dao;

import com.wcf.entity.Picture;

import java.util.List;

public interface PictureMapper {
    void delete(String id);
    void insert(Picture picture);
    Picture findOne(String id);
    List<Picture> findAll(Integer page, Integer size);
    Long findCounts();
    void update(Picture picture);
}