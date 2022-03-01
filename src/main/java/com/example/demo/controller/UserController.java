package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.serviceImpl.UserServiceImpl;
import com.example.demo.util.RandomValidateCode;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public User addUser(User user) throws Exception {
        return userService.add(user);
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public User queryUserById(String userId){
        return userService.selectById(userId);
    }
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public List<User> queryAllUsers(User user){
        return userService.selectAll(user);
    }
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public User updateUser(User user){
        return userService.updateInfo(user);
    }
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    public String deleteUser(String userId){
        int result = userService.delete(userId);
        if(result>0){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }
    //登录获取验证码
    @RequestMapping("/getSysManageLoginCode")
    @ResponseBody
    public String getSysManageLoginCode(HttpServletResponse response,
                                        HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response,"imagecode");// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //验证码验证
    @RequestMapping(value = "/checkimagecode")
    @ResponseBody
    public String checkTcode(HttpServletRequest request,HttpServletResponse response) {
        String validateCode = request.getParameter("validateCode");
        String code = null;
        //1:获取cookie里面的验证码信息
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("imagecode".equals(cookie.getName())) {
                code = cookie.getValue();
                break;
            }
        }
        //1:获取session验证码的信息
        //String code1 = (String) request.getSession().getAttribute("");
        //2:判断验证码是否正确
        if(!StringUtils.isEmpty(validateCode) && validateCode.equals(code)){
            return "ok";

        }
        return "error";
        //这里我没有进行字母大小模糊的验证处理，感兴趣的你可以去试一下！
    }

    /*
     * 登录
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public User login(User user){
        //根据ID获取用户
//        User byName = userServiceImpl.findById(user.getUserId());
        //密码验证
//        if(!user.getPassword().equals(byName.getPassword())){
//            System.out.println("密码不正确");
//            return null;
//        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserId(),
                user.getPassword()
        );
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            return null;
        }
        return userServiceImpl.findById(user.getUserId());
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

}
