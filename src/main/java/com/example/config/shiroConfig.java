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
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        //引入自定义的Filter
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        // 配置自定义 or角色 认证
        filtersMap.put("roles", new RoleFilter());
        //加入另一个filter
//        filtersMap.put("CORS",new CORSFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");

        //管理员
        map.put("/**/add","roles[ADMIN]");
        map.put("/**/remove","roles[ADMIN]");
        map.put("/notice/edit","roles[ADMIN]");
        map.put("/user/queryAll","roles[ADMIN]");
        map.put("/dishes/edit","roles[ADMIN]");
        map.put("/order/queryOrder","roles[ADMIN]");
        map.put("/order/query","roles[ADMIN]");
        map.put("/order/queryDetailOrder","roles[ADMIN]");
        map.put("/order/querySales","roles[ADMIN]");
        map.put("/order/get7DaysData","roles[ADMIN]");
        map.put("/order/get6MonthsData","roles[ADMIN]");
        map.put("/order/getToday","roles[ADMIN]");
        map.put("/order/getYesterday","roles[ADMIN]");
        map.put("/order/getThisMonth","roles[ADMIN]");
        map.put("/order/getThisWeek","roles[ADMIN]");

        //后厨
        map.put("/dishOrder/querySome","roles[COOK]");
        map.put("/dishOrder/update","roles[COOK,WAITER]");

        //服务员
        map.put("/dishes/all","roles[WAITER,ADMIN]");
        map.put("/dishes/query","roles[WAITER]");
        map.put("/dishes/querySome","roles[WAITER,ADMIN]");
        map.put("/order/newOrder","roles[WAITER]");
        map.put("/order/checkout","roles[WAITER]");
        map.put("/dishOrder/sendDishInfo","roles[WAITER]");


        //基础功能
        map.put("/login","anon");
        map.put("/user/modify","user");
        map.put("/user/query","user");
        map.put("/user/add","anon");
        map.put("/notice/query","user");
        map.put("/notice/all","user");
        map.put("/user/uploadFile","user");


//        map.put("/dishes/all","perms[add]");
//        map.put("")
        //登录
//        shiroFilterFactoryBean.setLoginUrl("/index.html");
        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/addDish.html");
        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/success.html");
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