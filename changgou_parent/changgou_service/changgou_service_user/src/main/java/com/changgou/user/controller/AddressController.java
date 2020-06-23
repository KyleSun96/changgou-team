package com.changgou.user.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.config.TokenDecode;
import com.changgou.user.service.AddressService;
import com.changgou.user.pojo.Address;
import com.github.pagehelper.Page;
import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {


    @Autowired
    private AddressService addressService;


    /**
     * 设置默认
     * @param id
     */
    @RequestMapping("/setDefault")
    public void setDefault(@RequestParam("id") Integer id){

        addressService.setDefault(id);
    }


    /**
     * 用户添加地址
     *
     * @param areaMap
     * @return
     */
    @RequestMapping("/addPush")
    public Result addPush(@RequestBody Map areaMap) {
        addressService.addPush(areaMap);
        return new Result(true, StatusCode.OK, "地址添加成功");
    }

    /**
     * id查询address对象
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryAddress")
    public Result<Address> queryAddress(@RequestParam("id") Integer id) {
        Address address = addressService.findByIdPush(id);
        return new Result(true, StatusCode.OK, "查询对象成功", address);
    }


    @RequestMapping("/findMapByMapId")
    public Map findMapByMapId(@RequestBody  Address address) {
        return addressService.findMapByMapId(address);
    }

    /**
     * 编辑对象
     *
     * @param areaMap
     * @return
     */
    @PostMapping("/editAddress")
    public Result editAddress(@RequestBody Map areaMap) {
        addressService.editPush(areaMap);
        return new Result(true, StatusCode.OK, "编辑成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Address> addressList = addressService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", addressList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Address address = addressService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", address);
    }

    /**
     * 地址编辑
     *
     * @param address
     * @return
     */
    @PutMapping()
    public Result edit(@RequestBody Address address) {
        addressService.edit(address);
        return new Result(true, StatusCode.OK, "地址编辑成功");
    }

    /***
     * 新增数据
     * @param address
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Address address) {
        addressService.add(address);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Address address, @PathVariable Integer id) {
        address.setId(id);
        addressService.update(address);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @GetMapping(value = "/del/{id}")
    public Result delete(@PathVariable("id") Integer id) {

        addressService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Address> list = addressService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Address> pageList = addressService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @Autowired
    private TokenDecode tokenDecode;

    @RequestMapping("/list")
    public Result<List<Address>> list() {
        //获取当前的登录人名称
        String username = tokenDecode.getUserInfo().get("username");
        //查询登录人相关的收件人地址信息
        List<Address> addressList = addressService.list(username);
        return new Result<>(true, StatusCode.OK, "查询成功", addressList);
    }

}
