package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.config.TokenDecode;
import com.changgou.goods.pojo.Footmark;
import com.changgou.goods.service.FootmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/footmark")
public class FootmarkController {

    @Autowired
    private FootmarkService footmarkService;

    @Autowired
    private TokenDecode tokenDecode;


    //新增足迹
    @PostMapping("/add")
    public Result add(@RequestBody Footmark footmark) {
        String username = tokenDecode.getUserInfo().get("username");
        footmark.setUsername(username);
        footmarkService.add(footmark);
        return new Result(true, StatusCode.OK, "足迹新增成功");
    }


    //查询足迹列表
    @GetMapping("/list")
    public Result<List<Footmark>> list() {
        String username = tokenDecode.getUserInfo().get("username");
        List<Footmark> footmarkList = footmarkService.findByUsername(username);
        return new Result(true, StatusCode.OK, "查询足迹成功", footmarkList);
    }
}
