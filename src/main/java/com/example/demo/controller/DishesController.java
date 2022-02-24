package com.example.demo.controller;

import com.example.demo.entity.Dishes;
import com.example.demo.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/dishes")
public class DishesController {

    @Autowired
    private DishesService dishesService;

//    public DishesService getDishesService() {
//        return dishesService;
//    }
//
//    @Autowired
//    public void setDishesService(DishesService dishesService) {
//        this.dishesService = dishesService;
//    }

    @RequestMapping("/all")
    @ResponseBody
    public List<Dishes> showDishes() {
        List<Dishes> list = dishesService.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Dishes queryDish(int dishId){
//        System.out.println("开始queryDish");
        Dishes dish = dishesService.findByDid(dishId);
        System.out.println(dish);
//        Dishes test = new Dishes();
//        test.setDishId(1);
//        test.setPrice(2.0);
//        test.setDishName("test");
//        test.setIntro("dish");
//        test.setType("type");
        return dish;
    }
    @ResponseBody
    @RequestMapping("/querySome")
    public List<Dishes> querySomeDishes(String dishName){
        return dishesService.findSomeDishes(dishName);
    }

    @ResponseBody
    @RequestMapping("/remove")
    public boolean removeDish(int dishid){
        return dishesService.removeDishes(dishid);
    }

    @ResponseBody
    @RequestMapping("/add")
    public boolean addDish(Dishes dish,MultipartHttpServletRequest request)throws Exception {
        System.out.println(dish);
//        dish.setDishPic(uploadPic("dish_pic",request));  //设置图片
        return dishesService.addDishes(dish);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public boolean editDish(Dishes dish,MultipartHttpServletRequest request)throws Exception {
//        dish.setDishPic(uploadPic("dish_pic",request));  //设置图片
        System.out.println(dish);
        return dishesService.editDishes(dish);
    }

    //图片上传
    public String uploadPic(String itemName, MultipartHttpServletRequest request)throws Exception {
        String pathRoot = request.getSession().getServletContext().getRealPath(""); //获得服务器上绝对路径
        String path = "";// 保存的图片名
        List<String> paths = new ArrayList<String>();//存放多个图片名
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
