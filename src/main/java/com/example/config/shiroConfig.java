package com.example.config;

import com.example.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");

        //管理员
        map.put("/add","roles[ADMIN]");
        map.put("/remove","roles[ADMIN]");
        map.put("/notice/edit","roles[ADMIN]");
        map.put("/user/queryAll","roles[ADMIN]");
        map.put("/dishes/edit","roles[ADMIN]");
        map.put("/queryOrder","roles[ADMIN]");
        map.put("/Order/query","roles[ADMIN]");

        //后厨
        map.put("/dishOrder/querySome","roles[COOK]");
        map.put("/dishOrder/update","roles[COOK,WAITER]");

        //服务员
        map.put("/dishes/all","roles[WAITER]");
        map.put("/dishes/query","roles[WAITER]");
        map.put("/Order/newOrder","roles[WAITER]");
        map.put("/checkout","roles[WAITER]");
        map.put("/dishOrder/sendDishInfo","roles[WAITER]");

        //基础功能
        map.put("/login","anon");
        map.put("/user/modify","user");
        map.put("/user/query","user");
        map.put("/user/add","user");
        map.put("/notice/query","user");
        map.put("/notice/all","user");
        map.put("/user/uploadFile","user");

//        map.put("/dishes/all","perms[add]");
//        map.put("")
        //登录
        shiroFilterFactoryBean.setLoginUrl("/index.html");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/addDish.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/success.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}