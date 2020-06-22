package com.changgou.page.controller;

import com.changgou.comment.feign.CommentFeign;
import com.changgou.comment.pojo.CommentCount;
import com.changgou.comment.pojo.CommentInfo;
import com.changgou.entity.Result;
import com.changgou.page.service.impl.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.Map;

/**
 * @Program: changgou_parent
 * @ClassName: PageController
 * @Description:
 * @Author: KyleSun
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageServiceImpl pageService;

    @Autowired
    private CommentFeign commentFeign;

    @GetMapping("/toItem/{spuId}")
    public String toGoodsPage(@PathVariable("spuId") String spuId, Model model) {
        Map<String, Object> resultMap = pageService.getItemData(spuId);

        model.addAttribute("spuId", spuId);
        model.addAllAttributes(resultMap);

        // 测试
        System.out.println("商品的spuId为：" + spuId);

        // 返回商品数量数据
        CommentCount commentCount = commentFeign.getCommentCountBySpuId(spuId).getData();
        model.addAttribute("commentCount", commentCount);

        //返回商品分级评论
//        List<CommentInfo> commentInfoList = commentFeign.getCommentInfoList(spuId, "all").getData();
//        List<CommentInfo> highCommentInfoList = commentFeign.getCommentInfoList(spuId, "high").getData();
//        List<CommentInfo> mediumCommentInfoList = commentFeign.getCommentInfoList(spuId, "medium").getData();
//        List<CommentInfo> lowCommentInfoList = commentFeign.getCommentInfoList(spuId, "low").getData();
//
//        model.addAttribute("commentInfoList", commentInfoList);
//        model.addAttribute("highCommentInfoList", highCommentInfoList);
//        model.addAttribute("mediumCommentInfoList", mediumCommentInfoList);
//        model.addAttribute("lowCommentInfoList", lowCommentInfoList);

        return "item";
    }


}
