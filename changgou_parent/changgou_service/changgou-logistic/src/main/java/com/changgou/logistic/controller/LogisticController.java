package com.changgou.logistic.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.logistic.service.KDNLogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/logistic")
public class LogisticController {

    @Autowired
    private KDNLogisticService kdnLogisticService;

    @GetMapping("/query/{logisticCode}/{shipperCode}")
    //前端传递map：String logisticCode, String shipperCode
    public Result queryByLogisticCode(@PathVariable("logisticCode") String logisticCode,@PathVariable("shipperCode")  String shipperCode) {
        Map resultMap = kdnLogisticService.queryByLogisticCode(logisticCode, shipperCode);
        return new Result(true, StatusCode.OK, "查詢物流成功", resultMap);
    }
}
