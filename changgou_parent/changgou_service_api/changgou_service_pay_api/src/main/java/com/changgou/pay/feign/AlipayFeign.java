package com.changgou.pay.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ppay")
public interface AlipayFeign {

    //下单
    @GetMapping("/alipay/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money);

    //基于支付宝查询订单
    @GetMapping("/alipay/query/{orderId}")
    public Result queryOrder(@PathVariable("orderId") String orderId);

    //基于微信关闭订单
    @PutMapping("/alipay/close/{orderId}")
    public Result closeOrder(@PathVariable("orderId") String orderId);


}
