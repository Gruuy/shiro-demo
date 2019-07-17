package com.shiro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangsz  2018/10/20 0020
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/getInfo")
    public String getInfo(){
        return "您拥有管理员权限，可以获得该接口的信息！";
    }
}
