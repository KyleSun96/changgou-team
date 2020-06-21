package com.changgou.web.logistic.utils;

import jdk.internal.dynalink.beans.StaticClass;
import org.springframework.util.StringUtils;

public class ShippingUtil {

    public static void main(String[] args) {
        String s = nameToCode("nnn顺丰速运啊啊");
        System.out.println(s);
    }

    public static String nameToCode(String name) {

        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("请填写物流公司名称");
        }

        if (name.contains("顺丰速运")) {
            return "SF";
        }
        if (name.contains("中通快递")) {
            return "ZTO";
        }
        if (name.contains("申通快递")) {
            return "STO";
        }
        if (name.contains("圆通速递")) {
            return "YTO";
        }
        if (name.contains("韵达速递")) {
            return "YD";
        }
        if (name.contains("邮政快递包裹")) {
            return "YZPY";
        }
        if (name.contains("EMS")) {
            return "EMS";
        }
        if (name.contains("天天快递")) {
            return "HHTT";
        }
        if (name.contains("京东快递")) {
            return "JD";
        }
        if (name.contains("德邦快递")) {
            return "DBL";
        }
        if (name.contains("宅急送")) {
            return "ZJS";
        }
        return null;
    }
}
