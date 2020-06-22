package com.changgou.backend.controller;

import com.changgou.entity.Page;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/ordercenter")
public class OrderCenterController {

    @Autowired
    private OrderFeign orderFeign;


    @RequestMapping("/toOrderCenter")
    public String toOrderCenter(){
        return "hh";
    }

    @PostMapping("/findPage")
    @ResponseBody
    public Result findPage(@RequestBody Map searchMap){
        Result<Page<Order>> result = orderFeign.findPage(searchMap);
        Page<Order> data = result.getData();
        return  new Result(true, StatusCode.OK,"",data);
    }
}
