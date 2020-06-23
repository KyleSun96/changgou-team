package com.changgou.web.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.OrderFeign;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.Address;
import com.changgou.user.pojo.User;
import com.changgou.web.center.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wcenterAddress")
public class WCenterAdressController {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AddressFeign addressFeign;

    @GetMapping
    public String cnenterAddress(Model model) {

        Result<List<Address>> list = addressFeign.list();

        model.addAttribute("addressList", list.getData());

        return "center-setting-address";
    }

    /**
     * 删除地址信息
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

        return "center-setting-address";
    }

    @GetMapping("/setDefault")
    @ResponseBody
    public Result setDefault(String id, Model model) {

        if (id != null) {
            Integer integer = Integer.valueOf(id);
            addressFeign.setDefault(integer);
        }
       /* Result<List<Address>> list = addressFeign.list();
        model.addAttribute("addressList", list.getData());
*/
        return new Result(true,StatusCode.OK,"设置默认成功");
    }

    /**
     * 添加地址信息
     * @param map
     * @param model
     * @return
     */
    @PostMapping("/addAddress")
    @ResponseBody
    public Result addAddress(@RequestBody Map map, Model model) {

        addressFeign.addPush(map);
        return new Result(true,StatusCode.OK,"添加");
    }

    /**
     * 查询地址信息
     * @param id
     * @return
     */
   @GetMapping("/queryAddress")
   @ResponseBody
    public Result queryAddress( String id ,Model model) {

       Address address = addressFeign.queryAddress(id).getData();
       Map mapByMapId = addressFeign.findMapByMapId(address);
       model.addAttribute("areaMap",mapByMapId);
       model.addAttribute("address",address);
       return new Result(true,StatusCode.OK,"");
    }


    /**
     * 编辑地址信息
     * @param map
     * @return
     */
   @PutMapping("/editAddress")
   @ResponseBody
    public Result editAddress( @RequestBody  Map map ) {
       return  addressFeign.editAddress(map);

    }
}
