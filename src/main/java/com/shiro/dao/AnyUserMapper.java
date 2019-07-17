package com.shiro.dao;

import com.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 获取用户信息dao（UserDetails）
 * @author Gruuy
 * @date 2019-7-16
 */
@Repository("anyUserMapper")
@Mapper
public interface AnyUserMapper {

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByUsername(@Param("username") String username);
}
