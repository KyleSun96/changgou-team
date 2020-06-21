package com.changgou.order.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order")
public interface OrderItemFeign {

    @GetMapping("orderItem/findName/{orderId}")
    public Result findByOrderId(@PathVariable("orderId") String orderId);
}
