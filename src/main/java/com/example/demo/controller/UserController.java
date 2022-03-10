package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.serviceImpl.UserServiceImpl;
import com.example.util.JWTUtil;
import com.example.util.ResultMap;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.DigestUtils;
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

    private final ResultMap resultMap;

    @Autowired
    public UserController(ResultMap resultMap,UserServiceImpl userServiceImpl) {
        this.resultMap = resultMap;
        this.userServiceImpl=userServiceImpl;
    }

    //注册
    @RequiresGuest
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public boolean addUser(User user){
        return userServiceImpl.add(user);
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public User queryUserById(String userId){
        return userServiceImpl.selectById(userId);
    }

    @RequiresRoles("ADMIN")
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public List<User> queryAllUsers(User user){
        return userServiceImpl.selectAll(user);
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public User updateUser(User user){
        return userServiceImpl.updateInfo(user);
    }

    @RequiresRoles("ADMIN")
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
    public ResultMap login(@Param("userId") String userId, @Param("password") String password) {
        System.out.println("在登录");
        byte[] data = password.getBytes();
        password = DigestUtils.md5DigestAsHex(data);
        User user = userServiceImpl.selectById(userId);
        String realPassword = user.getPassword();
        int islock = user.getIsLock();
        if (realPassword == null) {
            return resultMap.fail().code(401).message("账号不存在").curRoleid(4).curuser(null);
        } else if (!realPassword.equals(password)) {
            return resultMap.fail().code(401).message("密码错误").curRoleid(6).curuser(null);
        } else if(islock==1){
            return resultMap.fail().code(401).message("用户已经锁定").curRoleid(5).curuser(null);
        } else {
            return resultMap.success().code(200).message(JWTUtil.createToken(userId)).curuser(user);
        }
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
