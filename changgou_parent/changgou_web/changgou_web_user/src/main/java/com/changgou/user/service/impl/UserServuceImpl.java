package com.changgou.user.service.impl;


import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.changgou.user.util.RandomUserName;
import com.changgou.user.util.SMSUtils;
import com.changgou.user.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserServuceImpl implements UserService {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 注册新用户
     * @param smsCode
     * @param user
     */
    @Override
    public void register(String smsCode, User user) {
        String phone = user.getPhone();
        //取出存到redis中的验证码
        String smsCodeInRedis = (String) redisTemplate.opsForValue().get(phone);
        if (smsCodeInRedis != null && smsCode != null && user != null) {
            //对比成功,保存新用户
            User newUser = new User();
            //账号
            /*String username = RandomUserName.verifyUserName();
            user.setUsername(username);*/
            newUser.setUsername(user.getPhone());
            //手机号
            newUser.setPhone(user.getPhone());
            //密码
            String pwd = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(pwd);
            Date date = new Date();
            newUser.setCreated(date);
            newUser.setUpdated(date);
            userFeign.add(newUser);
        }
    }
    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @Override
    public Result sendMessage(String mobile) {
        //随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        //给用户发送验证码
        try{
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,mobile,validateCode.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"发送短信失败");
        }
        //将验证码保存到redis（5分钟）
        redisTemplate.boundValueOps(mobile).set(validateCode.toString(),1000, TimeUnit.HOURS);
        return new Result(true,StatusCode.OK,"发送成功");
    }

}

