package com.changgou.user.dao;

import com.changgou.user.pojo.Provinces;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ProvincesMapper extends Mapper<Provinces> {

    @Select("select * from tb_provinces where province = #{province}")
    public Provinces findByProvince(@Param("province") String province);
}
