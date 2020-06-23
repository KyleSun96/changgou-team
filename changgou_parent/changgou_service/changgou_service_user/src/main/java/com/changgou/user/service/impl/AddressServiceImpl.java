package com.changgou.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.changgou.user.config.TokenDecode;
import com.changgou.user.dao.*;
import com.changgou.user.pojo.Areas;
import com.changgou.user.pojo.Cities;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.AddressService;
import com.changgou.user.pojo.Address;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Autowired
    private AreasMapper areasMapper;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private ProvincesMapper provincesMapper;

    /**
     * 编辑地址
     *
     * @param address
     */
    @Override
    public void edit(Address address) {
        if (address != null && address.getId() != null) {
            addressMapper.updateByPrimaryKeySelective(address);
        } else {
            throw new RuntimeException("传入参数为空或,缺少主键");
        }
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Address findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     *
     * @param address
     */
    @Override
    public void add(Address address) {
        addressMapper.insert(address);
    }


    /**
     * 修改
     *
     * @param address
     */
    @Override
    public void update(Address address) {
        addressMapper.updateByPrimaryKey(address);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Address> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return addressMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Address> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Address>) addressMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Address> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Address>) addressMapper.selectByExample(example);
    }

    @Override
    public List<Address> list(String username) {
        Address address = new Address();
        address.setUsername(username);
        List<Address> addressList = addressMapper.select(address);
        return addressList;
    }

    /**
     * 用户添加数据
     *
     * @param map
     */
    @Override
    public void addPush(Map map) {
        Object address = map.get("address");
        String s = JSONObject.toJSONString(address);
        Address addressResult = JSONObject.parseObject(s, Address.class);
        Map areaMap = (Map) map.get("areaMap");
        Map<String, String> resultMap = findAreaId(areaMap);
        if (areaMap != null) {
            if (resultMap.get("areaId") != null) {
                addressResult.setAreaid(resultMap.get("areaId"));
            }
            if (resultMap.get("provincesId") != null) {
                addressResult.setProvinceid(resultMap.get("provincesId"));
            }
            if (resultMap.get("cityId") != null) {
                addressResult.setCityid(resultMap.get("cityId"));
            }
        }
        addressResult.setUsername(tokenDecode.getUserInfo().get("username"));
        addressMapper.insert(addressResult);


    }

    /**
     * 根据id查地址
     *
     * @param
     * @return
     */
    @Override
    public Map findMapByMapId(Address address) {

        Map<String, String> map = new HashMap<>();

        if (address != null) {
            if (address.getAreaid() != null) {
                map.put("area", String.valueOf(areasMapper.selectByPrimaryKey(address.getAreaid()).getArea()));
            }
            if (address.getProvinceid() != null) {
                map.put("province", String.valueOf(provincesMapper.selectByPrimaryKey(address.getProvinceid()).getProvince()));

            }
            if (address.getCityid() != null) {
                map.put("city", String.valueOf(citiesMapper.selectByPrimaryKey(address.getCityid()).getCity()));

            }
        }
        return map;
    }

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 设置默认
     *
     * @param id
     */
    @Override
    @Transactional
    public void setDefault(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        if (address == null) {
            throw new RuntimeException("地址不存在");
        }
        if (!"1".equals(address.getIsDefault())) {
            //将默认地址修改为0
            String username = tokenDecode.getUserInfo().get("username");
            Example example = new Example(Address.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", username);
            criteria.andEqualTo("isDefault", "1");
            List<Address> addressList = addressMapper.selectByExample(example);
            for (Address address1 : addressList) {
                address1.setIsDefault("0");
                addressMapper.updateByPrimaryKeySelective(address1);
            }
            address.setIsDefault("1");
            addressMapper.updateByPrimaryKeySelective(address);
        }
    }


    /**
     * 编辑地址信息
     *
     * @param map
     */
    @Override
    public void editPush(Map map) {

        Object address = map.get("address");
        String s = JSONObject.toJSONString(address);
        Address addressResult = JSONObject.parseObject(s, Address.class);
        Map areaMap = (Map) map.get("areaMap");
        Map<String, String> resultMap = findAreaId(areaMap);
        if (resultMap != null) {
            if (resultMap.get("areaId") != null) {
                addressResult.setAreaid(resultMap.get("areaId"));
            }
            if (resultMap.get("provincesId") != null) {
                addressResult.setProvinceid(resultMap.get("provincesId"));
            }
            if (resultMap.get("cityId") != null) {

                addressResult.setCityid(resultMap.get("cityId"));
            }
        }
        addressMapper.updateByPrimaryKeySelective(addressResult);
    }

    @Override
    public Address findByIdPush(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        return address;
    }

    /**
     * 查询三级地区ID
     *
     * @param areaMap
     * @return
     */
    public Map<String, String> findAreaId(Map areaMap) {
        try {

            String province = null;
            String city = null;
            String area = null;
            try {
                province = (String) areaMap.get("province");
            } catch (Exception e) {

            }

            Map<String, String> map = new HashMap<>();
            if (province != null) {

                Provinces provinces1 = new Provinces();
                provinces1.setProvince(province);

                Example example = new Example(Provinces.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike("province", "%" + province + "%");

                Provinces provinces = provincesMapper.selectOneByExample(example);
                if (provinces != null) {

                    map.put("provincesId", provinces.getProvinceid());
                    try {
                        city = (String) areaMap.get("city");
                    } catch (Exception e) {

                    }

                    Cities cities = new Cities();
                    cities.setProvinceid(provinces.getProvinceid());
                    cities.setCity(city);
                    Cities citiesResult = citiesMapper.selectOne(cities);
                    if (citiesResult != null) {
                        map.put("cityId", citiesResult.getCityid());
                        try {
                            area = (String) areaMap.get("area");
                        } catch (Exception e) {

                        }
                        Areas areas = new Areas();
                        areas.setArea(area);
                        areas.setCityid(citiesResult.getCityid());
                        Areas areasResult = areasMapper.selectOne(areas);

                        if (areasResult != null) {

                            map.put("areaId", areasResult.getAreaid());
                        }


                    }
                }
            }
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                criteria.andEqualTo("username", searchMap.get("username"));
            }
            // 省
            if (searchMap.get("provinceid") != null && !"".equals(searchMap.get("provinceid"))) {
                criteria.andLike("provinceid", "%" + searchMap.get("provinceid") + "%");
            }
            // 市
            if (searchMap.get("cityid") != null && !"".equals(searchMap.get("cityid"))) {
                criteria.andLike("cityid", "%" + searchMap.get("cityid") + "%");
            }
            // 县/区
            if (searchMap.get("areaid") != null && !"".equals(searchMap.get("areaid"))) {
                criteria.andLike("areaid", "%" + searchMap.get("areaid") + "%");
            }
            // 电话
            if (searchMap.get("phone") != null && !"".equals(searchMap.get("phone"))) {
                criteria.andLike("phone", "%" + searchMap.get("phone") + "%");
            }
            // 详细地址
            if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
                criteria.andLike("address", "%" + searchMap.get("address") + "%");
            }
            // 联系人
            if (searchMap.get("contact") != null && !"".equals(searchMap.get("contact"))) {
                criteria.andLike("contact", "%" + searchMap.get("contact") + "%");
            }
            // 是否是默认 1默认 0否
            if (searchMap.get("isDefault") != null && !"".equals(searchMap.get("isDefault"))) {
                criteria.andEqualTo("isDefault", searchMap.get("isDefault"));
            }
            // 别名
            if (searchMap.get("alias") != null && !"".equals(searchMap.get("alias"))) {
                criteria.andLike("alias", "%" + searchMap.get("alias") + "%");
            }

            // id
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }

        }
        return example;
    }

}
