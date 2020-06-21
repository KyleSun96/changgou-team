package com.changgou.comment;

import com.changgou.comment.dao.CommentRepository;
import com.changgou.comment.pojo.Comment;
import com.changgou.comment.pojo.CommentInfo;
import com.changgou.comment.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试api
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentService commentService;


    @Test
    public void add() {

        Comment comment = new Comment();
        comment.setUsername("张三");
        comment.setOrderId("006");
        comment.setSpuId("001");
        comment.setSkuId("002");
        comment.setContent("很好");
        comment.setLevel("high");
        commentRepository.save(comment);

    }

    @Test
    public void findBySpuId() {
        List<Comment> commentList = commentRepository.findBySpuId("002");
        System.out.println(commentList);
    }

    @Test
    public void findBySpuIdAndLevel() {
        List<Comment> commentList = commentRepository.findBySpuIdAndLevel("001", "high");
        System.out.println(commentList);
    }

    @Test
    public void count() {
        Long high = commentRepository.countAllBySpuIdAndLevel("001", "high");
        System.out.println(high);
    }

    @Test
    public void getCommentInfoList() {
        List<CommentInfo> commentInfoList = commentService.getCommentInfoList("001", "high");
        System.out.println(commentInfoList);
    }

    // 测试失败
    @Test
    public void groupBySpuId() {
        Aggregation agg = Aggregation.newAggregation(Aggregation.group("spuId"));
        AggregationResults<String> aggregationResults = mongoTemplate.aggregate(agg, "comment", String.class);
        List<String> countList = aggregationResults.getMappedResults();
        System.out.println(countList);
    }
}
