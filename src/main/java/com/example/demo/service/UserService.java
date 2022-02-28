package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(String userId){
        return userMapper.selectById(userId);
    }

    public List<User> selectAll(User user){
        return userMapper.selectAll(user);
    }

    public User add(User user) throws Exception {
        User oldUser = userMapper.selectById(user.getUserId());
        if(oldUser != null){
            return oldUser;
        }
//        user.setProfilePic(uploadPic("profile_pic",request));
        userMapper.insert(user);
        return user;
    }
    public User updateInfo(User user){
        int result =  userMapper.updateInfo(user);
        if(result>0){
            return userMapper.selectById(user.getUserId());
        }else{
            return null;
        }
    }

    public int delete(String userId){
        return userMapper.deleteById(userId);
    }
    public String uploadPic(String itemName, MultipartHttpServletRequest request)throws Exception {
        String pathRoot = request.getSession().getServletContext().getRealPath(""); //获得服务器上绝对路径
        String path = "";// 保存的图片名
        List<String> paths = new ArrayList();//存放多个图片名
        for(MultipartFile mf : request.getFiles(itemName)) {
            if(!mf.isEmpty()) {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");//获得一个不会重复的文件名
                String contentType = mf.getContentType();//获得图片的后缀名
                String suffixName = contentType.substring(contentType.indexOf("/")+1);//截取图片后缀名，它是一个特殊的格式，比如XXXX/JPEG
                if(suffixName.equals("octet-stream"))return null;//一种特殊情况，一般不做处理
                path = "/files/"+uuid+"."+suffixName;//合成图片的路径。files是文件夹的名字
                mf.transferTo(new File(pathRoot+path));//存到服务器上
                paths.add(path);
            }
        }
        if(paths.size()!=0)
            return String.join(";", paths);
        else
            return null;
    }
}
