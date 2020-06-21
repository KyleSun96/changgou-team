package com.changgou.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.comment.dao.CommentRepository;
import com.changgou.comment.pojo.Comment;
import com.changgou.comment.pojo.CommentCount;
import com.changgou.comment.pojo.CommentInfo;
import com.changgou.comment.service.CommentService;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.User;
import com.changgou.util.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Program: changgou_parent
 * @ClassName: CommentServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CommentRepository commentRepository;


    /**
     * @description: //TODO 添加评论
     * @param: [comment]
     * @return: void
     */
    @Override
    @Transactional
    public void add(Comment comment) {

        long nextId = idWorker.nextId();
        comment.setId(String.valueOf(nextId));

        commentRepository.save(comment);

        // 发送订单id通知
    }


    /**
     * @description: //TODO 根据spuId查询评论数量
     * @param: [spuId]
     * @return: com.changgou.comment.pojo.CommentCount
     */
    @Override
    public CommentCount getCommentCountBySpuId(String spuId) {

        CommentCount commentCount = new CommentCount();

        // 全部评价量
        Long allCount = commentRepository.countAllBySpuId(spuId);
        commentCount.setAllComment(allCount);

        // 所有好评量
        Long highCount = commentRepository.countAllBySpuIdAndLevel(spuId, "high");
        commentCount.setHighComment(highCount);

        // 所有中评量
        Long mediumCount = commentRepository.countAllBySpuIdAndLevel(spuId, "medium");
        commentCount.setMediumComment(mediumCount);

        // 所有差评量
        Long lowCount = commentRepository.countAllBySpuIdAndLevel(spuId, "low");
        commentCount.setLowComment(lowCount);

        return commentCount;

    }


    /**
     * @description: //TODO 根据spuId查询评论集合（用于商品详情页面评价展示）
     * @param: [spuId, level]
     * @return: java.util.List<com.changgou.comment.pojo.CommentInfo>
     */
    @Override
    public List<CommentInfo> getCommentInfoList(String spuId, String level) {

        List<CommentInfo> commentInfoList = new ArrayList<>();

        List<Comment> commentList = new ArrayList<>();

        if ("all".equals(level)) {
            // 选择了所有评价
            commentList = commentRepository.findBySpuId(spuId);
        } else {
            // 选择了好评，中评，差评
            commentList = commentRepository.findBySpuIdAndLevel(spuId, level);
        }

        // 如果没有评价则返回空集合
        if (commentList == null || commentList.size() <= 0) {
            return commentInfoList;
        }

        // 如果有评价则封装传送到前台的对象commentInfoList
        for (Comment comment : commentList) {

            CommentInfo commentInfo = new CommentInfo();

            // 封装数据
            commentInfo.setUsername(comment.getUsername());
            commentInfo.setContent(comment.getContent());
            commentInfo.setLevel(comment.getLevel());

            // 封装当前sku的规格信息，使用其value
            List<String> specList = new ArrayList<>();
            Sku sku = skuFeign.findById(comment.getSkuId()).getData();

            // 规格为空，传入空集合
            if (StringUtils.isEmpty(sku.getSpec())) {
                commentInfo.setSpecList(new ArrayList<>());
            }

            Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for (String value : specMap.values()) {
                specList.add(value);
            }
            commentInfo.setSpecList(specList);

            // 封装用户头像信息
            User user = userFeign.findUserInfo(comment.getUsername());
            commentInfo.setUserPhoto(user.getHeadPic());

            commentInfoList.add(commentInfo);
        }
        return commentInfoList;
    }


    /**
     * @description: //TODO 根据skuId查询评论列表
     * @param: [skuId]
     * @return: java.util.List<com.changgou.comment.pojo.Comment>
     */
    @Override
    public List<Comment> findBySkuId(String skuId) {
        List<Comment> commentList = commentRepository.findBySkuId(skuId);
        return commentList;
    }

}