package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.serviceImpl.NoticeServiceImpl;
import com.example.demo.service.serviceImpl.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Notice> showNotice() {
        List<Notice> list = noticeServiceImpl.findAll();
        return list;
    }

    @RequestMapping("/query")
    public Notice queryNotice(int notice_id){
        System.out.println("开始看公告了");
        return noticeServiceImpl.findByDid(notice_id);
    }

    @RequestMapping("/remove")
    public boolean removeNotice(int noticeId,String userId){
        WebSocketService.sendAllMessage(userId,"公告");
        return noticeServiceImpl.removeNotice(noticeId);
    }

    @RequestMapping("/add")
    public boolean addNotice(Notice notice){
        notice.setSendTime(new Date());
        WebSocketService.sendAllMessage(notice.getUserId(),"公告");
        return noticeServiceImpl.addNotice(notice);
    }

    @RequestMapping("/edit")
    public boolean editNotice(Notice notice){
        notice.setSendTime(new Date());
        WebSocketService.sendAllMessage(notice.getUserId(),"公告");
        return noticeServiceImpl.editNotice(notice);
    }

}
