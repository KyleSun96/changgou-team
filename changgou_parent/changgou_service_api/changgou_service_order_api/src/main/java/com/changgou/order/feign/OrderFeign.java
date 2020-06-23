package com.changgou.order.feign;

import com.changgou.entity.Page;
import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;

@FeignClient(name = "order")
public interface OrderFeign {

    @PostMapping("/order")
    public Result add(@RequestBody Order order);

    @GetMapping("/order/{id}")
    public Result<Order> findById(@PathVariable("id") String id);

    @PostMapping(value = "/orderbcakend/search" )
    public Result<PageResult> findPage(@RequestBody Map searchMap);

    //查询待收货的订单
    @RequestMapping("/order/findPayOrder")
    public Result<List<Order>> findPayOrder();

    @RequestMapping("/order/findBuyerRate")
    public Result<List<Order>> findBuyerRateByOrder();

    @RequestMapping("/order/define")
    public Result define();


    @GetMapping("/orderbcakend/findAll")
    public List<Order> findAll();

}
