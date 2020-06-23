package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Footmark;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "goods")
public interface FootmarkFeign {

    //新增足迹
    @PostMapping("/footmark/add")
    public Result add(@RequestBody Footmark footmark);


    //查询足迹列表
    @GetMapping("/footmark/list")
    public Result<List<Footmark>> list();
}
