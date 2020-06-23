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

import java.text.DecimalFormat;
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

    @ResponseBody
    @GetMapping("/getCommentInfoList/{spuId}/{level}")
    public Result<List<CommentInfo>> getCommentInfoList(@PathVariable("spuId") String spuId, @PathVariable("level") String level) {
        return commentFeign.getCommentInfoList(spuId, level);
    }

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

        // 添加好评概率，好评率 = (好评 + 中评 )/全部评价
        long l1 = commentCount.getHighComment() + commentCount.getMediumComment();
        long l2 = commentCount.getAllComment();
        double d1 = l1 / (l2 * (1.0)) * 100;

        DecimalFormat df = new DecimalFormat("0.00");
        String rate = df.format(d1);
        System.out.println(rate);

        model.addAttribute("rate", rate);
        System.out.println(rate);

        return "item";
    }


}
