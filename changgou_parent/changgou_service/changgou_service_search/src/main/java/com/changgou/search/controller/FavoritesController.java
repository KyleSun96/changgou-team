package com.changgou.search.controller;

import com.changgou.entity.Result;
import com.changgou.goods.feign.FavoritesFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Favorites;
import com.changgou.goods.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wfavorites")
public class FavoritesController {

    @Autowired
    private FavoritesFeign favoritesFeign;
    @Autowired
    private SkuFeign skuFeign;

    //新增收藏
    @GetMapping("/add/{skuId}")
    public Result add(@PathVariable("skuId") String skuId) {
        Sku sku = skuFeign.findById(skuId).getData();
        Favorites favorites = new Favorites();
        //封装favorites
        favorites.setSkuId(skuId);
        favorites.setAlertNum(sku.getAlertNum());
        favorites.setBrandName(sku.getBrandName());
        favorites.setCategoryId(sku.getCategoryId());
        favorites.setCategoryName(sku.getCategoryName());
        favorites.setCommentNum(sku.getCommentNum());
        favorites.setCreateTime(new Date());
        favorites.setImage(sku.getImage());
        favorites.setImages(sku.getImages());
        favorites.setName(sku.getName());
        favorites.setNum(sku.getNum());
        favorites.setPrice(sku.getPrice());
        favorites.setSaleNum(sku.getSaleNum());
        favorites.setSn(sku.getSn());
        favorites.setSpec(sku.getSpec());
        favorites.setSpuId(sku.getSpuId());
        favorites.setStatus(sku.getStatus());
        favorites.setWeight(sku.getWeight());
        return favoritesFeign.add(favorites);
    }


    //删除收藏
    @DeleteMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        return favoritesFeign.deleteById(id);
    }


    //查询收藏列表
    @GetMapping("/list")
    public Result<List<Favorites>> list() {
        return favoritesFeign.list();
    }
}
