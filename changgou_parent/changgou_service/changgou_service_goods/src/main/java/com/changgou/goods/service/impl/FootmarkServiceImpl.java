package com.changgou.goods.service.impl;

import com.changgou.goods.dao.FootmarkMapper;
import com.changgou.goods.pojo.Footmark;
import com.changgou.goods.service.FootmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootmarkServiceImpl implements FootmarkService {

    @Autowired
    private FootmarkMapper footmarkMapper;


    //新增足迹
    @Override
    public void add(Footmark footmark) {
        //判断对应的数据足迹表中是否已添加
        Footmark foot = new Footmark();
        foot.setSkuId(footmark.getSkuId());
        List<Footmark> footmarkList = footmarkMapper.select(foot);
        if (footmarkList != null && footmarkList.size() > 0) {
            return;
        }
        footmarkMapper.insertSelective(footmark);
    }


    //查询足迹列表
    @Override
    public List<Footmark> findByUsername(String username) {
        Footmark footmark = new Footmark();
        footmark.setUsername(username);
        return footmarkMapper.select(footmark);
    }
}
