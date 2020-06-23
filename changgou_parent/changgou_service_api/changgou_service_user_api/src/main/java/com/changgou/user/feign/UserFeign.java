package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.Address;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);


    @GetMapping("/user/load")
    public User findUserInfo();

    @GetMapping("/user/areaMap")
    public Map findAreaMap();

    @PostMapping("/user/imageUpload")
    public Result imageUpload(MultipartFile file);

    @PostMapping(value = "/user/imageUploadOss",consumes = "multipart/form-data")
    public Result imageUploadOss(MultipartFile file);

    @RequestMapping("/user/updatePassword")
    public Result updatePassword(@RequestParam("password") String password, @RequestParam("hisPassword") String hisPassword,@RequestParam("name") String name);



    @RequestMapping("/user/a/updatePhone")
    public Result updatePhone(@RequestParam("code") String code);



    @RequestMapping("/user/updatePhoneTrue")
    public Result updatePhoneTrue(@RequestParam("phone") String phone);


    @RequestMapping("/user/validatePassword")
    public Result validatePassword(@RequestParam("password") String password);

    /**
     * 更新用户数据
     *
     * @param userInfoMap
     * @param userInfo
     * @return
     */
    @PutMapping("/user")
    public Result updateInfo(@RequestBody Map userInfoMap, @RequestParam("userInfo") String userInfo);

    @GetMapping("/user/load/noname")
    public User findUse();
}
