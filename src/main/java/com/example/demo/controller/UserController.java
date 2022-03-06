package com.example.demo.controller;

import com.example.demo.entity.Dishes;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public User addUser(User user) throws Exception {
        return userServiceImpl.add(user);
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public User queryUserById(String userId){
        return userServiceImpl.selectById(userId);
    }
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public List<User> queryAllUsers(User user){
        return userServiceImpl.selectAll(user);
    }
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public User updateUser(User user){
        return userServiceImpl.updateInfo(user);
    }
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
    public String deleteUser(String userId){
        int result = userServiceImpl.delete(userId);
        if(result>0){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    /*
     * 登录
     * */
    @RequestMapping(value = "/login")
    public User login(@Param("userId") String userId, @Param("password") String password, Model model,HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        User user_wait = new User();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userId,password,true);
            try {
                subject.login(token);
            } catch(UnknownAccountException e) {
                user_wait.setRoleId(4);
            }catch (ExcessiveAttemptsException e){
                User user = new User();
                user.setUserId(userId);
                user.setIsLock(1);
                userServiceImpl.updateInfo(user);
                user_wait.setRoleId(5);
            }catch (IncorrectCredentialsException e){
                user_wait.setRoleId(6);
            }catch (DisabledAccountException e){
                user_wait.setRoleId(5);
            }
        }

        if (subject.isAuthenticated()){
            User user = userServiceImpl.selectById(userId);
            session.setAttribute("userId",userId);
            return user;
//            subject.logout();
        }
        return user_wait;
    }
    @RequestMapping("/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            System.out.println("准备取消已登录账户");
            subject.logout();
        }
    }

    @RequestMapping("/uploadFile")
    public String uploadFile(MultipartFile photo, HttpSession session) throws IOException {
        System.out.println("开始上传");
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        System.out.println("上传图片"+fileName);
        //处理文件重名问题
        String firstName = fileName.substring(0, fileName.lastIndexOf("."));
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = firstName+"-"+UUID.randomUUID().toString() + hzName;
        System.out.println("filename-------"+fileName);
        //获取服务器中photo目录的路径
//        ServletContext servletContext = session.getServletContext();
//        String photoPath = servletContext.getRealPath("photo");
        String photoPath = "/www/wwwroot/img";
        System.out.println(photoPath);
        File file = new File(photoPath);
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        System.out.println(finalPath);
        //实现上传功能
        photo.transferTo(new File(finalPath));

        return "img/"+fileName;
    }

}
