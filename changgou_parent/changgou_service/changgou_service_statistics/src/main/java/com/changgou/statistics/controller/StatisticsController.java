package com.changgou.statistics.controller;

import com.changgou.order.feign.OrderFeign;
import com.changgou.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private OrderFeign orderFeign;

    @RequestMapping("/order")
    public String toOrderStatistics(Model model) {
        List<String> orderStatus = new ArrayList<>(); //封装订单分类
        List<Map<String, Object>> orderData = new ArrayList<>(); //封装订单状态与其对应的值
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Integer>> dataList = orderFeign.findOrderStatisticsData(DateUtil.addDateHour(new Date(), -24 * 7), new Date()).getData();
        if (dataList != null) {
            for (Map<String, Integer> map : dataList) {
                Map<String, Object> data = new HashMap<>();
                Integer value = map.get("order_status");
                if (value == 0) {
                    orderStatus.add("待付款订单");
                    data.put("name", "待付款订单");
                    data.put("value", map.get("num"));
                    orderData.add(data);
                }
                if (value == 1) {
                    orderStatus.add("待发货订单");
                    data.put("name", "待发货订单");
                    data.put("value", map.get("num"));
                    orderData.add(data);
                }
                if (value == 2) {
                    orderStatus.add("已发货订单");
                    data.put("name", "已发货订单");
                    data.put("value", map.get("num"));
                    orderData.add(data);
                }
                if (value == 3) {
                    orderStatus.add("已完成订单");
                    data.put("name", "已完成订单");
                    data.put("value", map.get("num"));
                    orderData.add(data);
                }
                if (value == 4) {
                    orderStatus.add("已关闭订单");
                    data.put("name", "已关闭订单");
                    data.put("value", map.get("num"));
                    orderData.add(data);
                }
            }
            result.put("orderStatus", orderStatus);
            result.put("orderData", orderData);
        }
        model.addAttribute("result",result);
        return "order_statistics";
    }
}
