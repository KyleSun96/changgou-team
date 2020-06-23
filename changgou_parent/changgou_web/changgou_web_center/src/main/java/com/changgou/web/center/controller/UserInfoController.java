package com.changgou.web.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.OrderFeign;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.User;
import com.changgou.web.center.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/wuserInfo")
public class UserInfoController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

    @GetMapping
    public String cnenterUserInfo(Model model) {

        User userInfo = userFeign.findUserInfo();
        Map areaMap = userFeign.findAreaMap();
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("map", areaMap);

        return "center-setting-info";
    }

    @PostMapping("/imageUploadOss")

    public String imageUploadOss(@RequestBody MultipartFile file,Model model) {

        User userInfo = userFeign.findUserInfo();
        Map areaMap = userFeign.findAreaMap();
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("map", areaMap);
        if (file != null) {
            userFeign.imageUploadOss(file);
            return "center-setting-info";
        }
        return "center-setting-info";
    }

    @PutMapping
    @ResponseBody
    public Result updateInfo(@RequestBody Map map) {

        Map userInfoMap = (Map) map.get("userInfoMap");
        Map userMap = (Map) map.get("userInfo");
        String created = (String) userMap.get("created");
        String updated = (String) userMap.get("updated");
        try {
            Date date = DateUtils.parseString2Date(created);
            Date date1 = DateUtils.parseString2Date(updated);
            userMap.put("created", date);
            userMap.put("updated", date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userInfoString = JSONObject.toJSONString(userMap);

        return userFeign.updateInfo(userInfoMap, userInfoString);
    }
}
