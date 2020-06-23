package com.changgou.order.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.pojo.Order;
import com.changgou.order.service.OrderService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderbcakend")
public class OrderBackendController {


    @Autowired
    private OrderService orderService;

    /***
     * 分页搜索实现
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<PageResult> findPageBackend(@RequestBody Map searchMap){

        Page<Order> pageList = orderService.findPage(searchMap);
        PageResult<Order> result = new PageResult<>(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK,"查询成功",result);
    }

    @GetMapping("/findAll")
    public List<Order> findAll(){
       return orderService.findAll();
    }

}
