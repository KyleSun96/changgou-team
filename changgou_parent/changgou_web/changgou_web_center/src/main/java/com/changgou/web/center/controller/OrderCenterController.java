package com.changgou.web.center.controller;

import com.changgou.entity.Result;
import com.changgou.goods.feign.FavoritesFeign;
import com.changgou.goods.feign.FootmarkFeign;
import com.changgou.goods.pojo.Favorites;
import com.changgou.goods.pojo.Footmark;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.feign.OrderItemFeign;
import com.changgou.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wcenter")
public class OrderCenterController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private OrderItemFeign orderItemFeign;

    @Autowired
    private FavoritesFeign favoritesFeign;

    @Autowired
    private FootmarkFeign footmarkFeign;

    //确定收货
    @RequestMapping("/define")
    public void define() {
        orderFeign.define();
    }

    /**
     * 跳转到待收货页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/order/findPayOrder")
    public String toOrderReceive(Model model) {
        List<Order> orderList = orderFeign.findPayOrder().getData();
        for (Order order : orderList) {
            String name = (String) orderItemFeign.findByOrderId(order.getId()).getData();
            order.setOrderItemName(name);

        }
        model.addAttribute("orderList", orderList);

        return "center-order-receive";

    }

    /**
     * 跳转到待评价页面
     *
     * @return
     */
    @RequestMapping("/order/findBuyerRate")
    public String toOrderEvaluate(Model model) {
        List<Order> orderList = orderFeign.findBuyerRateByOrder().getData();
        for (Order order : orderList) {
            String name = (String) orderItemFeign.findByOrderId(order.getId()).getData();

            order.setOrderItemName(name);

        }
        model.addAttribute("orderList", orderList);

        return "center-order-evaluate";
    }


    //跳转收藏列表页面
    @RequestMapping("/toCollect")
    public String toCollect(Model model) {
        List<Favorites> favoritesList = favoritesFeign.list().getData();
        model.addAttribute("favoritesList", favoritesList);
        return "center-collect";
    }


    //跳转我的足迹页面
    @RequestMapping("/toFootmark")
    public String toFootmark(Model model) {
        List<Footmark> footmarkList = footmarkFeign.list().getData();
        model.addAttribute("footmarkList", footmarkList);
        return "center-footmark";
    }

}
