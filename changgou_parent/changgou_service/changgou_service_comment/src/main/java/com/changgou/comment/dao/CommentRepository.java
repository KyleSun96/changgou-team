package com.changgou.comment.dao;

import com.changgou.comment.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Program: changgou_parent
 * @InterfaceName: CommentRepository
 * @Description: MongoRepository<指定实体类, 主键类型>
 * @Author: KyleSun
 **/
public interface CommentRepository extends MongoRepository<Comment, String> {

    // 查询spu所有评价数量
    Long countAllBySpuId(String spuId);

    // 查询spu分级评价数量
    Long countAllBySpuIdAndLevel(String spuId, String level);

    // 根据spuId查询评论列表
    List<Comment> findBySpuId(String spuId);

    // 根据skuId查询评论列表
    List<Comment> findBySkuId(String skuId);

    // 查询分级评价列表
    List<Comment> findBySpuIdAndLevel(String spuId, String level);

}
