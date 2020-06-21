package com.changgou.web.order.controller;

import com.changgou.entity.Result;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.order.feign.CartFeign;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.feign.OrderItemFeign;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import com.changgou.util.DateUtil;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/worder")
public class OrderController {

    @Autowired
    private AddressFeign addressFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private OrderItemFeign orderItemFeign;

    //确定收货
    @RequestMapping("/define")
    public void define(){
        orderFeign.define();
    }

    /**
     * 跳转到待收货页面
     * @param model
     * @return
     */
    @RequestMapping("/order/findPayOrder")
    public String toOrderReceive(Model model){
        List<Order> orderList = orderFeign.findPayOrder().getData();
        for (Order order : orderList) {
            String name = (String) orderItemFeign.findByOrderId(order.getId()).getData();
            order.setOrderItemName(name);

        }
        model.addAttribute("orderList",orderList);

        return "center-order-receive";

    }

    /**
     *跳转到待评价页面
     * @return
     */
    @RequestMapping("/order/findBuyerRate")
    public String toOrderEvaluate(Model model){
        List<Order> orderList = orderFeign.findBuyerRateByOrder().getData();
        for (Order order : orderList) {
            String name = (String) orderItemFeign.findByOrderId(order.getId()).getData();

            order.setOrderItemName(name);

        }
        model.addAttribute("orderList",orderList);

        return "center-order-evaluate";
    }





    @RequestMapping("/ready/order")
    public String readyOrder(Model model){
        //收件人的地址信息
        List<Address> addressList = addressFeign.list().getData();
        model.addAttribute("address",addressList);

        //购物车信息
        Map map = cartFeign.list();
        List<OrderItem> orderItemList = (List<OrderItem>) map.get("orderItemList");
        Integer totalMoney = (Integer) map.get("totalMoney");
        Integer totalNum = (Integer) map.get("totalNum");

        model.addAttribute("carts",orderItemList);
        model.addAttribute("totalMoney",totalMoney);
        model.addAttribute("totalNum",totalNum);

        //默认收件人信息
        for (Address address : addressList) {
            if ("1".equals(address.getIsDefault())){
                //默认收件人
                model.addAttribute("deAddr",address);
                break;
            }
        }
        return "order";
    }



    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Order order){
        Result result = orderFeign.add(order);
        return result;
    }

    @GetMapping("/toPayPage")
    public String toPayPage(String orderId,Model model){
        //获取到订单的相关信息
        Order order = orderFeign.findById(orderId).getData();
        model.addAttribute("orderId",orderId);
        model.addAttribute("payMoney",order.getPayMoney());
        return "pay";
    }
}
