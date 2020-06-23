package com.changgou.order.dao;

import com.changgou.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<Order> {


    @Select("SELECT order_status,count(*) as num from tb_order where create_time between #{start} and #{end} GROUP BY order_status")
    List<Map<String,Integer>> findOrderStatisticsData(@Param("start")Date start,@Param("end")Date end);


}
