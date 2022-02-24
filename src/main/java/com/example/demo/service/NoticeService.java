package com.example.demo.service;
import com.example.demo.dao.NoticeMapper;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> findAll(){
        return noticeMapper.selectByExampleWithBLOBs(null);
    }

    public Notice findByDid(int notice_id){
        return noticeMapper.selectByPrimaryKey(notice_id);
    }

    public boolean addNotice(Notice notice) {
        return noticeMapper.insertSelective(notice)>0;
    }

    public boolean removeNotice(int notice_id) {
        return noticeMapper.deleteByPrimaryKey(notice_id)>0;
    }

    public boolean editNotice(Notice notice) {
        return noticeMapper.updateByPrimaryKeySelective(notice)>0;
    }
}
