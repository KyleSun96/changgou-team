package com.changgou.web.logistic.controller;

import com.changgou.entity.Result;
import com.changgou.logisitic.feign.KDniaoFeign;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.web.logistic.utils.ShippingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/wlogisitic")
public class LogisiticController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private KDniaoFeign kDniaoFeign;


    @RequestMapping("/trace")
    public String toLogisiticTracePage(@RequestParam("orderId") String orderId, Model model) {

        // 获取订单信息
        Result<Order> result = orderFeign.findById(orderId);
        Order order = result.getData();

        if (order != null) {
//        获取物理信息 物流单号，物流公司编码
            String logisticCode = order.getShippingCode();
            String shippingName = order.getShippingName();
            String shipperCode = ShippingUtil.nameToCode(shippingName);

            //查询物流
//            Result r = kDniaoFeign.queryByLogisticCode("75358360227330", "ZTO");
            Result r = kDniaoFeign.queryByLogisticCode(logisticCode, shipperCode);
            Map<String,Object> map = (Map<String, Object>) r.getData();
            Object traces = map.get("Traces");

            //封装数据模型
            model.addAttribute("Traces", traces);
//            model.addAttributes(map);
        }

        //渲染
        return "trace_detail";
    }
}
