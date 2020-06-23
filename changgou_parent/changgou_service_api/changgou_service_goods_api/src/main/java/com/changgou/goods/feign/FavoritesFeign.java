package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Favorites;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "goods")
public interface FavoritesFeign {

    //新增收藏
    @PostMapping("/favorites/add")
    public Result add(@RequestBody Favorites favorites);


    //删除收藏
    @DeleteMapping("/favorites/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id);


    //根据用户名查询收藏列表
    @GetMapping("/favorites/list")
    public Result<List<Favorites>> list();
}
