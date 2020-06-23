package com.changgou.order.controller;
import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.OrderService;
import com.changgou.order.pojo.Order;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    //手动确定收货
    @RequestMapping ("/task/id")
    public Result confirmTask(@RequestParam("id") String Id){
        //1272865248067588096
        //1272909208886579200
        try {
            String username = tokenDecode.getUserInfo().get("username");
            orderService.confirmTask(Id,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
        return new Result(true,StatusCode.OK,"");
    }
    //根据用户名查询所有订单
    @RequestMapping("/findOrderByUsername")
    public Result<List<Order>> findOrderByUsername(){
        String username = tokenDecode.getUserInfo().get("username");
        List<Order> orderList = orderService.findOrderByUsername(username);
        return new Result<List<Order>>(true,StatusCode.OK,"查询所有订单成功",orderList);
    }

    //查询待收货的订单
    @RequestMapping("/findPayOrder")
    public Result<List<Order>> findPayOrder(){
        String username = tokenDecode.getUserInfo().get("username");
        List<Order> orderList = orderService.findPayOrder(username);

        return new Result<List<Order>>(true,StatusCode.OK,"查询待收货订单成功",orderList);
    }

    //代付款
    @RequestMapping("/findNoPayByUsername")
    public Result<List<Order>> findNoPayByUsername(){
        String username = tokenDecode.getUserInfo().get("username");
        List<Order> orderList = orderService.findNoPayByUsername(username);
        return new Result<List<Order>>(true,StatusCode.OK,"查询代付款订单成功",orderList);
    }
    //代发货
    @RequestMapping("/findNoConsignByUsername")
    public Result<List<Order>> findNoConsignByUsername(){
        String username = tokenDecode.getUserInfo().get("username");
        List<Order> orderList = orderService.findNoConsignByUsername(username);
        return new Result<List<Order>>(true,StatusCode.OK,"查询代付款订单成功",orderList);
    }

    //查询所有待收货订单
    @RequestMapping("/findAllOrder")
    public Result<List<Order>> findAllOrder(){
        List<Order> orderList = orderService.findAllOrder();
        return new Result<List<Order>>(true,StatusCode.OK,"根据id查询所有待收货订单成功",orderList);
    }

    //查询待评价订单
    @RequestMapping("/findBuyerRate")
    public Result<List<Order>> findBuyerRateByOrder(){
        String username = tokenDecode.getUserInfo().get("username");
        List<Order> orderList = orderService.findBuyerRateByOrder(username);
        return new Result<List<Order>>(true,StatusCode.OK,"查询待评价订单成功",orderList);
    }



    /**
     * 查询全部订单数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Order> orderList = orderService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",orderList) ;
    }



    @Autowired
    private TokenDecode tokenDecode;

    /***
     * 新增数据
     * @param order
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Order order){
        //获取登录人名称
        String username = tokenDecode.getUserInfo().get("username");
        order.setUsername(username);
        String orderId = orderService.add(order);
        return new Result(true,StatusCode.OK,"添加成功",orderId);
    }


    /***
     * 修改数据
     * @param order
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Order order,@PathVariable String id){
        order.setId(id);
        orderService.update(order);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        orderService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Order> list = orderService.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<Page<Order>> findPage(@RequestBody Map searchMap){

        Page<Order> pageList = orderService.findPage(searchMap);
        PageResult<Order> result = new PageResult<>(pageList.getTotal(), pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

    @PostMapping("/batchSend")
    public Result batchSend(@RequestBody List<Order> orders){
        orderService.batchSend(orders);
        return new Result(true,StatusCode.OK,"发货成功");
    }


    @RequestMapping("/statistics")
    public Result<List<Map<String, Integer>>> findOrderStatisticsData(@RequestParam("start") Date start,@RequestParam("end") Date end){
        List<Map<String, Integer>> data = orderService.findOrderStatisticsData(start, end);
        return new Result<>(true,StatusCode.OK,"获取数据成功",data);
    }

}
