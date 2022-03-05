package com.example.demo.service;

import com.example.demo.entity.Notice;
import com.example.demo.entity.NoticeExample;

import java.util.List;

public interface NoticeService {
    List<Notice> findAll();

    Notice findByDid(int notice_id);

    boolean addNotice(Notice notice);

    boolean removeNotice(int notice_id);

    boolean editNotice(Notice notice);
}
