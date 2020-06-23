package com.changgou.goods.dao;

import com.changgou.goods.pojo.Footmark;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FootmarkMapper extends Mapper<Footmark>{

    @Select("SELECT * FROM tb_footmark WHERE username = #{username} ORDER BY id DESC LIMIT 0,10")
    List<Footmark> findByUsername(@Param("username") String username);
}
