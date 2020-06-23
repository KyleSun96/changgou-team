package com.changgou.goods.service.impl;

import com.changgou.goods.dao.FavoritesMapper;
import com.changgou.goods.pojo.Favorites;
import com.changgou.goods.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesMapper favoritesMapper;

    //新增收藏
    @Override
    public void add(Favorites favorites) {
        //根据skuId查询客户是否是重复收藏
        Favorites favor = new Favorites();
        favor.setSkuId(favorites.getSkuId());
        List<Favorites> favoritesList = favoritesMapper.select(favor);
        if (favoritesList != null && favoritesList.size() > 0){
            //该商品用户已收藏过
            throw new RuntimeException("该商品已收藏,请不要重复收藏");
        }
        favoritesMapper.insert(favorites);
    }


    //删除收藏
    @Override
    public void deleteById(Integer id) {
        favoritesMapper.deleteByPrimaryKey(id);
    }


    //根据用户名查询收藏列表
    @Override
    public List<Favorites> findByUsername(String username) {
        Favorites favorites = new Favorites();
        favorites.setUsername(username);
        return favoritesMapper.select(favorites);
    }
}
