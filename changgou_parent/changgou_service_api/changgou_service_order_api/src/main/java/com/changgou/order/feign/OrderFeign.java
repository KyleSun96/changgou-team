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
    public Result<PageResult> findPageBackend(@RequestBody Map searchMap);

    @PostMapping(value = "/order/search" )
    public Result<Page<Order>> findPage(@RequestBody Map searchMap);


    //查询待收货的订单
    @RequestMapping("/order/findPayOrder")
    public Result<List<Order>> findPayOrder();

    @RequestMapping("/order/findBuyerRate")
    public Result<List<Order>> findBuyerRateByOrder();

    @RequestMapping("/order/define")
    public Result define();


    @GetMapping("/orderbcakend/findAll")
    public List<Order> findAll();

    @RequestMapping ("/order/task/id")
    public Result confirmTask(@RequestParam("id") String Id);

    @RequestMapping("/order/findOrderByUsername")
    public Result<List<Order>> findOrderByUsername();

    //查询所有待收货订单
    @RequestMapping("/order/findAllOrder")
    public Result<List<Order>> findAllOrder();

    @RequestMapping("/order/findNoPayByUsername")
    public Result<List<Order>> findNoPayByUsername();

    //代发货
    @RequestMapping("/order/findNoConsignByUsername")
    public Result<List<Order>> findNoConsignByUsername();

    //立即支付
    @RequestMapping("/order/findtoPay")
    public Result findtoPayByUsername(@RequestParam("id") String id);

    //取消订单
    @RequestMapping("/order/findtoNoPay")
    public Result findtoNoPayById(@RequestParam("id") String id);

    //发送催货短信
    @RequestMapping("/order/sendMessage")
    public Result sendMessage(@RequestParam("id") String id);

}
