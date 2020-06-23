package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);

    /**
     * 根据用户名获取用户电话
     *
     * @return
     */
    @GetMapping("/user/findPhoneByUsername")
    public String findPhoneByUsername();
}
