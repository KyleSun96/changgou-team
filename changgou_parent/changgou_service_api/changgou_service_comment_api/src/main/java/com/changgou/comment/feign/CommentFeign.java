package com.changgou.comment.feign;

import com.changgou.comment.pojo.Comment;
import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Program: changgou_parent
 * @InterfaceName: CommentFeign
 * @Description:
 * @Author: KyleSun
 **/
@FeignClient(name = "comment")
@RequestMapping("/comment")
public interface CommentFeign {

    // 添加评论
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment);


    // 根据skuId查询评论列表
    @GetMapping("/findBySkuId/{skuId}")
    public Result<List<Comment>> findBySkuId(@PathVariable("skuId") String skuId);

}
