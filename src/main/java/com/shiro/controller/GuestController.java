package com.shiro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游客访问的地址
 * @author huangsz  2018/10/20 0020
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    public String login(){
        return "我是游客哦！";
    }
}