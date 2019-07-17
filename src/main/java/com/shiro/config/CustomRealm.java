package com.shiro.config;

import com.shiro.dao.AnyUserMapper;
import com.shiro.entity.User;
import com.shiro.service.AnyUserService;
import com.shiro.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的身份认证类
 * 必须写它，整个Shiro自定义就是这一块
 * 就好像Security自定义认证流程一样
 * 不写就是认证不来
 * 这玩意还要注册到SecurityManager里面
 * 继承AuthorizingRealm类
 * @author Gruuy
 * @date 2019-7-16
 */

public class CustomRealm extends AuthorizingRealm {

    private static Logger log= LoggerFactory.getLogger(CustomRealm.class);

    public CustomRealm(){}

    @Autowired
    private AnyUserService anyUserService;

    /**
     * Authorization是 认证，授权。
     * 获取授权信息
     * 你简单理解就是
     * 获取用户的角色
     * 就是Spring Security的ROLE_XXX
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("开始权限认证");
        // 获取登录用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //创建角色权限对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户角色
        User user=anyUserService.getUserByName(username);
        // 需要将 role 封装到 Set 作为 info.setRoles() 的参数
        Set<String> set=new HashSet<>();
        for (String role:user.getRole()) {
            set.add(role);
        }
        info.setRoles(set);
        return info;
    }

    /**
     * Authentication是 鉴定；证实
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     * 简单理解就是，
     * 登陆  嗯  登陆
     * @param token 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("开始身份认证（登陆）");
        //转换成UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
        //获取DB里的用户信息
        User user=anyUserService.getUserByName(usernamePasswordToken.getUsername());
        //获取token里的密码
        String password=new String(usernamePasswordToken.getPassword());
        //比对
        if(user==null){
            throw new AccountException("用户不存在！");
        }
        if(password==null||password.equals("")){
            throw new AccountException("请输入密码！");
        }else{
            if(!MD5Utils.checkPassword(user,password)){
                throw new AccountException("密码错误！");
            }
        }
        System.out.println(getName());
        return new SimpleAuthenticationInfo(token.getPrincipal(),password,getName());
    }
}
