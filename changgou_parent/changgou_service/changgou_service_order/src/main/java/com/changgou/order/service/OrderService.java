package com.changgou.order.service;

import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /***
     * 查询所有
     * @return
     */
    List<Order> findAll();



    //根据用户名查询所有订单
    List<Order> findOrderByUsername(String username);

    //代付款
    List<Order> findNoPayByUsername(String username);

    //立即支付
    void findtoPayByUsername(String id);

    //取消订单
    void findtoNoPayById(String id);

    //代发货
    List<Order> findNoConsignByUsername(String username);



    //查询待收货的订单
    List<Order> findPayOrder(String username);

    //查询所有待收货订单
    List<Order> findAllOrder();

    //查询带评价订单
    List<Order> findBuyerRateByOrder(String username);



    /***
     * 新增
     * @param order
     */
    String add(Order order);

    /***
     * 修改
     * @param order
     */
    void update(Order order);

    /***
     * 删除
     * @param id
     */
    void delete(String id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Order> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Order> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @return
     */
    Page<Order> findPage(Map<String, Object> searchMap);

    //修改订单的支付状态,并记录日志
    void updatePayStatus(String orderId, String transactionId);

    void closeOrder(String message);

    void batchSend(List<Order> orders);

    //手动确认收货
    void confirmTask(String orderId,String operator);



    void autoTack();

    /**
     * 发送催发货短信
     * @param id
     */
    void sendMessage(String id);
}
