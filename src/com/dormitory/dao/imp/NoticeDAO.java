package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.INoticeDAO;
import com.dormitory.entity.Notice;

@Repository("noticeDAO")
public class NoticeDAO extends BaseDAO<Notice, Integer> implements INoticeDAO {

}
