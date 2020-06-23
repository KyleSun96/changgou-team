package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.config.TokenDecode;
import com.changgou.goods.pojo.Favorites;
import com.changgou.goods.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private FavoritesService favoritesService;


    //新增收藏
    @PostMapping("/add")
    public Result add(@RequestBody Favorites favorites) {
        try {
            String username = tokenDecode.getUserInfo().get("username");
            favorites.setUsername(username);
            favoritesService.add(favorites);
            return new Result(true, StatusCode.OK, "收藏成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "收藏失败,请稍后重试!");
        }
    }


    //删除收藏
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        favoritesService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除收藏成功");
    }


    //根据用户名查询收藏列表
    @GetMapping("/list")
    public Result<List<Favorites>> list() {
        String username = tokenDecode.getUserInfo().get("username");
        List<Favorites> favoritesList = favoritesService.findByUsername(username);
        return new Result(true, StatusCode.OK, "查询收藏成功", favoritesList);
    }
}
