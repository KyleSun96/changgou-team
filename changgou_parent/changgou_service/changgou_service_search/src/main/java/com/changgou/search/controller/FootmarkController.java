package com.changgou.search.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.feign.FootmarkFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Footmark;
import com.changgou.goods.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/wfootmark")
public class FootmarkController {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private FootmarkFeign footmarkFeign;

    @PostMapping("/add/{skuId}")
    public Result add(@PathVariable("skuId") String skuId) {
        Sku sku = skuFeign.findById(skuId).getData();
        //封装footmark
        Footmark footmark = new Footmark();
        footmark.setCreateTime(new Date());
        footmark.setImage(sku.getImage());
        footmark.setName(sku.getName());
        footmark.setPrice(sku.getPrice());
        footmark.setSkuId(skuId);
        footmark.setSpec(sku.getSpec());
        footmark.setSpuId(sku.getSpuId());

        footmarkFeign.add(footmark);
        return new Result(true, StatusCode.OK, "新增足迹成功");
    }
}
