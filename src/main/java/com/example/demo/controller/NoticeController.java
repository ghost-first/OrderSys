package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.NoticeService;
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
    private NoticeService noticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ResponseBody
    @RequestMapping("/all")
    public List<Notice> showNotice() {
        List<Notice> list = noticeService.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Notice queryNotice(int notice_id){
        System.out.println("开始看公告了");
        return noticeService.findByDid(notice_id);
    }

    @ResponseBody
    @RequestMapping("/remove")
    public boolean removeNotice(int notice_id){
        return noticeService.removeNotice(notice_id);
    }

    @ResponseBody
    @RequestMapping("/add")
    public boolean addDish(Notice notice){
        notice.setSendTime(new Date());
        return noticeService.addNotice(notice);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public boolean editDish(Notice notice){
        notice.setSendTime(new Date());
        return noticeService.editNotice(notice);
    }

}
