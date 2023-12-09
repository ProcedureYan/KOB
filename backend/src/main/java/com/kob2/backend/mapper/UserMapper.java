package com.kob2.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob2.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
