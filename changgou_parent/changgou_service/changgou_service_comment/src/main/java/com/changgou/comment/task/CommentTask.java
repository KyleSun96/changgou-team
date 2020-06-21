package com.changgou.comment.task;

import com.changgou.comment.dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Program: changgou_parent
 * @ClassName: CommentTask
 * @Description: 用户评论定时扫描
 * @Author: KyleSun
 **/
@Component
public class CommentTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    // 两分钟更新一次
    @Scheduled(cron = "0 0/2 * * * ?")
    public void updateCommentCount() {

        // 更新获取spu最新评价数量
        Aggregation agg = Aggregation.newAggregation(Aggregation.group("spuId"));
        AggregationResults<String> aggregationResults = mongoTemplate.aggregate(agg, "comment", String.class);

        List<String> spuIdList = aggregationResults.getMappedResults();

        if (spuIdList.size() <= 0) {
            throw new RuntimeException("当前商品无评价信息");
        }

        spuIdList.stream().forEach((spuId) -> {
            // 根据spuId查询评论数量
            Long count = commentRepository.countAllBySpuId(spuId);

            // 存入redis
            redisTemplate.opsForValue().set(spuId, count);

        });
    }


}
