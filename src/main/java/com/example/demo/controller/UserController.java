package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.serviceImpl.UserServiceImpl;
import com.example.demo.util.RandomValidateCode;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//@Controller
@RequestMapping("/user")
@CrossOrigin
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
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
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
    @RequestMapping(value = "/login")
//    @ResponseBody
//    public User login(User user){
//        //根据ID获取用户
//        User byName = userServiceImpl.findById(user.getUserId());
//        //密码验证
//        if(!user.getPassword().equals(byName.getPassword())){
//            return null;
//        }
//
//        return byName;
//    }
    public User login(@Param("userId") String userId, @Param("password") String password,
                      @Param("rememberMe")boolean rememberMe,RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userId,password,rememberMe);
            try {
                subject.login(token);
            } catch(UnknownAccountException e) {
                redirectAttributes.addAttribute("message","账户不存在");
            }catch (ExcessiveAttemptsException e){
                redirectAttributes.addAttribute("message","验证未通过，错误次数大于5次，账户已锁定！");
                User user = new User();
                user.setUserId(userId);
                user.setIsLock(1);
                userService.updateInfo(user);
            }catch (IncorrectCredentialsException e){
                redirectAttributes.addAttribute("message","验证未通过，账户密码错误！");
            }catch (DisabledAccountException e){
                redirectAttributes.addAttribute("message","验证未通过，账户已经禁止登录！");
            }
        }

        if (subject.isAuthenticated()){
            Session session = subject.getSession();
            User user = userService.selectById(userId);
            session.setAttribute("user",user);
            return user;
//            subject.logout();
        }
        return null;
    }
    @RequestMapping("/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            System.out.println("准备取消已登录账户");
            subject.logout();
        }
    }
}
