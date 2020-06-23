package com.changgou.goods.service;

import com.changgou.goods.pojo.Favorites;

import java.util.List;

public interface FavoritesService {

    //新增收藏
    void add(Favorites favorites);

    //删除收藏
    void deleteById(Integer id);

    //根据用户名查询收藏列表
    List<Favorites> findByUsername(String username);
}
