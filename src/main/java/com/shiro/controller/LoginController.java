package com.shiro.controller;

import com.shiro.dao.AnyUserMapper;
import com.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆控制器
 * @author Gruuy
 * @date 2019-7-17
 */
@RestController
public class LoginController {

    @Autowired
    private AnyUserMapper anyUserMapper;

    /**
     * 登陆接口
     * @param user
     * @return
     */
    @PostMapping("/Login")
    public Map<String,Object> login(@RequestBody User user){
        //获取Shiro项目对象
        Subject subject= SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 执行登录
        subject.login(token);
        // 根据权限，返回指定数据
        user=anyUserMapper.getUserByUsername(user.getUsername());
        Map<String,Object> map=new HashMap<>(1);
        String flag=user.getRole().stream().filter(a->a.equals("ROLE_ADMIN")).findAny().orElse(null);
        if(flag!=null&&!flag.equals("")){
            map.put("message","欢迎Admin!");
            return map;
        }
        map.put("error_msg","权限错误!");
        return map;
    }
}
