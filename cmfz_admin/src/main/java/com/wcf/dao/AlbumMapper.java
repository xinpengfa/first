package com.wcf.dao;

import com.wcf.entity.Album;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlbumMapper {


    void delete(String id);

    void insert(Album album);

    Album findOne(String id);

    void update(Album album);

    List<Album> findAll();
}