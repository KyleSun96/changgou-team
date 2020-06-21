package com.changgou.web.order.controller;


import com.changgou.entity.Result;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.pay.feign.AlipayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/alipay")
public class AlipayPayController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private AlipayFeign alipayFeign;

    //跳转到支付宝二维码页面
    @GetMapping
    public String aliPay(String orderId) {
        //1.根据order查询订单,跳转到错误页面
        Result<Order> orderResult = orderFeign.findById(orderId);
        if (orderResult.getData() == null) {
            return "fail";
        }
        //2.根据订单的状态判断,如果是未支付的订单,跳转到错误页面
        Order order = orderResult.getData();
        if (!"0".equals(order.getPayStatus())) {
            return "fail";
        }
        //3.基于alipayFeign的接口统计下单接口,并获取返回结果
        Result payResult = alipayFeign.nativePay(orderId, order.getPayMoney());
        if (payResult.getData() == null) {
            return "fail";
        }

        //4.封装结果数据
        Map payMap = (Map) payResult.getData();
        payMap.put("orderId", orderId);
        payMap.put("payMoney", order.getPayMoney());

        return "alipay";
    }

    //支付成功页面的跳转
    @RequestMapping("/toPaySuccess")
    public String toPaySuccess(Integer payMoney, Model model) {
        model.addAttribute("payMoney", payMoney);
        return "paysuccess";
    }
}
