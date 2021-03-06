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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            return "????????????";
        }else{
            return "????????????";
        }
    }
    //?????????????????????
//    @RequestMapping("/getSysManageLoginCode")
//    @ResponseBody
//    public String getSysManageLoginCode(HttpServletResponse response,
//                                        HttpServletRequest request) {
//        response.setContentType("image/jpeg");// ??????????????????,???????????????????????????????????????
//        response.setHeader("Pragma", "No-cache");// ????????????????????????????????????????????????????????????
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Set-Cookie", "name=value; HttpOnly");//??????HttpOnly??????,??????Xss??????
//        response.setDateHeader("Expire", 0);
//        RandomValidateCode randomValidateCode = new RandomValidateCode();
//        try {
//            randomValidateCode.getRandcode(request, response,"imagecode");// ??????????????????
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    //???????????????
//    @RequestMapping(value = "/checkimagecode")
//    @ResponseBody
//    public String checkTcode(HttpServletRequest request,HttpServletResponse response) {
//        String validateCode = request.getParameter("validateCode");
//        String code = null;
//        //1:??????cookie????????????????????????
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if ("imagecode".equals(cookie.getName())) {
//                code = cookie.getValue();
//                break;
//            }
//        }
//        //1:??????session??????????????????
//        //String code1 = (String) request.getSession().getAttribute("");
//        //2:???????????????????????????
//        if(!StringUtils.isEmpty(validateCode) && validateCode.equals(code)){
//            return "ok";
//
//        }
//        return "error";
//        //?????????????????????????????????????????????????????????????????????????????????????????????
//    }

    /*
     * ??????
     * */
    @RequestMapping(value = "/login")
    @ResponseBody
//    public User login(User user){
//        //??????ID????????????
//        User byName = userServiceImpl.findById(user.getUserId());
//        //????????????
//        if(!user.getPassword().equals(byName.getPassword())){
//            return null;
//        }
//
//        return byName;
//    }
    public User login(@Param("userId") String userId, @Param("password") String password,RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userId,password,true);
            try {
                subject.login(token);
            } catch(UnknownAccountException e) {
                redirectAttributes.addAttribute("message","???????????????");
            }catch (ExcessiveAttemptsException e){
                redirectAttributes.addAttribute("message","????????????????????????????????????5????????????????????????");
                User user = new User();
                user.setUserId(userId);
                user.setIsLock(1);
                userService.updateInfo(user);
            }catch (IncorrectCredentialsException e){
                redirectAttributes.addAttribute("message","???????????????????????????????????????");
            }catch (DisabledAccountException e){
                redirectAttributes.addAttribute("message","?????????????????????????????????????????????");
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
            System.out.println("???????????????????????????");
            subject.logout();
        }
        System.out.println("????????????\nlogin??????");
    }

    @RequestMapping("/uploadFile")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
        System.out.println("????????????");
        //?????????????????????????????????
        String fileName = photo.getOriginalFilename();
        //????????????????????????
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        //??????????????????photo???????????????
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        System.out.println(photoPath);
        File file = new File(photoPath);
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        System.out.println(finalPath);
        //??????????????????
        photo.transferTo(new File(finalPath));

        return finalPath;
    }

}
