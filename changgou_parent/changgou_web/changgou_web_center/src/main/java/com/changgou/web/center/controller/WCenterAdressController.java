package com.changgou.web.center.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wcenterAddress")
public class WCenterAdressController {


    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AddressFeign addressFeign;

    @GetMapping
    public String cnenterAddress(Model model) {

        Result<List<Address>> list = addressFeign.list();

        model.addAttribute("addressList", list.getData());

        return "center-setting-addr";
    }

    /**
     * 删除地址信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/deleteAddress")
    public String deleteAddress(String id, Model model) {

        if (id != null) {
            Integer integer = Integer.valueOf(id);
            addressFeign.delete(integer);
        }
        Result<List<Address>> list = addressFeign.list();
        model.addAttribute("addressList", list.getData());

        return "center-setting-addr";
    }

    @GetMapping("/setDefault")
    @ResponseBody
    public Result setDefault(Integer id, Model model) {

        if (id != null) {
            addressFeign.setDefault(id);

            return new Result(true, StatusCode.OK, "设置默认成功");
        }
        return new Result(false, StatusCode.ERROR, "设置默认失败");

    }

    /**
     * 添加地址信息
     *
     * @param map
     * @param model
     * @return
     */
    @PostMapping("/addAddress")
    @ResponseBody
    public Result addAddress(@RequestBody Map map, Model model) {

        addressFeign.addPush(map);
        return new Result(true, StatusCode.OK, "添加");
    }

    /**
     * 查询地址信息
     *
     * @param id
     * @return
     */
    @GetMapping("/queryAddress")
    @ResponseBody
    public Result queryAddress(@PathParam("id") Integer id, Model model) {


        Address address = addressFeign.queryAddress(id).getData();
        Map mapByMapId = addressFeign.findMapByMapId(address);
        Map map = new HashMap<>();
        map.put("address",address);
        map.put("areamap",mapByMapId);

        return new Result(true, StatusCode.OK, "", map);
    }


    /**
     * 编辑地址信息
     *
     * @param map
     * @return
     */
    @PutMapping("/editAddress")
    @ResponseBody
    public Result editAddress(@RequestBody Map map) {
        return addressFeign.editAddress(map);

    }
}
