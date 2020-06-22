package com.changgou.user.service;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;

public interface UserService {


    /**
     * 发送短信
     */
    Result sendMessage(String mobile);


    /**
     * 注册新用户
     */
    void register(String smsCode, User user);
}
