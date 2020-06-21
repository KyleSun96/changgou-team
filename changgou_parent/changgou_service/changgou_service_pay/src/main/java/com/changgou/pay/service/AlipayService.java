package com.changgou.pay.service;

import java.util.Map;


public interface AlipayService {

    Map nativePay(String orderId, Integer money);

    //基于支付宝查询
    Map queryOrder(String orderId);

    //基于支付宝关闭订单
    Map closeOrder(String orderId);
}
