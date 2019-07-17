package com.shiro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User权限
 * @author huangsz  2018/10/20 0020
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/getInfo")
    public String getInfo(){
        return "您拥有用户权限，可以获得该接口的信息！";
    }
}
