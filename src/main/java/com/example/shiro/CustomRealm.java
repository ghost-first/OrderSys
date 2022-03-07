package com.example.shiro;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userServiceImpl;

    private Integer count = 0;
    private String preUserId = "";
    //角色和权限添加。执行授权操作
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = userServiceImpl.selectById(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        System.out.println(user.getRole(user.getRoleId()));
        simpleAuthorizationInfo.addRole(user.getRole(user.getRoleId()));
        return simpleAuthorizationInfo;
    }

    //执行认证操作
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String userId1 = token1.getUsername();
        User user = userServiceImpl.selectById(userId1);
        if (user == null){
            return null;
        }else if (user.getIsLock() == 1){
            throw new DisabledAccountException();
        }
        if (token1.getPassword().equals(user.getPassword())){
            preUserId = "";
            count = 0;
        }else{
            if (preUserId.equals(userId1)){
                count++;
                if (count>5){
                    preUserId = "";
                    count = 0;
                    throw new ExcessiveAttemptsException();
                }
            }else {
                preUserId = userId1;
                count = 1;
            }
        }
        return new SimpleAuthenticationInfo(userId1,user.getPassword(),getName());
    }
}
