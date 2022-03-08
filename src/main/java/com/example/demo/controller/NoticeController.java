package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.serviceImpl.NoticeServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    private NoticeServiceImpl noticeServiceImpl;

    @Autowired
    public void setNoticeServiceImpl(NoticeServiceImpl noticeServiceImpl) {
        this.noticeServiceImpl = noticeServiceImpl;
    }


    @RequestMapping("/all")
//    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public List<Notice> showNotice() {
        List<Notice> list = noticeServiceImpl.findAll();
        return list;
    }


    @RequestMapping("/query")
//    @RequiresRoles(logical = Logical.OR, value = {"WAITER", "ADMIN","COOK"})
    public Notice queryNotice(int notice_id){
        System.out.println("开始看公告了");
        return noticeServiceImpl.findByDid(notice_id);
    }

    @RequestMapping("/remove")
    @RequiresRoles("ADMIN")
    public boolean removeNotice(int notice_id){
        return noticeServiceImpl.removeNotice(notice_id);
    }

    @RequestMapping("/add")
    @RequiresRoles("ADMIN")
    public boolean addDish(Notice notice){
        notice.setSendTime(new Date());
        return noticeServiceImpl.addNotice(notice);
    }

    @RequestMapping("/edit")
    @RequiresRoles("ADMIN")
    public boolean editDish(Notice notice){
        notice.setSendTime(new Date());
        return noticeServiceImpl.editNotice(notice);
    }

}
