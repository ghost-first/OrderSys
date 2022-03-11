package com.example.demo.service.serviceImpl;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录时根据用户id查询用户信息
     * @param userId
     * @return
     */
    public User selectById(String userId){
        User user = userMapper.selectById(userId);
        //解密
        try {
            Base64 base64 = new Base64();
            String password = user.getPassword();
            String decodePassword = new String(base64.decode(password));
            System.out.println("decodePassword:"+decodePassword);
            user.setPassword(decodePassword);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查询所有用户
     * @param user
     * @return
     */
    public List<User> selectAll(User user){
        List<User> users = userMapper.selectAll(user);
        for (User queryUser : users) {
            org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
            String password = queryUser.getPassword();
            String decodePassword = new String(base64.decode(password));
            System.out.println(decodePassword);
            queryUser.setPassword(decodePassword);
        }
        //解密

        return users;
    }

    /**
     * 用户注册
     * @param user
     * @return
     * @throws Exception
     */
    public boolean add(User user){
        try {
            //加密
            String password = user.getPassword();
            byte[] data = password.getBytes();
            org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
            String encode = base64.encodeAsString(data);

            user.setPassword(encode);
            return userMapper.insertSelective(user)>0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public User updateInfo(User user){
        byte[] data = user.getPassword().getBytes();

        //加密
        org.apache.commons.codec.binary.Base64 base64 = new Base64();
        String encode = base64.encodeAsString(data);
        user.setPassword(encode);

        int result =  userMapper.updateInfo(user);

        if(result>0){
            return selectById(user.getUserId());
        }else{
            return null;
        }
    }

    public int delete(String userId){
        return userMapper.deleteById(userId);
    }

    /**
     * 上传图片
     * @param itemName
     * @param request
     * @return
     * @throws Exception
     */
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
