package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Update("update tb_user set points=points+#{point} where username=#{username}")
    int updateUserPoint(@Param("username")String username, @Param("point") int point);

    //根据用户名获取用户电话
    @Select("select phone from tb_user where username = #{username}")
    String findPhoneByUsername(String username);
}
