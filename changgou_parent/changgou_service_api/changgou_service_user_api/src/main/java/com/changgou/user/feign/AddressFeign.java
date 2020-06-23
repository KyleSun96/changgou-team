package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@FeignClient(name = "user")
public interface AddressFeign {

    @PostMapping("/address")
    public Result add(@RequestBody Address address);

    @GetMapping("/address/{id}")
    public Result findById(@PathVariable("id") Integer id);

    @GetMapping("/address/list")
    public Result<List<Address>> list();

    @GetMapping(value = "/address/del/{id}")
    public Result delete(@PathVariable("id") Integer id);

    @PostMapping("/address/addPush")
    public Result addPush(@RequestBody Map areaMap);

    @PostMapping("/address/editAddress")
    public Result editAddress(@RequestBody Map areaMap);

    @GetMapping("/address/queryAddress")
    public Result<Address> queryAddress(@PathVariable("id") String id);


    @PostMapping("/address/findMapByMapId")
    public Map findMapByMapId(@RequestBody Address address);

    @GetMapping("/address/setDefault")
    public void setDefault(@PathParam("id") Integer id);
}
