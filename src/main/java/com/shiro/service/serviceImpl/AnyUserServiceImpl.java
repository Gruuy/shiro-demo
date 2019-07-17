package com.shiro.service.serviceImpl;

import com.shiro.dao.AnyUserMapper;
import com.shiro.entity.User;
import com.shiro.service.AnyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnyUserServiceImpl implements AnyUserService {

    @Autowired
    private AnyUserMapper anyUserMapper;

    /**
     * 通过用户名获取用户信息
     */
    @Override
    public User getUserByName(String username) {
        return anyUserMapper.getUserByUsername(username);
    }
}
