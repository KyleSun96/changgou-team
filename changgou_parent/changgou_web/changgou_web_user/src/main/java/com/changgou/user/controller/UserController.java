package com.changgou.user.controller;


import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/wuser")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/Register")
    public String toRegister() {
        System.out.println(111);
        return "register";
    }

    /**
     * 发送短息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/sendSms")
    @ResponseBody
    public Result sendMessage(@RequestParam("mobile") String mobile) {
        Result result = userService.sendMessage(mobile);
        System.out.println(mobile);
        return result;
    }
    /**
     * 注册
     *
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("smsCode") String smsCode, @RequestBody User user) {
        userService.register(smsCode,user);
        return new Result(true, StatusCode.OK, "注册成功");


    }
}