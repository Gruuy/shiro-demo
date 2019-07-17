package com.shiro.service;

import com.shiro.entity.User;


/**
 *  获取用户权限信息接口
 * @author Gruuy
 * @date 2019-7-16
 */
public interface AnyUserService {

    /**
     *  根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByName(String username);
}
