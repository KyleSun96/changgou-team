package com.changgou.web.center.controller;

import com.changgou.entity.Result;
import com.changgou.order.feign.OrderFeign;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.Address;
import com.changgou.user.pojo.User;
import com.changgou.util.SMSUtilsLqz;
import com.changgou.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/wcenterSafe")
public class WCenterSafeController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AddressFeign addressFeign;

    @GetMapping
    public String cnenterAddress(Model model) {
        User user = userFeign.findUse();
        System.out.println(user.getPhone());
      //  model.addAttribute("phone", user.getPhone());
        model.addAttribute("phone", "15605607630");
        return "center-setting-safe";
    }

    @GetMapping("/phone")
    public String findPhone() {
        return "center-setting-address-phone";
    }

    @GetMapping("/complete")
    public String findComplete() {
        return "center-setting-address-complete";
    }

    /**
     * 验证旧密码
     *
     * @param password
     * @return
     */
    @GetMapping("/validatePassword")
    @ResponseBody
    public Result validatePassword(String password) {

        return userFeign.validatePassword(password);
    }


    /**
     * 修改新密码
     * @return
     */
    @GetMapping("/updatePassword")
    @ResponseBody
        public Result updatePassword(@PathParam("password") String password, @PathParam("hisPassword") String hisPassword, @PathParam("name") String name) {
        System.out.println(password);
        Result result = userFeign.updatePassword(password, hisPassword, name);
        return result;
    }


    /**
     * 验证手机验证码
     * @param code
     * @return
     */
    @GetMapping("/updatePhone")
    @ResponseBody
    public Result updatePhone(@PathParam("code") String code) {
        System.out.println(code);
        return userFeign.updatePhone(code);
    }


    /**
     * 修改新手机号
     * @param phone
     * @return
     */
    @GetMapping("/updatePhoneTrue")
    @ResponseBody
    public Result updatePhoneTrue(@PathParam("phone") String phone) {

        return userFeign.updatePhoneTrue(phone);
    }


    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send")
    @ResponseBody
    public String sendCode(@PathParam("phone") String phone) {

        //用户在线体检预约发送验证码
        //随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        System.out.println("validateCode: " + validateCode);
        //给用户发送验证码
        try {
           // SMSUtilsLqz.sendShortMessage(SMSUtilsLqz.VALIDATE_CODE, phone, validateCode.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将验证码保存到redis（5分钟）
        redisTemplate.boundValueOps(phone + "001").set(validateCode.toString(),300, TimeUnit.SECONDS);

        System.out.println(phone);
        return "center-setting-address-complete";
    }


}
