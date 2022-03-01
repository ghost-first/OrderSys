package com.example.config;

import com.example.shiro.CustomRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    /**
     * 设置cookie
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称,对应前端的checkbox的name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间10秒钟(单位秒)
        simpleCookie.setMaxAge(30000000);
        return simpleCookie;
    }
    /**
     * cookie管理对象,记住我功能
     * @return
     */
    @Bean
    public CookieRememberMeManager getRememberMeManager(@Qualifier("rememberMeCookie")SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        // rememberMe cookie加密的密钥  建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
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
        securityManager.setRememberMeManager(getRememberMeManager(rememberMeCookie()));
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
        map.put("/**/add","roles[ADMIN]");
        map.put("/**/remove","roles[ADMIN]");
        map.put("/notice/edit","roles[ADMIN]");
        map.put("/user/queryAll","roles[ADMIN]");
        map.put("/dishes/edit","roles[ADMIN]");
        map.put("/queryOrder","roles[ADMIN]");
        map.put("/Order/query","roles[ADMIN]");

        //后厨
        map.put("/dishOrder/querySome","roles[COOK]");
        map.put("/dishOrder/update","roles[COOK,WAITER]");

        //服务员
        map.put("/dishes/all","roles[WAITER],roles[ADMIN]");
        map.put("/dishes/query","roles[WAITER]");
        map.put("/dishes/querySome","roles[WAITER],roles[ADMIN]");
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
//        shiroFilterFactoryBean.setSuccessUrl("/addDish.html");
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