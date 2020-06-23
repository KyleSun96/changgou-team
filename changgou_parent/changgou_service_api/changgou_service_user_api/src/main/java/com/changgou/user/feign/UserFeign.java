package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);

    @PostMapping("/user/add")
    public Result add(@RequestBody User user);


    /**
     * 根据用户名获取用户电话
     *
     * @return
     */
    @GetMapping("/user/findPhoneByUsername")
    public String findPhoneByUsername();
}
