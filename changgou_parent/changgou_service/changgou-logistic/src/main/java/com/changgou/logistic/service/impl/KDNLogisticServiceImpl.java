package com.changgou.logistic.service.impl;


import com.alibaba.fastjson.JSON;
import com.changgou.logistic.utils.KDNiaoUtil;
import com.changgou.logistic.service.KDNLogisticService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

@Service
public class KDNLogisticServiceImpl implements KDNLogisticService {

    @Value("${EBusinessID}")
    private String EBusinessID;

    @Value("${AppKey}")
    private String AppKey;

    @Value("${DataType}")
    private String DataType;

    @Override
    public Map queryByLogisticCode(String logisticCode, String shipperCode) {
        //封装请求数据
        String requestData = "{'OrderCode':'','ShipperCode':'" + shipperCode + "','LogisticCode':'" + logisticCode + "'}";

        Map<String, String> params = new HashMap<String, String>();

        try {
            // 请求数据utf-8编码
            params.put("RequestData", KDNiaoUtil.urlEncoder(requestData, "UTF-8"));
            // 商户id
            params.put("EBusinessID", EBusinessID);
            //请求指令
            params.put("RequestType", KDNiaoUtil.Order_Handle_URL_RequestType);
            // 电子签名
            String dataSign = KDNiaoUtil.encrypt(requestData, AppKey, "UTF-8");
            params.put("DataSign", KDNiaoUtil.urlEncoder(dataSign, "UTF-8"));

            params.put("DataType", DataType);

            // 发送请求，获取返回结果
            String result=KDNiaoUtil.sendPost(KDNiaoUtil.Order_Handle_URL, params);

            //转为map对象
            Map resultMap = JSON.parseObject(result, Map.class);

            //根据公司业务处理返回的信息......

            return resultMap;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
