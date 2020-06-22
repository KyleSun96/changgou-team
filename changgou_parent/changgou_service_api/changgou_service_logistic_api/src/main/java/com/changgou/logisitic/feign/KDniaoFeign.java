package com.changgou.logisitic.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "logistic")
public interface KDniaoFeign {
    @GetMapping("/logistic/query/{logisticCode}/{shipperCode}")
    public Result<Map> queryByLogisticCode(@PathVariable("logisticCode") String logisticCode, @PathVariable("shipperCode")  String shipperCode);
}
