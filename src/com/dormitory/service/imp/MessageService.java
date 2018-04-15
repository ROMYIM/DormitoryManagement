package com.dormitory.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.dormitory.constant.MessageStatus;
import com.dormitory.dao.IMessageDAO;
import com.dormitory.entity.Message;
import com.dormitory.service.IMessageService;

/**
*@author:   yim
*@date:  2018年3月31日下午2:46:46
*@description:   
*/
@Service("messageService")
@Transactional
public class MessageService implements IMessageService {
	
	@Resource(name = "messageDAO")
	private IMessageDAO messageDAO;

	@Override
	public List<Message> findUnSendMessagesGroupBySender(String receiverId) {
		// TODO Auto-generated method stub
		String hql = "select new Message(m.senderId, m.content, m.sendDate) from Message m where m.receiverId = ?"
				+ " group by m.senderId order by m.sendDate desc";
		return (List<Message>) messageDAO.queryByHQL(hql, receiverId);
	}

	@Override
	public List<Message> findMessagsBySendDate(String targetId, String owenerId, String sendDate) {
		// TODO Auto-generated method stub
//		String hql = "select new Message(m.senderId, m.content, m.sendDate) from Message m where m.receiverId = ? and m.senderId = ?"
//				+ " and m.sendDate >= ? order by m.sendDate union all"
//				+ " select new Message(m.senderId, m.content, m.sendDate) from Message m where m.receiverId = ? and m.senderId = ?"
//				+ " and m.sendDate >= ? order by m.sendDate";
//		return (List<Message>) messageDAO.queryByHQL(hql, targetId, owenerId, sendDate, owenerId, targetId, sendDate);
		String hql = "select new Message(m.senderId, m.content, m.sendDate) from Message m where m.receiverId = ? and m.senderId = ?"
				+ " and m.sendDate >= ? order by m.sendDate";
		return (List<Message>) messageDAO.queryByHQL(hql, targetId, owenerId, sendDate);
	}

	@Override
	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		messageDAO.save(message);
	}

}
