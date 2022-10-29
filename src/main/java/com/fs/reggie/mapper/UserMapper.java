package com.fs.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
