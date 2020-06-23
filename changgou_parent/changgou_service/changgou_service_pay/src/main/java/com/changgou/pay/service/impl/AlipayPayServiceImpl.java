package com.changgou.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.changgou.pay.service.AlipayPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayPayServiceImpl implements AlipayPayService {
    @Autowired
    private AlipayClient alipayClient;

    //下单
    @Override
    public Map nativePay(String orderId, Integer money) {
        //1.创建用于请求的客户端对象

        //2.创建用于接口请求的对象
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //3.定义参数
        HashMap<String, String> map = new HashMap<>();
        System.out.println(orderId);
        map.put("out_trade_no", orderId);//订单
        map.put("total_amount", money + "");//当前订单总金额
        map.put("subject", "畅购商城");//当前订单描述
        map.put("timeout_express", "1m");

        request.setNotifyUrl("http://kylesun.cross.echosite.cn/alipay/notify");
        request.setBizContent(JSON.toJSONString(map));

        //4.使用支付宝接口调用的对象发起预下单发起请求
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }

        //5、处理结果
        HashMap<String, Object> res = new HashMap<>();
        res.put("qr_code", response.getQrCode());
        res.put("orderId", response.getOutTradeNo());
        return res;
    }

    //查询订单
    @Override
    public Map queryOrder(String orderId) {
        //1.创建用于请求的客户端对象

        //2.创建用于接口请求的对象
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //3.封装请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("out_trade_no", orderId);
        request.setBizContent(JSON.toJSONString(map));

        //4.发起接口请求
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //5.处理结果
        System.out.println("交易状态:" + response.getTradeStatus());
        System.out.println("订单金额:" + response.getTotalAmount());

        HashMap<String, Object> res = new HashMap<>();
        res.put("trade_status", response.getTradeStatus());//交易状态码
        res.put("orderId", response.getOutTradeNo());//订单编号
        res.put("total", response.getTotalAmount());//总金额
        return res;
    }

    //关闭订单
    @Override
    public Map closeOrder(String orderId) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();

        //封装关闭订单的请求参数
        HashMap<String, String> map = new HashMap<>();
        map.put("out_trade_no", orderId);
        request.setBizContent(JSON.toJSONString(map));
        try {
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            //判断订单有没有关闭成功
            String code = response.getBody();
            if ("10000".equals(code)) {
                HashMap<String, String> res = new HashMap<>();
                res.put("code", code);
                return res;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
