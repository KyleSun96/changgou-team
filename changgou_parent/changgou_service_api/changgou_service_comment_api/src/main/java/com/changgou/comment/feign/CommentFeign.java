package com.changgou.comment.feign;

import com.changgou.comment.pojo.Comment;
import com.changgou.comment.pojo.CommentCount;
import com.changgou.comment.pojo.CommentInfo;
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

    // 根据spuId查询评论数量
    @GetMapping("/getCommentCountBySpuId/{spuId}")
    public Result<CommentCount> getCommentCountBySpuId(@PathVariable("spuId") String spuId);

    // 根据skuId查询评论列表
    @GetMapping("/findBySkuId/{skuId}")
    public Result<List<Comment>> findBySkuId(@PathVariable("skuId") String skuId);

   // 根据spuId查询评论集合
    @GetMapping("/getCommentInfoList/{spuId}/{level}")
    public Result<List<CommentInfo>> getCommentInfoList(@PathVariable("spuId") String spuId, @PathVariable("level") String level);
}
