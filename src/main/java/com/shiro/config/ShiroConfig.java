package com.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 * @author Gruuy
 * @date 2019-7-16
 */
@Configuration
public class ShiroConfig {

    Logger log= LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * anon：无参，开放权限，可以理解为匿名用户或游客
     * logout：无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
     * authc：无参，需要认证
     * authcBasic：无参，表示 httpBasic 认证
     * user：无参，表示必须存在用户，当登入操作时不做检查
     * ssl：无参，表示安全的URL请求，协议为 https
     * perms[user]：参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms["user, admin"]，当有多个参数时必须每个参数都通过才算通过
     * roles[admin]：参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles["admin，user"]，当有多个参数时必须每个参数都通过才算通过
     * rest[user]：根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
     * port[8081]：当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，
     * serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数
     * 注意：拦截全部要写在最后面，有先后顺序的
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录页
//        shiroFilterFactoryBean.setLoginUrl("/notLogin");
//        //设置登陆成功跳转页
//        shiroFilterFactoryBean.setSuccessUrl("/successLogin");
//        //设置权限不足的跳转页
//        shiroFilterFactoryBean.setUnauthorizedUrl("/NotRolePage");

        //配置拦截器(用Map写的Filter  牛逼啊)
        Map<String,String> map=new LinkedHashMap<>();
        //guest路径开头的  都不拦  （anon）  游客机制
        map.put("/guest/**","anon");
        //user开头的，需要有user角色
        map.put("/user/**","roles[ROLE_USER]");
        //admin开头的，需要有admin角色
        map.put("/admin/**","roles[ROLE_ADMIN]");
        //开放登陆接口（anon）
        map.put("/Login","anon");
        //其他的  拦他妈的
        //authc：要认证  认证就能访问啦
        map.put("/**","authc");

        //把过滤器Map设置到工厂中
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //日志输出一波
        log.info("shiroFilterFactoryBean构建成功！");
        //完成！
        return shiroFilterFactoryBean;
    }

    /**
     * 配置Shiro安全管理器
     * 注意命名，这个实例需要自动注入到上面的Bean之中，生成Shiro拦截工厂实例
     * 它用于自定义身份认证realm
     * realm相当于Security自定义的权限认证以及身份认证，但是它很简单
     * 只需要自己写一个类实现接口，重写对应的方法即可
     * Security真太尼玛比绕了！
     * 注意  返回类型是org.apache.shiro.mgt.SecurityManager
     * 你从DefaultWebSecurityManager一直找下去  可以找得到这个接口
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(customRealm);
        return securityManager;
    }

    /**
     * 返回realm的Bean实例
     * 用于设置到安全管理器
     * 使用其进行身份，权限认证
     */
    @Bean
    public CustomRealm customRealm(){
        return new CustomRealm();
    }
}
