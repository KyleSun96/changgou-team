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

    /*@Autowired
    private CommentFeign commentFeign;*/

    @GetMapping("/toItem/{spuId}")
    public String toGoodsPage(@PathVariable("spuId") String spuId, Model model) {
        Map<String, Object> resultMap = pageService.getItemData(spuId);

        // 测试
        System.out.println("商品的spuId为：" + spuId);

        /*CommentCount commentCount = commentFeign.getCommentCountBySpuId(spuId).getData();

        List<CommentInfo> commentInfoList = commentFeign.getCommentInfoList(spuId, "all").getData();

        if (commentCount != null){
            model.addAttribute("commentCount",commentCount);
        }

        if (commentInfoList!=null){
            model.addAttribute("commentInfoList",commentInfoList);
        }*/

        model.addAttribute("spuId", spuId);
        model.addAllAttributes(resultMap);


        return "item";
    }


}
