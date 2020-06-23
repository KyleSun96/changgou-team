package com.changgou.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.pojo.Task;
import com.changgou.user.config.TokenDecode;
import com.changgou.user.dao.*;
import com.changgou.user.pojo.*;
import com.changgou.user.service.UserService;
import com.changgou.user.util.OSSClientUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PointLogMapper pointLogMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AreasMapper areasMapper;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private ProvincesMapper provincesMapper;

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private OSSClientUtil ossClientUtil;

    /**
     * 更新用户头像
     *
     * @param file
     */
    @Override
    public void updateHead(MultipartFile file) {
        String name = ossClientUtil.uploadImg2Oss(file);
        String imgUrl = ossClientUtil.getImgUrl(name);
        User user = new User();
        String username = tokenDecode.getUserInfo().get("username");
        user.setUsername(username);
        user.setImageUrl(imgUrl);
        user.setUpdated(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 更新用户所在地
     *
     * @param userInfoMap
     * @param userInfo
     */
    @Override
    public void updateInfo(Map userInfoMap, User userInfo) {
        String areaId = findAreaId(userInfoMap);

            String username = tokenDecode.getUserInfo().get("username");

            //查询主键usernmae
            userInfo.setUsername(username);
            userInfo.setUpdated(new Date());

            userMapper.updateByPrimaryKeySelective(userInfo);


    }

    /**
     * 根据地址查询地区Id
     *
     * @param userInfoMap
     * @return
     */
    @Override
    public String findAreaId(Map userInfoMap) {

        //根据省份名称查询省份Id
        String province = (String) userInfoMap.get("province");

        Example example1 = new Example(Provinces.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andLike("province", "%" + province + "%");
        List<Provinces> provincesList = provincesMapper.selectByExample(example1);

        if (provincesList != null && provincesList.size() > 0) {
            Provinces resultProvinces = provincesList.get(0);
            //存在省份,根据省份Id和城市名称查询城市Id
            String city = (String) userInfoMap.get("city");
            Cities cities = new Cities();
            cities.setProvinceid(resultProvinces.getProvinceid());
            cities.setCity(city);
            List<Cities> citiesList = citiesMapper.select(cities);
            //存在城市查询区Id
            if (citiesList != null && citiesList.size() > 0) {
                Cities cities1 = citiesList.get(0);
                String area = (String) userInfoMap.get("area");
                Example example = new Example(Areas.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("area", area);
                criteria.andEqualTo("cityid", cities1.getCityid());

                List<Areas> areas = areasMapper.selectByExample(example);

                //如果区不为空修改用户信息
                if (areas != null && areas.size() > 0) {
                    String areaid = areas.get(0).getAreaid();
                    //返回地区areaId
                    return areaid;
                }
            }

        }

        return null;
    }


    @Override
    public Map findMapByAreaId() {
        String username = tokenDecode.getUserInfo().get("username");
        if (username != null) {
            String areaId = userMapper.selectByPrimaryKey(username).getAreaId();
            if (areaId != null) {
                Areas areas = areasMapper.selectByPrimaryKey(areaId);
                Cities cities = citiesMapper.selectByPrimaryKey(areas.getCityid());
                Provinces provinces = provincesMapper.selectByPrimaryKey(cities.getProvinceid());
                Map map = new HashMap();
                map.put("province", provinces.getProvince());
                map.put("city", cities.getCity());
                map.put("district", areas.getArea());
                return map;
            }
        }
        return null;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param username
     * @return
     */
    @Override
    public User findById(String username) {
        return userMapper.selectByPrimaryKey(username);
    }


    /**
     * 增加
     *
     * @param user
     */
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }


    /**
     * 修改
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     *
     * @param username
     */
    @Override
    public void delete(String username) {
        userMapper.deleteByPrimaryKey(username);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<User> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return userMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<User> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<User>) userMapper.selectAll();
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
    public Page<User> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<User>) userMapper.selectByExample(example);
    }


    @Override
    @Transactional
    public int updateUserPoint(Task task) {
        System.out.println("用户服务现在开始对任务进行处理");
        //1.从task中获取相关数据
        Map map = JSON.parseObject(task.getRequestBody(), Map.class);
        String username = map.get("username").toString();
        String orderId = map.get("orderId").toString();
        int point = (int) map.get("point");

        //2.判断当前的任务是否操作过
        PointLog pointLog = pointLogMapper.findPointLogByOrderId(orderId);
        if (pointLog != null) {
            return 0;
        }

        //3.将任务存入到redis中
        redisTemplate.boundValueOps(task.getId()).set("exist", 30, TimeUnit.SECONDS);

        //4.修改用户积分
        int result = userMapper.updateUserPoint(username, point);
        if (result <= 0) {
            return 0;
        }

        //5.记录积分日志信息
        pointLog = new PointLog();
        pointLog.setUserId(username);
        pointLog.setOrderId(orderId);
        pointLog.setPoint(point);
        result = pointLogMapper.insertSelective(pointLog);
        if (result <= 0) {
            return 0;
        }

        //6.删除redis中的任务信息
        redisTemplate.delete(task.getId());
        System.out.println("用户服务完成了更改用户积分的操作");
        return 1;
    }

    /**
     *  更新密码
     *
     * @param password
     * @return
     */
    @Override
    public Result updatePassword(String password, String hisPassword, String name) {
        if (password == null || hisPassword == null) {
            return new Result(false, StatusCode.ERROR, "请输入密码,请重新发送");
        }
        if (!password.equals(hisPassword)) {
            return new Result(false, StatusCode.ERROR, "密码不一致,请重新发送");
        }

        String username = tokenDecode.getUserInfo().get("username");
        String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();

        user.setName(name);
        user.setUsername(username);
        user.setPassword(newPassword);

        userMapper.updateByPrimaryKeySelective(user);
        return new Result(true, StatusCode.OK, "修改密码成功");


    }

    /**
     * 修改手机号验证
     *
     * @param code
     * @return
     */
    @Override
    public Result updatePhone(String code) {
        String username = tokenDecode.getUserInfo().get("username");
        User user = userMapper.selectByPrimaryKey(username);

        String valiCode = (String) redisTemplate.boundValueOps(user.getPhone() + "001").get();
        if (valiCode == null || code == null) {
            return new Result(false, StatusCode.ERROR, "验证码不存在,请重新发送");
        }
        if (valiCode.equals(code)) {
            return new Result(true, StatusCode.OK, "验证通过");
        } else {
            return new Result(false, StatusCode.ERROR, "验证码错误");
        }

    }

    /**
     * 修改手机号
     *
     * @param
     * @return
     */
    @Override
    public Result updatePhoneTrue(String phone) {
        String username = tokenDecode.getUserInfo().get("username");
        User user = userMapper.selectByPrimaryKey(username);
        user.setPhone(phone);
        userMapper.updateByPrimaryKeySelective(user);
        return new Result(true, StatusCode.OK, "修改手机号成功");

    }

    @Override
    public Result validatePassword(String password) {
        String username = tokenDecode.getUserInfo().get("username");
        User user = userMapper.selectByPrimaryKey(username);
        if (BCrypt.checkpw(password, user.getPassword())) {
            return new Result(true, StatusCode.OK, "验证通过");
        }
        return new Result(false, StatusCode.ERROR, "密码不对");
    }

    @Override
    public User findByNull() {
        String username = tokenDecode.getUserInfo().get("username");
        User user = userMapper.selectByPrimaryKey(username);
        return user;
    }

    /**
     * 根据用户名获取用户电话
     * @param username
     * @return
     */
    @Override
    public String findPhoneByUsername(String username) {
        return userMapper.findPhoneByUsername(username);
    }

    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                criteria.andEqualTo("username", searchMap.get("username"));
            }
            // 密码，加密存储
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                criteria.andEqualTo("password", searchMap.get("password"));
            }
            // 注册手机号
            if (searchMap.get("phone") != null && !"".equals(searchMap.get("phone"))) {
                criteria.andLike("phone", "%" + searchMap.get("phone") + "%");
            }
            // 注册邮箱
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                criteria.andLike("email", "%" + searchMap.get("email") + "%");
            }
            // 会员来源：1:PC，2：H5，3：Android，4：IOS
            if (searchMap.get("sourceType") != null && !"".equals(searchMap.get("sourceType"))) {
                criteria.andEqualTo("sourceType", searchMap.get("sourceType"));
            }
            // 昵称
            if (searchMap.get("nickName") != null && !"".equals(searchMap.get("nickName"))) {
                criteria.andLike("nickName", "%" + searchMap.get("nickName") + "%");
            }
            // 真实姓名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 使用状态（1正常 0非正常）
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            // 头像地址
            if (searchMap.get("headPic") != null && !"".equals(searchMap.get("headPic"))) {
                criteria.andLike("headPic", "%" + searchMap.get("headPic") + "%");
            }
            // QQ号码
            if (searchMap.get("qq") != null && !"".equals(searchMap.get("qq"))) {
                criteria.andLike("qq", "%" + searchMap.get("qq") + "%");
            }
            // 手机是否验证 （0否  1是）
            if (searchMap.get("isMobileCheck") != null && !"".equals(searchMap.get("isMobileCheck"))) {
                criteria.andEqualTo("isMobileCheck", searchMap.get("isMobileCheck"));
            }
            // 邮箱是否检测（0否  1是）
            if (searchMap.get("isEmailCheck") != null && !"".equals(searchMap.get("isEmailCheck"))) {
                criteria.andEqualTo("isEmailCheck", searchMap.get("isEmailCheck"));
            }
            // 性别，1男，0女
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                criteria.andEqualTo("sex", searchMap.get("sex"));
            }

            // 会员等级
            if (searchMap.get("userLevel") != null) {
                criteria.andEqualTo("userLevel", searchMap.get("userLevel"));
            }
            // 积分
            if (searchMap.get("points") != null) {
                criteria.andEqualTo("points", searchMap.get("points"));
            }
            // 经验值
            if (searchMap.get("experienceValue") != null) {
                criteria.andEqualTo("experienceValue", searchMap.get("experienceValue"));
            }

        }
        return example;
    }

}
