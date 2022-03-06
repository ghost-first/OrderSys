package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.serviceImpl.NoticeServiceImpl;
import com.example.demo.service.serviceImpl.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {
    private NoticeServiceImpl noticeServiceImpl;

    @Autowired
    public void setNoticeServiceImpl(NoticeServiceImpl noticeServiceImpl) {
        this.noticeServiceImpl = noticeServiceImpl;
    }

    @ResponseBody
    @RequestMapping("/all")
    public List<Notice> showNotice() {
        List<Notice> list = noticeServiceImpl.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Notice queryNotice(int notice_id){
        System.out.println("开始看公告了");
        return noticeServiceImpl.findByDid(notice_id);
    }

    @ResponseBody
    @RequestMapping("/remove")
    public boolean removeNotice(int notice_id){
        return noticeServiceImpl.removeNotice(notice_id);
    }

    @ResponseBody
    @RequestMapping("/add")
    public boolean addDish(Notice notice){
        notice.setSendTime(new Date());
        WebSocketService.sendAllMessage(notice.getUserId(),"管理员发布了一条新公告，请尽快查看！");
        return noticeServiceImpl.addNotice(notice);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public boolean editDish(Notice notice){
        notice.setSendTime(new Date());
        return noticeServiceImpl.editNotice(notice);
    }

}
