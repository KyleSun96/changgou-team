package com.changgou.order.dao;

import com.changgou.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {

    /*@Select("select * from tb_order where username=#{username}")
    public List<Order> findOrderByUsername(@Param("username") String username);*/
}
