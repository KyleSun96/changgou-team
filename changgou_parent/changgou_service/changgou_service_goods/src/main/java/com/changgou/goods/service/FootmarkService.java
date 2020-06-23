package com.changgou.goods.service;

import com.changgou.goods.pojo.Footmark;

import java.util.List;

public interface FootmarkService {

    //新增足迹
    void add(Footmark footmark);

    //根据用户名查询足迹列表
    List<Footmark> findByUsername(String username);
}
