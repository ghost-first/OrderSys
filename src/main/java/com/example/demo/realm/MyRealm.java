package com.example.demo.realm;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    private Integer count = 0;
    private String preUserId = "";
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        //能进入到这里，表示账号已经通过验证了
//        String userName =(String) principals.getPrimaryPrincipal();
//        //获取角色和权限
//        List<String> permissions = iUserService.listPermissions(userName);
//        List<String> roles = iUserService.listRoles(userName);
//        //授权对象
//        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
//        //把通过DAO获取到的角色和权限放进去
//        s.setStringPermissions(new HashSet(permissions));
//        s.setRoles(new HashSet(roles));
//        return s;
        System.out.println("执行了授权");
        return null;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        //获取账号密码
//        UsernamePasswordToken t = (UsernamePasswordToken) token;
//        String userId= token.getPrincipal().toString();
//        String password= new String( t.getPassword());
//
//        //获取数据库中的密码
//        String password1 = userService.selectById(userId).getPassword();
//
//        //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
//        if(null==password1 || !password1.equals(password)){
//            return null;
//        }
//        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
//        return new SimpleAuthenticationInfo(userId,password,getName());
        System.out.println("执行了认证");
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String userId1 = token1.getUsername();
        User user = userService.selectById(userId1);
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