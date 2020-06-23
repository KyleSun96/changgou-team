package com.changgou.comment.service;

import com.changgou.comment.pojo.Comment;
import com.changgou.comment.pojo.CommentCount;
import com.changgou.comment.pojo.CommentInfo;

import java.util.List;

/**
 * @Program: changgou_parent
 * @InterfaceName: CommentService
 * @Description:
 * @Author: KyleSun
 **/
public interface CommentService {

    // 添加评论
    void add(Comment comment);

    // 根据spuId查询评论数量
    CommentCount getCommentCountBySpuId(String spuId);

    // 根据spuId查询评论集合（用于商品详情页面评价展示）
    List<CommentInfo> getCommentInfoList(String spuId, String level);

    // 根据skuId查询评论列表
    List<Comment> findBySkuId(String skuId);

}
