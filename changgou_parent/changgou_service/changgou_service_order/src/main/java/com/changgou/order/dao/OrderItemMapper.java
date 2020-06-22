package com.changgou.order.dao;

import com.changgou.order.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {

    @Select("select * from tb_order_item where order_id=#{orderId}")
    public List<OrderItem> findByOrderId(@Param("orderId") String orderId);

}
